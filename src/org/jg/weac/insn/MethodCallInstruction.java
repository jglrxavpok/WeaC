package org.jg.weac.insn;

import org.jg.weac.*;

public class MethodCallInstruction extends Instruction
{

	private WeaCMethod method;

	public MethodCallInstruction(WeaCMethod method)
	{
		super(OpCodes.METHOD_CALL);
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
