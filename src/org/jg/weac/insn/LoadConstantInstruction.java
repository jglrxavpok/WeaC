package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class LoadConstantInstruction extends Instruction
{

	private WeaCValue constant;

	public LoadConstantInstruction(WeaCValue constant)
	{
		super(OpCodes.LOAD_CONST);
		this.constant = constant;
	}

	public WeaCValue getConstant()
	{
		return constant;
	}

	public String toString()
	{
		String constStr = constant.toString();
		if(constant.type.equals(WeaCType.stringType))
		{
			constStr = "\"" + constStr + "\"";
		}
		return super.toString() + " " + constStr;
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		WeaCType type = WeaCType.get(buffer.readString());
		Object value = null;
		if(type.equals(WeaCType.boolType))
		{
			value = (buffer.readBoolean());
		}
		if(type.equals(WeaCType.intType))
		{
			value = (buffer.readInt());
		}
		if(type.equals(WeaCType.floatType))
		{
			value = (buffer.readFloat());
		}
		else if(type.equals(WeaCType.longType))
		{
			value = (buffer.readLong());
		}
		else if(type.equals(WeaCType.doubleType))
		{
			value = (buffer.readDouble());
		}
		else if(type.equals(WeaCType.stringType))
		{
			value = (buffer.readString());
		}
		constant = type.newValue(value);
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeString(constant.type.getID());
		if(constant.type.equals(WeaCType.boolType))
		{
			buffer.writeBoolean((boolean)constant.getValue());
		}
		if(constant.type.equals(WeaCType.intType))
		{
			buffer.writeInt((int)constant.getValue());
		}
		if(constant.type.equals(WeaCType.floatType))
		{
			buffer.writeFloat((float)constant.getValue());
		}
		else if(constant.type.equals(WeaCType.longType))
		{
			buffer.writeLong((long)constant.getValue());
		}
		else if(constant.type.equals(WeaCType.doubleType))
		{
			buffer.writeDouble((double)constant.getValue());
		}
		else if(constant.type.equals(WeaCType.stringType))
		{
			buffer.writeString((String)constant.getValue());
		}
	}
}
