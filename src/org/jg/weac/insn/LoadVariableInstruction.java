package org.jg.weac.insn;

public class LoadVariableInstruction extends VarInstruction
{
	public LoadVariableInstruction(int index)
	{
		super(OpCodes.VAR_LOAD, index);
	}
}
