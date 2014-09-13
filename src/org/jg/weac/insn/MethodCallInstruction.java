package org.jg.weac.insn;

import org.jg.weac.*;

public class MethodCallInstruction extends MethodInstruction
{

	public MethodCallInstruction(WeaCMethod method)
	{
		super(OpCodes.METHOD_CALL, method);
	}

}
