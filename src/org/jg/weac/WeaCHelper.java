package org.jg.weac;

import java.util.*;

public class WeaCHelper
{
	public static final String HEADER_EXTENSION = "wch";
	public static final String IMPL_EXTENSION   = "wci";

	public static int countChar(String s, char c)
	{
		int n = 0;
		for(char c1 : s.toCharArray())
		{
			if(c1 == c)
			{
				n++ ;
			}
		}
		return n;
	}

	public static boolean areDescEquals(String a, String b)
	{
		WeaCType aReturnType = WeaCType.get(a.substring(0, a.indexOf("(")));
		WeaCType bReturnType = WeaCType.get(b.substring(0, b.indexOf("(")));
		if(aReturnType == null || bReturnType == null) return false;
		if(!aReturnType.isCompatible(bReturnType) && !bReturnType.isCompatible(aReturnType))
		{
			return false;
		}
		String aArgs = a.substring(a.indexOf("(") + 1, a.indexOf(")"));
		String bArgs = b.substring(b.indexOf("(") + 1, b.indexOf(")"));
		if(countChar(aArgs, ';') != countChar(bArgs, ';')) return false;
		String[] aTokens = aArgs.split(";");
		String[] bTokens = bArgs.split(";");
		if(aTokens.length != bTokens.length) return false;
		for(int i = 0; i < aTokens.length - 1; i++ )
		{
			WeaCType aType = WeaCType.get(aTokens[i]);
			WeaCType bType = WeaCType.get(bTokens[i]);
			if(!aType.isCompatible(bType) && !bType.isCompatible(aType))
			{
				return false;
			}
		}
		return true;
	}

	public static String getMethodName(String txt)
	{
		String n = txt;
		if(n.contains("::"))
		{
			n = n.substring(n.lastIndexOf("::") + 2);
		}
		return n;
	}

	public static boolean isMethod(WeaCLib lib, String name, String methodDesc)
	{
		String owner = null;
		String n = name;
		if(n.contains("::"))
		{
			owner = n.split("::")[0];
			n = n.substring(n.lastIndexOf("::") + 2);
			if(!owner.equals(lib.getName())) return false;
		}
		for(WeaCMethod m : lib.getMethods())
		{
			if(m.getName().equals(n) && WeaCHelper.areDescEquals(m.getDesc().toString(), methodDesc))
			{
				return true;
			}
		}
		return false;
	}

	public static WeaCValue getAsNumber(String var)
	{
		if(!var.contains("."))
		{
			try
			{
				WeaCType type = WeaCType.intType;
				return new WeaCValue(Integer.parseInt(var), type);
			}
			catch(Exception e)
			{
				;
			}

			try
			{
				WeaCType type = WeaCType.longType;
				return new WeaCValue(Long.parseLong(var), type);
			}
			catch(Exception e)
			{
				;
			}
		}
		else
		{
			try
			{
				WeaCType type = WeaCType.floatType;
				return new WeaCValue(Float.parseFloat(var), type);
			}
			catch(Exception e)
			{
				;
			}
			try
			{
				WeaCType type = WeaCType.doubleType;
				return new WeaCValue(Double.parseDouble(var), type);
			}
			catch(Exception e)
			{
				;
			}
		}

		return null;
	}

	public static ArrayList<String> getRPNOutputFromInfix(String arg, int line) throws WeaCException
	{
		String[] tokens = getTokens(arg, line);

		ArrayList<String> outputQueue = new ArrayList<String>();
		Stack<String> operatorStack = new Stack<String>();
		for(String token : tokens)
		{
			if(token.equals("("))
			{
				operatorStack.push(token);
			}
			else if(token.equals(")"))
			{
				while(!operatorStack.isEmpty())
				{
					String operator = operatorStack.pop();
					if(operator.equals("("))
					{
						break;
					}
					else
					{
						outputQueue.add(operator);
					}
				}
			}
			else
			{
				if(isTokenOperator(token))
				{
					while(!operatorStack.isEmpty())
					{
						String operator = operatorStack.peek();
						if(getPrecedence(operator) >= getPrecedence(token))
						{
							outputQueue.add(operatorStack.pop());
						}
						else
						{
							break;
						}
					}
					operatorStack.push(token);
				}
				else
				{
					outputQueue.add(token);
				}
			}
		}
		while(!operatorStack.isEmpty())
		{
			String operator = operatorStack.pop();
			if(operator.equals("(") || operator.equals(")")) continue;
			outputQueue.add(operator);
		}
		return outputQueue;
	}

