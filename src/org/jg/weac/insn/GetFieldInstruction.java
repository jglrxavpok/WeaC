package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class GetFieldInstruction extends Instruction
{

	private String field;

	public GetFieldInstruction(String field)
	{
		super(OpCodes.GET_FIELD);
		this.field = field;
	}

	public String getField()
	{
		return field;
	}

	public String toString()
	{
		return super.toString() + " " + field;
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeString(field);
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		field = buffer.readString();
	}
}
