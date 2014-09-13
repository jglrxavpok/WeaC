package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public abstract class VarInstruction extends Instruction
{

	private int varIndex;

	public VarInstruction(int opcode, int varIndex)
	{
		super(opcode);
		this.varIndex = varIndex;
	}

	public String toString()
	{
		return super.toString() + " " + varIndex;
	}

	public int getVarIndex()
	{
		return varIndex;
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
