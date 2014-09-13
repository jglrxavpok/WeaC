package org.jg.weac;

public class WeaCValue
{

	public Object   value;
	public WeaCType type;

	public WeaCValue(Object v, WeaCType type)
	{
		this.type = type;
		this.value = type.correctValue(v);
	}

	public WeaCValue()
	{
	}

	public String toString()
	{
		if(value == null) return "nil";
		return value.toString();
	}

}
