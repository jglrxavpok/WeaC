package org.jg.weac.insn;

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
}
