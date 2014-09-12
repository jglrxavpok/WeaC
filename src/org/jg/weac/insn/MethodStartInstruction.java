package org.jg.weac.insn;

import org.jg.weac.*;

public class MethodStartInstruction extends Instruction
{

	private WeaCMethod method;

	public MethodStartInstruction(WeaCMethod method)
	{
		super(OpCodes.METHOD_START);
		this.method = method;
	}

	public WeaCMethod getMethod()
	{
		return method;
	}

	public String toString()
	{
		return super.toString() + " " + method.getOwner() + "::" + method.getName() + " " + method.getDesc();
	}

}
