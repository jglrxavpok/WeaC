package org.jg.weac;

public class WeaCValue
{

	public Object   value;
	public WeaCType type;

	public WeaCValue(Object v, WeaCType type)
	{
		this.value = v;
		this.type = type;
	}

	public String toString()
	{
		if(value == null) return "null";
		return value.toString();
	}

}
