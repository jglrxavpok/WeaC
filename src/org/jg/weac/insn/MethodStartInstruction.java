package org.jg.weac.insn;

import org.jg.weac.*;

public class MethodStartInstruction extends MethodInstruction
{

	public MethodStartInstruction(WeaCMethod method)
	{
		super(OpCodes.METHOD_START, method);
	}

}
