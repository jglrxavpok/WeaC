package org.jg.weac;

public class WeaCVariable
{

	public int	   index;
	public boolean   isLocal;
	public boolean   isGlobal;
	public String	name;
	public WeaCValue value;
	public WeaCType  type;

	public WeaCVariable(int index, String name, WeaCType type, boolean isLocal, boolean isGlobal, WeaCValue initialValue)
	{
		this.type = type;
		this.value = initialValue;
		this.index = index;
		this.name = name;
		this.isLocal = isLocal;
		this.isGlobal = isGlobal;
	}

	public String toString()
	{
		if(value == null) return "nil";
		return value.toString();
	}
}
