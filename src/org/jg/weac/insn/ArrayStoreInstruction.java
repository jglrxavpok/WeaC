package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class ArrayStoreInstruction extends Instruction
{

	private int arrayIndex;

	public ArrayStoreInstruction(int index)
	{
		super(OpCodes.ARRAY_STORE);
		this.arrayIndex = index;
	}

	public int getArrayIndex()
	{
		return arrayIndex;
	}

	public String toString()
	{
		return super.toString() + " " + arrayIndex;
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(arrayIndex);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		arrayIndex = buffer.readInt();
	}

}
