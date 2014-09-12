package org.jg.weac.insn;

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

}
