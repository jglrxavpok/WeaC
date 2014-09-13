package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class MethodInstruction extends Instruction
{

	private WeaCMethod method;

	public MethodInstruction(int opcode, WeaCMethod m)
	{
		super(opcode);
		this.method = m;
	}

	public WeaCMethod getMethod()
	{
		return method;
	}

	public String toString()
	{
		return super.toString() + " " + method.getOwner() + "::" + method.getName() + " " + method.getDesc();
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		method.writeInfos(buffer);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		method = new WeaCMethod("", "", "void()");
		method.readInfos(buffer);
	}

}
