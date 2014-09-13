package org.jg.weac.insn;

import java.io.*;

public abstract class Instruction implements OpCodes
{

	private int opcode;

	public Instruction(int opcode)
	{
		this.opcode = opcode;
	}

	public int getOpcode()
	{
		return opcode;
	}

	public String toString()
	{
		return OpCodes.toString(opcode);
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{

	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{

	}
}
