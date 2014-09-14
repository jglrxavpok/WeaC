package org.jg.weac;

import java.io.*;
import java.util.*;

import org.jg.weac.insn.*;

public class WeaCReader implements OpCodes
{

	public WeaCReader()
	{

	}

	public WeaCode read(InputStream stream) throws IOException
	{
		WeaCBuffer buffer = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] dataBuffer = new byte[65565];
		int i;
		while((i = stream.read(dataBuffer)) != -1)
		{
			out.write(dataBuffer, 0, i);
		}
		out.flush();
		out.close();
		buffer = new WeaCBuffer(out.toByteArray());
		ArrayList<Instruction> instructions = new ArrayList<>();
		ArrayList<WeaCLib> included = new ArrayList<>();
		while(buffer.isReadable())
		{
			int opcode = buffer.readInt();
			Instruction in = null;
			switch(opcode)
			{
				case -1:
					WeaCLib lib = WeaCLib.getStandardLib(buffer.readString());
					if(lib != null)
					{
						included.add(lib);
					}
					continue;
				case LINE_NUMBER:
					in = new LineNumberInstruction(0);
					break;
				case LOAD_CONST:
					in = new LoadConstantInstruction(null);
					break;
				case METHOD_CALL:
					in = new MethodCallInstruction(null);
					break;
				case METHOD_START:
					in = new MethodStartInstruction(null);
					break;
				case OPERATION:
					in = new OperationInstruction(null);
					break;
				case VAR_LOAD:
					in = new LoadVariableInstruction(-1);
					break;
				case VAR_STORE:
					in = new StoreVariableInstruction(-1);
					break;
				case LABEL:
					in = new LabelInstruction(null);
					break;
				case IF:
					in = new IfInstruction(null);
					break;
				case ARRAY_STORE:
					in = new ArrayStoreInstruction(0);
					break;
				case NEW_ARRAY:
					in = new NewArrayInstruction();
					break;
				case GET_FIELD:
					in = new GetFieldInstruction(null);
					break;
				default:
					in = new BaseInstruction(opcode);
					break;
			}
			in.readInfos(buffer);
			instructions.add(in);
		}
		WeaCode code = new WeaCode(included, instructions);
		return code;
	}

}
