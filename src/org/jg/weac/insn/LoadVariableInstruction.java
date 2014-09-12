package org.jg.weac.insn;

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

}
