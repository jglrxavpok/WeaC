package org.jg.weac.insn;

public class StoreVariableInstruction extends VarInstruction
{

	public StoreVariableInstruction(int index)
	{
		super(OpCodes.VAR_STORE, index);
	}
}
