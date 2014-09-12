package org.jg.weac;

public class WeaCVariable extends WeaCValue
{

	public int	 index;
	public boolean isLocal;
	public boolean isGlobal;
	public String  name;

	public WeaCVariable(int index, String name, WeaCType type, boolean isLocal, boolean isGlobal, Object initialValue)
	{
		super(initialValue, type);
		this.index = index;
		this.name = name;
		this.isLocal = isLocal;
		this.isGlobal = isGlobal;
	}
}
