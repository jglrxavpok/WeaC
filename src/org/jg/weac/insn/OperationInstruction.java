package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class OperationInstruction extends Instruction
{

	public static enum Operation
	{
		ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/"), MODULO("%"), LEFT_SHIFT("<<"), RIGHT_SHIFT(">>"), UNSIGNED_RIGHT_SHIFT(">>>"), BITWISE_AND("&"), BITWISE_OR("|"), BITWISE_XOR("^");

		private String id;

		Operation(String id)
		{
			this.id = id;
		}

		public String getID()
		{
			return id;
		}

		public static Operation get(String id)
		{
			for(Operation v : values())
			{
				if(v.getID().equals(id))
				{
					return v;
				}
			}
			return null;
		}
	}

	private Operation operation;

	public OperationInstruction(Operation op)
	{
		super(OpCodes.OPERATION);
		this.operation = op;
	}

	public Operation getOperation()
	{
		return operation;
	}

	public String toString()
	{
		return super.toString() + " " + operation.getID();
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeString(operation.getID());
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		operation = Operation.get(buffer.readString());
	}
}
