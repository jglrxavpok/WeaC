package org.jg.weac;

import java.util.*;

import org.jg.weac.insn.*;

public class WeaCode
{

	private ArrayList<WeaCLib>	 required;
	private ArrayList<Instruction> instructions;

	public WeaCode(ArrayList<WeaCLib> included, ArrayList<Instruction> instructions)
	{
		this.required = included;
		this.instructions = instructions;
	}

	public ArrayList<WeaCLib> getRequiredLibs()
	{
		return required;
	}

	public ArrayList<Instruction> getInstructions()
	{
		return instructions;
	}

}
