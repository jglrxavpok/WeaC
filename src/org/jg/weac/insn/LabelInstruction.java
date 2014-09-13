package org.jg.weac.insn;

import java.io.*;

import org.jg.weac.*;

public class LabelInstruction extends Instruction
{

	public static class Label
	{
		private int nbr;

		public Label(int nbr)
		{
			this.nbr = nbr;
		}

		public int getNbr()
		{
			return nbr;
		}

		public boolean equals(Object o)
		{
			if(o instanceof Label)
			{
				return ((Label)o).nbr == nbr;
			}
			return false;
		}

		public void setNbr(int i)
		{
			this.nbr = i;
		}
	}

	private Label label;

	public LabelInstruction(Label label)
	{
		super(OpCodes.LABEL);
		this.label = label;
	}

	public Label getLabel()
	{
		return label;
	}

	public String toString()
	{
		return super.toString() + " " + label.getNbr();
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeInt(label.getNbr());
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		label = new Label(buffer.readInt());
	}

}
