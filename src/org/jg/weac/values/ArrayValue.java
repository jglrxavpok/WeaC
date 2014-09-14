package org.jg.weac.values;

import java.util.*;

import org.jg.weac.*;

public class ArrayValue extends WeaCValue
{

	private ArrayList<WeaCValue> values = new ArrayList<>();

	public ArrayValue(WeaCType type)
	{
		super(null, WeaCType.get(type.getID() + "[]"));
	}

	public Object getValue()
	{
		return this;
	}

	public WeaCValue getField(String name) throws NoSuchFieldException
	{
		if(name.equals("length"))
		{
			return new WeaCValue(values.size(), WeaCType.intType);
		}
		if(name.startsWith("__"))
		{
			int index = Integer.parseInt(name.substring(2));
			return values.get(index);
		}
		return super.getField(name);
	}

	public String toString()
	{
		String s = "";
		for(WeaCValue val : values)
		{
			if(s.length() > 0)
			{
				s += "," + val.toString();
			}
			else
				s = val.toString();
		}
		return "array(" + values.size() + "){" + s + "}";
	}

	public void addValue(WeaCValue pop)
	{
		values.add(pop);
	}

	public WeaCValue getValue(int i)
	{
		return values.get(i);
	}
}
