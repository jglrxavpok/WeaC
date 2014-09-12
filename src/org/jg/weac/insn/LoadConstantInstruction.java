package org.jg.weac.insn;

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
}
