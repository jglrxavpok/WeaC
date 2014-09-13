package org.jg.weac.insn;

import java.io.*;

public abstract class VarInstruction extends Instruction
{

	private int varIndex;

	public VarInstruction(int opcode, int varIndex)
	{
		super(opcode);
		this.varIndex = varIndex;
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
