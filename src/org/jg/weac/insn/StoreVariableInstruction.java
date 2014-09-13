package org.jg.weac.insn;

import java.io.*;

public class StoreVariableInstruction extends Instruction
{

	private int index;

	public StoreVariableInstruction(int index)
	{
		super(OpCodes.VAR_STORE);
		this.index = index;
	}

	public int getVarIndex()
	{
		return index;
	}

	public String toString()
	{
		return super.toString() + " " + index;
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(index);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		index = buffer.readInt();
	}
}
