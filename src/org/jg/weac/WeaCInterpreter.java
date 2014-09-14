package org.jg.weac;

import java.util.*;

import org.jg.weac.WeaCMethod.MethodDesc;
import org.jg.weac.insn.*;
import org.jg.weac.insn.LabelInstruction.Label;
import org.jg.weac.insn.OperationInstruction.Operation;
import org.jg.weac.types.*;
import org.jg.weac.values.*;

public class WeaCInterpreter implements OpCodes
{

	private ArrayList<WeaCLib> included;
	private int				line;
	private Label			  currentLabel;
	private Label			  skipTo;
	private ArrayValue		 array;

	public void run(WeaCode code) throws Exception
	{
		this.included = code.getRequiredLibs();
		code.getInstructions().forEach(System.out::println);
		System.out.println("exit code: " + invokeMethod(code.getInstructions(), "test", "main", "int()", new Stack<>()));
	}

	private WeaCValue invokeMethod(ArrayList<Instruction> insns, String owner, String methodName, String methodDesc, Stack<WeaCValue> stack) throws Exception
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
					if(method.getName().equals(methodName) && WeaCHelper.areDescEquals(method.getDesc().toString(), methodDesc))
					{
						int n = WeaCHelper.countChar(method.getDesc().toString(), ';');
						MethodDesc desc = method.getDesc();
						WeaCValue[] args = new WeaCValue[0];
						if(n > 0)
						{
							args = new WeaCValue[n];
							for(int i = n - 1; i >= 0; i-- )
							{
								args[i] = stack.pop();
								if(!desc.getType(i).isCompatible(args[i].type)) WeaCHelper.throwError("Cannot cast " + args[i].type.getID() + " to " + desc.getType(i).getID(), line);
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
							WeaCVariable var = new WeaCVariable(i, start.getMethod().getLocals().get(i).name, val.type, true, false, val);
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

	private void execute(ArrayList<Instruction> insns, Instruction insn, Stack<WeaCValue> stack, HashMap<Integer, WeaCVariable> varMap) throws Exception
	{
		if(insn.getOpcode() == LABEL)
		{
			currentLabel = ((LabelInstruction)insn).getLabel();
			if(currentLabel.equals(skipTo))
			{
				skipTo = null;
			}
		}
		else if(insn.getOpcode() == LINE_NUMBER)
		{
			line = ((LineNumberInstruction)insn).getLine();
		}
		else if(skipTo == null)
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
				try
				{
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

					if(stack.peek() == null) throw new Exception();
				}
				catch(Exception e)
				{
					try
					{
						if(op == Operation.ADDITION)
						{
							stack.push(b.type.add(a, b));
						}
						else if(op == Operation.SUBTRACTION)
						{
							stack.push(b.type.sub(a, b));
						}
						else if(op == Operation.MULTIPLICATION)
						{
							stack.push(b.type.mul(a, b));
						}
						else if(op == Operation.DIVISION)
						{
							stack.push(b.type.div(a, b));
						}
						else if(op == Operation.MODULO)
						{
							stack.push(b.type.mod(a, b));
						}
						else if(op == Operation.LEFT_SHIFT)
						{
							stack.push(b.type.lsh(a, b));
						}
						else if(op == Operation.RIGHT_SHIFT)
						{
							stack.push(b.type.rsh(a, b));
						}
						else if(op == Operation.UNSIGNED_RIGHT_SHIFT)
						{
							stack.push(b.type.ursh(a, b));
						}
						else if(op == Operation.BITWISE_AND)
						{
							stack.push(b.type.bitAnd(a, b));
						}
						else if(op == Operation.BITWISE_OR)
						{
							stack.push(b.type.bitOr(a, b));
						}
						else if(op == Operation.BITWISE_XOR)
						{
							stack.push(b.type.bitXor(a, b));
						}

						if(stack.peek() == null) throw new Exception();
					}
					catch(Exception e1)
					{
						WeaCHelper.throwError("Invalid operand '" + op.getID() + "' between " + a.type.getID() + " and " + b.type.getID() + " types", line);
					}
				}
			}
			else if(insn.getOpcode() == VAR_LOAD)
			{
				WeaCVariable var = varMap.get(((LoadVariableInstruction)insn).getVarIndex());
				stack.push(var.value);
			}
			else if(insn.getOpcode() == VAR_STORE)
			{
				WeaCVariable var = varMap.get(((StoreVariableInstruction)insn).getVarIndex());
				WeaCValue val = stack.pop();
				if(!var.type.isCompatible(val.type))
				{
					WeaCHelper.throwError("Cannot cast " + val.type.getID() + " to " + var.type.getID(), line);
				}
				var.value = val.type.newValue(val.getValue());
				varMap.put(var.index, var);
			}
			else if(insn.getOpcode() == LOAD_NULL)
			{
				stack.push(new WeaCValue(null, WeaCType.wildcardType));
			}
			else if(insn.getOpcode() == IF)
			{
				WeaCValue val = stack.pop();
				if(!WeaCType.boolType.equal(val, WeaCType.boolType.TRUE))
				{
					skipTo = ((IfInstruction)insn).getGotoLabel();
				}
			}
			else if(insn.getOpcode() == INVERT_BOOL)
			{
				WeaCValue val = stack.pop();
				if(!WeaCType.boolType.isCompatible(val.type))
				{
					WeaCHelper.throwError("Cannot cast " + val.type + " to bool", line);
				}
				WeaCValue newVal = new WeaCValue(!(Boolean)val.getValue(), WeaCType.boolType);
				stack.push(newVal);
			}
			else if(insn.getOpcode() == ARRAY_STORE)
			{
				array.addValue(stack.pop());
			}
			else if(insn.getOpcode() == NEW_ARRAY)
			{
				array = new ArrayValue(WeaCType.wildcardType);
			}
			else if(insn.getOpcode() == PUSH_ARRAY)
			{
				stack.push(array);
				array = null;
			}
			else if(insn.getOpcode() == GET_FIELD)
			{
				stack.push(stack.pop().getField(((GetFieldInstruction)insn).getField()));
			}
			else if(insn.getOpcode() == LOAD_INDEX)
			{
				WeaCValue pop = stack.pop();
				WeaCValue array = stack.pop();
				if(array.type instanceof ArrayType)
				{
					stack.push(array.getField("__" + (int)pop.getValue()));
				}
				else
					System.err.println("TMP DEBUG: Array param is not actual array");
			}
			else
			{
				System.out.println("unhandled instruction: " + insn);
			}
		}
	}
}
