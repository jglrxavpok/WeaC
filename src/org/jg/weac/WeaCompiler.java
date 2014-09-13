package org.jg.weac;

import java.io.*;
import java.util.*;

import org.jg.weac.WeaCMethod.MethodDesc;
import org.jg.weac.insn.*;
import org.jg.weac.insn.LabelInstruction.Label;
import org.jg.weac.insn.OperationInstruction.Operation;
import org.jg.weac.libs.*;

public class WeaCompiler implements OpCodes
{

	public enum BlockType
	{
		NONE, METHOD_DEFINITION
	}

	private ArrayList<WeaCLib> included;

	public WeaCompiler()
	{
		included = new ArrayList<>();
		included.add(new StdLib());
	}

	private String		   currentLib	  = "";
	private float			sourceLine	  = 1;
	private WeaCMethod	   compilingMethod = null;
	private WeaCPreprocessor preprocessor;
	private Stack<String>	libs			= new Stack<>();
	private String		   fileName;
	private float			lastLine;
	private int			  labelNbr;
	private Stack<Label>	 labelStack	  = new Stack<>();

	public WeaCode compile(String prototypes, String implementation, String fileName) throws WeaCException
	{
		this.fileName = fileName;
		this.currentLib = fileName;
		enterNewLib(fileName);
		preprocessor = new WeaCPreprocessor();
		prototypes = prototypes.replace("\r", "\n");
		implementation = implementation.replace("\r", "\n");
		String[] splitInput = implementation.split("\n");
		String withoutComments = "";
		for(int i = 0; i < splitInput.length; i++ )
		{
			if(splitInput[i].contains("//"))
			{
				splitInput[i] = splitInput[i].substring(splitInput[i].indexOf("//"));
			}
			withoutComments += splitInput[i] + "\n";
		}
		implementation = withoutComments;
		withoutComments = "";
		splitInput = prototypes.split("\n");
		for(int i = 0; i < splitInput.length; i++ )
		{
			if(splitInput[i].contains("//"))
			{
				splitInput[i] = splitInput[i].substring(splitInput[i].indexOf("//"));
			}
			withoutComments += splitInput[i] + "\n";
		}
		prototypes = withoutComments;
		preprocessor.parseHeader(this, 0, "", implementation, fileName);
		currentLib = this.fileName;
		char[] chars = implementation.toCharArray();
		StringBuffer buffer = new StringBuffer();
		ArrayList<Instruction> instructions = new ArrayList<>();
		BlockType blockType = BlockType.NONE;
		String methodReturnType = "void";
		String methodName = null;
		ArrayList<String> methodArgsNames = new ArrayList<>();
		ArrayList<String> methodArgsTypes = new ArrayList<>();

		int blocks = 0;

		boolean inVariable = false;
		for(int i = 0; i < chars.length; i++ )
		{
			char current = chars[i];
			if(current == '\t') continue;
			if(current == '\n' || current == '\r')
			{
				continue;
			}
			else if(current == '>')
			{
				String s = buffer.toString();
				if(s.contains("#include"))
				{
					String s2 = s.substring(s.indexOf("#include") + "#include".length());
					if(s.contains("<"))
					{
						String s1 = s2.substring(s2.indexOf("<") + 1, s2.length()).replace(" ", "");
						if(!includeStandard(s1))
						{
							implementation = preprocessor.parseHeader(this, i, s, implementation, s1);
							chars = implementation.toCharArray();
						}
						else
						{
							int end = i - s.length() - s.indexOf("#include") - 1;
							if(end < 0) end = 0;
							implementation = implementation.substring(0, end).replaceFirst(s1, "") + implementation.substring(i + 1);
							chars = implementation.toCharArray();
							i = i - s.length();
						}
						buffer.delete(0, buffer.length());
					}
				}
			}
			else
			{
				buffer.append(current);
			}
		}
		buffer.delete(0, buffer.length());
		for(int i = 0; i < chars.length; i++ )
		{
			char current = chars[i];
			if(current == '\t') continue;
			if(current == '$')
			{
				if(buffer.toString().endsWith("$$$$$$$$$$$$$$$"))
				{
					buffer.delete(0, buffer.length());
					currentLib = libs.pop();
				}
				else
					buffer.append(current);
			}
			if(blockType == BlockType.METHOD_DEFINITION)
			{
				if(current == ' ')
				{
					buffer.append(current);
					// TODO: tmp
				}
				else if(current == '\n')
				{
					lastLine = sourceLine;
					sourceLine += 0.5f;
					if((int)sourceLine != (int)lastLine)
					{
						instructions.add(new LineNumberInstruction((int)sourceLine));
					}
				}
				else if(current == '=')
				{
					buffer.append(current);
					inVariable = true;
				}
				else if(current == ';')
				{
					endOfInsn(instructions, buffer.toString());
					buffer.delete(0, buffer.length());
					inVariable = false;
				}
				else if(current == '{' && !inVariable)
				{
					blocks++ ;
				}
				else if(current == '}' && !inVariable)
				{
					if(blocks == 0)
					{
						instructions.add(new BaseInstruction(METHOD_END));
						blockType = BlockType.NONE;
					}
					else
					{
						labelStack.pop().setNbr(labelNbr);
						blocks-- ;
					}
				}
				else
				{
					buffer.append(current);
				}
			}
			else if(blockType == BlockType.NONE)
			{
				if(current == '\n' || current == '\r')
				{
					lastLine = sourceLine;
					sourceLine += 0.5f;
					if((int)sourceLine != (int)lastLine)
					{
						instructions.add(new LineNumberInstruction((int)sourceLine));
					}
					continue;
				}
				else if(current == ' ')
				{
					if(buffer.length() > 0 && !buffer.toString().contains("#include"))
					{
						methodReturnType = buffer.toString();
						buffer.delete(0, buffer.length());
					}
					else
						buffer.append(current);
				}
				else if(current == '(')
				{
					methodName = buffer.toString();
					if(methodName.contains("#include ") && methodName.contains(">"))
					{
						methodName = methodName.replace("#include ", "");
						int index = methodName.indexOf(">") + 1;
						methodName = methodName.substring(methodName.indexOf(" ", index) + 1);
					}
					methodArgsNames.clear();
					methodArgsTypes.clear();
					String argsStr = implementation.substring(i + 1, implementation.indexOf(")", i + 1));
					String tokens[] = null;

					if(argsStr.contains(","))
					{
						tokens = argsStr.split(",");
					}
					else
					{
						tokens = new String[]
						{
							argsStr
						};
					}
					String methodDesc = "";
					int varIndex = 0;
					ArrayList<WeaCVariable> varList = new ArrayList<>();
					for(String token : tokens)
					{
						if(token.length() > 0)
						{
							String[] split = token.split(" ");
							String type = split[0];
							String name = split[split.length - 1];
							methodArgsNames.add(name);
							methodArgsTypes.add(type);
							methodDesc += type + ";";
							varList.add(new WeaCVariable(varIndex, name, WeaCType.get(type), true, false, null));
							varIndex++ ;
						}
					}
					blockType = BlockType.METHOD_DEFINITION;
					i = implementation.indexOf("{", i + 1) + 1;
					while(methodReturnType.startsWith("$"))
						methodReturnType = methodReturnType.substring(1);
					WeaCMethod method = new WeaCMethod(currentLib, methodName, methodReturnType + "(" + methodDesc + ")");
					method.addLocals(varList);
					MethodStartInstruction insn = new MethodStartInstruction(method);
					instructions.add(insn);
					buffer.delete(0, buffer.length());
					compilingMethod = method;
					blocks = 0;
				}
				else
				{
					buffer.append(current);
				}
			}
		}

		return new WeaCode(included, instructions);
	}

