package org.jg.weac.insn;

import java.io.*;

public class LoadVariableInstruction extends Instruction
{

	private int varIndex;

	public LoadVariableInstruction(int index)
	{
		super(OpCodes.VAR_LOAD);
		this.varIndex = index;
	}

	public int getVarIndex()
	{
		return varIndex;
	}

	public String toString()
	{
		return super.toString() + " " + varIndex;
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(varIndex);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		varIndex = buffer.readInt();
	}

}
