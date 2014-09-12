package org.jg.weac.insn;

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
}
