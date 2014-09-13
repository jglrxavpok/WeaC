package org.jg.weac;

import java.io.*;

import org.jg.weac.insn.*;

public class WeaCWriter
{

	private WeaCode code;

	public WeaCWriter(WeaCode code)
	{
		this.code = code;
	}

	public void write(OutputStream out) throws IOException
	{
		WeaCBuffer buffer = new WeaCBuffer();
		for(Instruction in : code.getInstructions())
		{
			buffer.writeInt(in.getOpcode());
			in.writeInfos(buffer);
		}
		for(WeaCLib lib : code.getRequiredLibs())
		{
			buffer.writeInt(-1);
			buffer.writeString(lib.getName());
		}
		out.write(buffer.toBytes());
	}
}
