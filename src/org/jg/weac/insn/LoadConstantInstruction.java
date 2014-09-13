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
		if(constant.type == WeaCType.stringType)
		{
			constStr = "\"" + constStr + "\"";
		}
		return super.toString() + " " + constStr;
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		constant = new WeaCValue();
		constant.type = WeaCType.get(buffer.readString());
		if(constant.type == WeaCType.intType)
		{
			constant.value = buffer.readInt();
		}
		if(constant.type == WeaCType.floatType)
		{
			constant.value = buffer.readFloat();
		}
		else if(constant.type == WeaCType.longType)
		{
			constant.value = buffer.readLong();
		}
		else if(constant.type == WeaCType.doubleType)
		{
			constant.value = buffer.readDouble();
		}
		else if(constant.type == WeaCType.stringType)
		{
			constant.value = buffer.readString();
		}
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeString(constant.type.getID());
		if(constant.type == WeaCType.intType)
		{
			buffer.writeInt((int)constant.value);
		}
		if(constant.type == WeaCType.floatType)
		{
			buffer.writeFloat((float)constant.value);
		}
		else if(constant.type == WeaCType.longType)
		{
			buffer.writeLong((long)constant.value);
		}
		else if(constant.type == WeaCType.doubleType)
		{
			buffer.writeDouble((double)constant.value);
		}
		else if(constant.type == WeaCType.stringType)
		{
			buffer.writeString((String)constant.value);
		}
	}
}