	public static boolean isTokenOperator(String token)
	{
		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals(">>") || token.equals("<<") || token.equals(">>>") || token.equals("^") || token.equals("|") || token.equals("&");
	}

	public static int getPrecedence(String operator)
	{
		if(operator.equals("+"))
		{
			return 2;
		}
		else if(operator.equals("-"))
		{
			return 2;
		}
		else if(operator.equals("*"))
		{
			return 3;
		}
		else if(operator.equals("/"))
		{
			return 3;
		}
		else if(operator.equals("%"))
		{
			return 3;
		}
		else if(operator.equals("<<"))
		{
			return 7;
		}
		else if(operator.equals(">>"))
		{
			return 7;
		}
		else if(operator.equals(">>>"))
		{
			return 7;
		}
		else if(operator.equals("&"))
		{
			return 10;
		}
		else if(operator.equals("^"))
		{
			return 11;
		}
		else if(operator.equals("|"))
		{
			return 12;
		}
		return 0;
	}

	public static String[] getTokens(String arg, int line) throws WeaCException
	{
		ArrayList<String> tokensList = new ArrayList<String>();
		String currentNumber = "";
		boolean inDecimalPart = false;
		boolean inString = false;
		StringBuffer buffer = new StringBuffer();

		for(int i = 0; i < arg.length(); i++ )
		{
			char c = arg.charAt(i);
			if(c >= '0' && c <= '9' && !inString)
			{
				if(buffer.length() > 0)
				{
					tokensList.add(buffer.toString());
					buffer.delete(0, buffer.length());
				}
				currentNumber += c;
			}
			else if(c == '"')
			{
				buffer.append('"');
				inString = !inString;
			}
			else if(c == '.' && !inString)
			{
				if(buffer.length() > 0)
				{
					tokensList.add(buffer.toString());
					buffer.delete(0, buffer.length());
				}
				if(!inDecimalPart)
				{
					inDecimalPart = true;
					currentNumber += c;
				}
				else
				{
					throwError("Invalid decimal separator in: " + arg, line);
				}
			}
			else if(isTokenOperator("" + c) && !inString)
			{
				if(buffer.length() > 0)
				{
					tokensList.add(buffer.toString());
					buffer.delete(0, buffer.length());
				}
				if(!isNaN(currentNumber))
				{
					tokensList.add(currentNumber);
					currentNumber = "NaN";
					inDecimalPart = false;
				}
				tokensList.add("" + c);
			}
			else if(isTokenOperator(buffer.toString() + c) && !inString)
			{
				tokensList.add(buffer.toString() + c);
				buffer.delete(0, buffer.length());
			}
			else
			{
				if(!isNaN(currentNumber) && !inString)
				{
					tokensList.add(currentNumber);
					currentNumber = "";
					inDecimalPart = false;
				}
				buffer.append(c);
			}
		}
		if(buffer.length() > 0)
		{
			tokensList.add(buffer.toString());
			buffer.delete(0, buffer.length());
		}
		if(!isNaN(currentNumber))
		{
			tokensList.add(currentNumber);
		}
		tokensList.forEach(System.err::println);
		return tokensList.toArray(new String[0]);
	}

	public static boolean isNaN(String currentNumber)
	{
		if(currentNumber.equals("NaN")) return true;
		if(currentNumber.equals(" ")) return true;
		if(currentNumber.equals("")) return true;
		try
		{
			return Double.isNaN(Double.parseDouble(currentNumber));
		}
		catch(Exception e)
		{
			;
		}
		return true;
	}

	public static String getInfos(int currentLine)
	{
		return "(in __MAIN__ at line:" + currentLine + ")";
	}

	public static void throwError(String message, int line) throws WeaCException
	{
		throw new WeaCException(message + " " + getInfos(line));
	}

}
