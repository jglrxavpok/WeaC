package org.jg.weac.insn;

import org.jg.weac.insn.LabelInstruction.Label;

public class IfInstruction extends JumpInstruction
{

	public IfInstruction(Label gotoLabel)
	{
		super(OpCodes.IF, gotoLabel);
	}

}