	private boolean includeStandard(String s1)
	{
		WeaCLib standardLib = WeaCLib.getStandardLib(s1);
		if(standardLib != null)
		{
			included.add(standardLib);
			return true;
		}
		return false;
	}

	public WeaCLib parseLib(String prototypesSource, String name)
	{
		String[] prototypes = prototypesSource.split(";");
		ArrayList<WeaCMethod> methods = new ArrayList<>();
		for(String prototype : prototypes)
		{
			if(prototype.length() > 0 && !prototype.equals("\n"))
			{
				prototype = prototype.replace("  ", " ").replace("\n", "").replace("\r", "").replace("\t", "");
				String argsStr = prototype.substring(prototype.indexOf("(") + 1, prototype.indexOf(")"));
				String mName = prototype.substring(prototype.indexOf(" ") + 1, prototype.indexOf("("));
				String desc = prototype.substring(0, prototype.indexOf(" ")) + "(";
				if(argsStr.length() > 0)
				{
					String[] tokens = argsStr.split(",");
					for(int i = 0; i < tokens.length; i++ )
					{
						int start = 0;
						while(tokens[i].charAt(start) == ' ')
							start++ ;
						tokens[i] = tokens[i].substring(start);
						String split[] = tokens[i].split(" ");
						String type = split[0];
						desc += type + ";";
					}
				}
				desc += ")";
				methods.add(new WeaCMethod(name, mName, desc));
			}
		}
		if(methods.isEmpty()) return null;
		WeaCMethod[] methodsArray = methods.toArray(new WeaCMethod[0]);
		WeaCLib lib = new WeaCLib()
		{

			@Override
			public String getName()
			{
				return name;
			}

			@Override
			public WeaCMethod[] getMethods()
			{
				return methodsArray;
			}

			public boolean isCompiledDirectly()
			{
				return true;
			}

		};
		return lib;
	}

