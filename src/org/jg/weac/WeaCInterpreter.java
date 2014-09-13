package org.jg.weac;

import java.util.*;

import org.jg.weac.insn.*;
import org.jg.weac.insn.OperationInstruction.Operation;

public class WeaCInterpreter implements OpCodes
{

	private ArrayList<WeaCLib> included;
	private int				line;

	public void run(ArrayList<WeaCLib> included, ArrayList<Instruction> instructions) throws WeaCException
	{
		this.included = included;
		instructions.forEach(System.out::println);
		System.out.println("exit code: " + invokeMethod(instructions, "__MAIN__", "main", "int()", new Stack<>()));
	}

	private WeaCValue invokeMethod(ArrayList<Instruction> insns, String owner, String methodName, String methodDesc, Stack<WeaCValue> stack) throws WeaCException
	{
		boolean execute = false;
		boolean invoked = false;
		boolean hasReturn = false;
		HashMap<Integer, WeaCVariable> varMap = new HashMap<>();
		for(WeaCLib lib : included)
		{
			if(lib.isCompiledDirectly()) continue;
			if(lib.getName().equals(owner))
			{
				for(WeaCMethod method : lib.getMethods())
				{
					if(method.getName().equals(methodName) && WeaCHelper.areDescEquals(methodDesc, method.getDesc().toString()))
					{
						int n = WeaCHelper.countChar(method.getDesc().toString(), ';');
						WeaCValue[] args = new WeaCValue[0];
						if(n > 0)
						{
							args = new WeaCValue[n];
							for(int i = n - 1; i >= 0; i-- )
							{
								args[i] = stack.pop();
							}
						}
						WeaCValue returned = method.invoke(args);
						invoked = true;
						return returned;
					}
				}
			}
		}
		for(Instruction insn : insns)
		{
			if(insn instanceof MethodStartInstruction)
			{
				MethodStartInstruction start = (MethodStartInstruction)insn;
				if(start.getMethod().getOwner().equals(owner) && start.getMethod().getName().equals(methodName) && WeaCHelper.areDescEquals(start.getMethod().getDesc().toString(), methodDesc))
				{
					execute = true;
					invoked = true;
					varMap.clear();
					int index = 0;
					for(WeaCVariable v : start.getMethod().getLocals())
					{
						varMap.put(index++ , v);
					}
					int n = WeaCHelper.countChar(methodDesc, ';');
					if(n > 0)
					{
						for(int i = n - 1; i >= 0; i-- )
						{
							WeaCValue val = stack.pop();
							WeaCVariable var = new WeaCVariable(i, start.getMethod().getLocals().get(i).name, val.type, true, false, val.value);
							varMap.put(i, var);
						}
					}
				}
			}
			else if(insn.getOpcode() == METHOD_END && execute)
			{
				execute = false;
				if(invoked) break;
			}
			else if(execute)
			{
				execute(insns, insn, stack, varMap);
				if(insn.getOpcode() == RETURN)
				{
					hasReturn = true;
				}
			}
		}
		WeaCValue returnedValue = null;
		if(!invoked)
		{
			WeaCHelper.throwError("No found method: " + owner + "::" + methodName + " " + methodDesc, line);
			hasReturn = false;
		}
		if(hasReturn)
		{
			returnedValue = stack.pop();
		}
		return returnedValue;
	}

	private void execute(ArrayList<Instruction> insns, Instruction insn, Stack<WeaCValue> stack, HashMap<Integer, WeaCVariable> varMap) throws WeaCException
	{
		if(insn.getOpcode() == LOAD_CONST)
		{
			WeaCValue constant = ((LoadConstantInstruction)insn).getConstant();
			stack.push(constant);
		}
		else if(insn.getOpcode() == METHOD_CALL)
		{
			MethodCallInstruction callInsn = (MethodCallInstruction)insn;
			WeaCValue returned = invokeMethod(insns, callInsn.getMethod().getOwner(), callInsn.getMethod().getName(), callInsn.getMethod().getDesc().toString(), stack);
			if(returned != null) stack.push(returned);
		}
		else if(insn.getOpcode() == RETURN)
		{
			WeaCValue returned = stack.pop();
			stack.push(returned); // Useless, i do know, thx
		}
		else if(insn.getOpcode() == OPERATION)
		{
			WeaCValue b = stack.pop();
			WeaCValue a = stack.pop();
			Operation op = ((OperationInstruction)insn).getOperation();
			if(op == Operation.ADDITION)
			{
				stack.push(a.type.add(a, b));
			}
			else if(op == Operation.SUBTRACTION)
			{
				stack.push(a.type.sub(a, b));
			}
			else if(op == Operation.MULTIPLICATION)
			{
				stack.push(a.type.mul(a, b));
			}
			else if(op == Operation.DIVISION)
			{
				stack.push(a.type.div(a, b));
			}
			else if(op == Operation.MODULO)
			{
				stack.push(a.type.mod(a, b));
			}
			else if(op == Operation.LEFT_SHIFT)
			{
				stack.push(a.type.lsh(a, b));
			}
			else if(op == Operation.RIGHT_SHIFT)
			{
				stack.push(a.type.rsh(a, b));
			}
			else if(op == Operation.UNSIGNED_RIGHT_SHIFT)
			{
				stack.push(a.type.ursh(a, b));
			}
			else if(op == Operation.BITWISE_AND)
			{
				stack.push(a.type.bitAnd(a, b));
			}
			else if(op == Operation.BITWISE_OR)
			{
				stack.push(a.type.bitOr(a, b));
			}
			else if(op == Operation.BITWISE_XOR)
			{
				stack.push(a.type.bitXor(a, b));
			}
		}
		else if(insn.getOpcode() == VAR_LOAD)
		{
			WeaCVariable var = varMap.get(((LoadVariableInstruction)insn).getVarIndex());
			stack.push(var);
		}
		else if(insn.getOpcode() == VAR_STORE)
		{
			WeaCVariable var = varMap.get(((StoreVariableInstruction)insn).getVarIndex());
			WeaCValue val = stack.pop();
			if(!var.type.isCompatible(val.type))
			{
				WeaCHelper.throwError("Cannot cast " + val.type.getID() + " to " + var.type.getID(), line);
			}
			var.value = val.value;
			varMap.put(var.index, var);
		}
	}

}
