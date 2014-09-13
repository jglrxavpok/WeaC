package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;
import org.jg.weac.insn.LabelInstruction.Label;

public class JumpInstruction extends Instruction
{
	private Label gotoLabel;

	public JumpInstruction(int opcode, Label gotoLabel)
	{
		super(opcode);
		this.gotoLabel = gotoLabel;
	}

	public Label getGotoLabel()
	{
		return gotoLabel;
	}

	public String toString()
	{
		return super.toString() + " " + gotoLabel.getNbr();
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(gotoLabel.getNbr());
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		if(gotoLabel == null)
			gotoLabel = new Label(buffer.readInt());
		else
			gotoLabel.setNbr(buffer.readInt());
	}

}