	static String read(String file)
	{
		try
		{
			InputStream input = Test.class.getResourceAsStream(file);
			byte[] buffer = new byte[65565];
			int i;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while((i = input.read(buffer, 0, buffer.length)) != -1)
			{
				out.write(buffer, 0, i);
			}
			out.flush();
			out.close();
			return new String(out.toByteArray(), "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void endOfInsn(ArrayList<Instruction> instructions, String buffer) throws WeaCException
	{
		instructions.add(new LabelInstruction(getNewLabel()));
		int endOfName = buffer.length();
		if(buffer.indexOf("(") >= 0)
		{
			endOfName = buffer.indexOf("(");
		}
		else if(buffer.indexOf(" ") >= 0)
		{
			endOfName = buffer.indexOf(" ");
		}
		String suggestedName = buffer.substring(0, endOfName);
		if(suggestedName.startsWith("return"))
		{
			boolean mustReturnValue = false;
			if(compilingMethod != null)
			{
				MethodDesc desc = compilingMethod.getDesc();
				mustReturnValue = desc.getReturnType() != WeaCType.voidType;
			}
			boolean hasReturnValue = buffer.length() - "return".length() > 0;
			if(hasReturnValue && mustReturnValue && compilingMethod != null)
			{
				String returned = buffer.substring(buffer.lastIndexOf(" ") + 1);
				newPush(instructions, returned);
			}
			else if(!hasReturnValue && mustReturnValue && compilingMethod != null)
			{
				throwCompileError("Method type is not void, must return a value");
			}
			else if(!mustReturnValue && hasReturnValue && compilingMethod != null)
			{
				throwCompileError("Method type is void, returning a value is not possible");
			}
			instructions.add(new BaseInstruction(RETURN));
		}
		else
		{
			if(buffer.contains("="))
			{
				newPush(instructions, buffer.split("=")[1]);
				String var = buffer.split("=")[0];
				while(var.charAt(0) == ' ')
				{
					var = var.substring(1);
				}
				while(var.charAt(var.length() - 1) == ' ')
				{
					var = var.substring(0, var.length() - 1);
				}
				boolean found = false;
				if(!var.contains(" "))
				{
					for(WeaCVariable local : compilingMethod.getLocals())
					{
						if(local.name.equals(var))
						{
							instructions.add(new StoreVariableInstruction(local.index));
							found = true;
							break;
						}
					}
				}
				else
				{
					String[] split = var.split(" ");
					String typeN = split[0];
					while(typeN.charAt(0) == ' ')
					{
						typeN = typeN.substring(1);
					}
					while(typeN.charAt(typeN.length() - 1) == ' ')
					{
						typeN = typeN.substring(0, typeN.length() - 1);
					}
					WeaCType type = WeaCType.get(typeN);
					String name = split[split.length - 1];
					while(name.charAt(0) == ' ')
					{
						name = name.substring(1);
					}
					while(name.charAt(name.length() - 1) == ' ')
					{
						name = typeN.substring(0, name.length() - 1);
					}
					boolean alreadyExists = false;
					int maxIndex = 0;
					for(WeaCVariable local : compilingMethod.getLocals())
					{
						if(local.index > maxIndex)
						{
							maxIndex = local.index;
						}
						if(local.name.equals(name))
						{
							alreadyExists = true;
						}
					}
					if(!alreadyExists)
					{
						WeaCVariable newVariable = new WeaCVariable(maxIndex + 1, name, type, true, false, null);
						compilingMethod.getLocals().add(newVariable);
						instructions.add(new StoreVariableInstruction(maxIndex + 1));
						found = true;
					}
					else
					{
						throwCompileError("Trying to overwrite already existing variable: " + name);
					}
				}
				if(!found)
				{
					throwCompileError(var + " cannot be resolved to a variable");
				}
			}
			else
			{
				newPush(instructions, buffer);
			}
		}
	}

	private Label getNewLabel()
	{
		return new Label(labelNbr++ );
	}

	private void newPush(ArrayList<Instruction> insns, String str) throws WeaCException
	{
		char[] chars = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		Stack<String> methodNames = new Stack<>();
		Stack<String> argStack = new Stack<>();
		int array = 0;
		System.out.println(">>>>>>" + str);
		while(str.charAt(0) == ' ')
			str = str.substring(1);
		while(str.charAt(str.length() - 1) == ' ')
			str = str.substring(0, str.length() - 1);
		for(int i = 0; i < chars.length; i++ )
		{
			char current = chars[i];
			if(current == '(')
			{
				methodNames.push(buffer.toString());
				buffer.delete(0, buffer.length());
			}
			else if(current == ' ')
			{

			}
			else if(current == ',')
			{
				if(array == 0)
				{
					if(buffer.length() > 0)
					{
						argStack.push(buffer.toString());
					}
					else
					{
						argStack.push("null");
					}
					buffer.delete(0, buffer.length());
				}
				else
					buffer.append(current);
			}
			else if(current == ')')
			{
				if(buffer.length() > 0)
				{
					argStack.push(buffer.toString());
				}
				Stack<String> callStack = new Stack<>();
				while(!argStack.isEmpty())
				{
					String pop = argStack.pop();
					callStack.push(pop);
				}
				buffer.delete(0, buffer.length());
				String desc = "*(";
				while(!callStack.isEmpty())
				{
					WeaCType type = newPushVar(insns, callStack.pop());
					if(type == null)
						desc += "*;";
					else
						desc += type.getID() + ";";
				}
				desc += ")";
				String method = methodNames.pop();
				method = method.replace("\n", "").replace("\r", "");
				if(method.length() > 0)
				{
					if(method.equals("if"))
					{
						if(WeaCHelper.countChar(desc, ';') != 1)
						{
							throwCompileError("If conditions only accept one argument");
						}
						insns.add(new IfInstruction(labelStack.push(new Label(-1))));
					}
					else
					{
						String mOwner = "std";

						boolean found = false;
						for(WeaCLib lib : included)
						{
							mOwner = lib.getName();
							if(WeaCHelper.isMethod(lib, method, desc))
							{
								insns.add(new MethodCallInstruction(new WeaCMethod(mOwner, WeaCHelper.getMethodName(method), desc)));
								found = true;
								break;
							}
						}
						if(!found)
						{
							throwCompileError("Unknown method: " + method + " " + desc);
						}
					}
				}
			}
			else
			{
				buffer.append(current);
			}
		}
		if(buffer.length() > 0)
		{
			newPushVar(insns, buffer.toString());
		}
	}

	private WeaCType newPushVar(ArrayList<Instruction> insns, String var) throws WeaCException
	{
		ArrayList<String> l = WeaCHelper.getRPNOutputFromInfix(var, (int)sourceLine);
		WeaCType type = null;
		Stack<Integer> arrayIndices = new Stack<>();
		for(String s : l)
		{
			if(s.equals(",")) continue;
			if(s.equals("{"))
			{
				arrayIndices.push(0);
				continue;
			}
			else if(s.equals("}"))
			{
				arrayIndices.pop();
				continue;
			}
			Operation op = Operation.get(s);
			if(op != null)
			{
				insns.add(new OperationInstruction(op));
			}
			else
			{
				WeaCValue nbr = WeaCHelper.getAsNumber(s);
				if(nbr != null)
				{
					insns.add(new LoadConstantInstruction(nbr));
					type = nbr.type;
					if(type.isCompatible(nbr.type))
					{
						type = nbr.type;
					}
				}
				else
				{
					if(s != null && !s.equals("null"))
					{
						boolean invert = false;
						if(s.startsWith("!"))
						{
							invert = true;
							s = s.substring(1);
						}
						if(s.startsWith("\"") && s.endsWith("\""))
						{
							insns.add(new LoadConstantInstruction(new WeaCValue(s.substring(1, s.length() - 1), WeaCType.stringType)));
						}
						else if(s.equals("true") || s.equals("True") || s.equals("TRUE"))
						{
							insns.add(new LoadConstantInstruction(new WeaCValue(invert ? false : true, WeaCType.boolType)));
						}
						else if(s.equals("false") || s.equals("False") || s.equals("FALSE"))
						{
							insns.add(new LoadConstantInstruction(new WeaCValue(invert ? true : false, WeaCType.boolType)));
						}
						else if(compilingMethod != null)
						{
							boolean found = false;
							for(WeaCVariable variable : compilingMethod.getLocals())
							{
								if(variable.name.equals(s))
								{
									found = true;
									insns.add(new LoadVariableInstruction(variable.index));
									if(invert) insns.add(new BaseInstruction(INVERT_BOOL));
								}
							}
							if(!found) throwCompileError(s + " cannot be resolved to a variable in " + var);
						}
						else
							throwCompileError("Global variables are not allowed");
					}
					else if(s.equals("null"))
					{
						insns.add(new BaseInstruction(LOAD_NULL));
					}
				}

				if(!arrayIndices.isEmpty())
				{
					insns.add(new ArrayStoreInstruction(ARRAY_STORE));
				}
			}
		}
		return type;
	}

	private void throwCompileError(String message) throws WeaCompileException
	{
		throw new WeaCompileException(message + " " + WeaCHelper.getInfos((int)sourceLine));
	}

	public void enterNewLib(String headerName)
	{
		libs.push(currentLib);
		currentLib = headerName;
	}

	public void include(WeaCLib lib)
	{
		included.add(lib);
	}
}
