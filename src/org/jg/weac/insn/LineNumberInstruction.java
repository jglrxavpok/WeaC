package org.jg.weac.insn;

import java.io.*;

public class LineNumberInstruction extends Instruction
{

	private int line;

	public LineNumberInstruction(int line)
	{
		super(OpCodes.LINE_NUMBER);
		this.line = line;
	}

	public int getLine()
	{
		return line;
	}

	public String toString()
	{
		return super.toString() + " " + line;
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(line);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		line = buffer.readInt();
	}

}
