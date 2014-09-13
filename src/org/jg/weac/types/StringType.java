package org.jg.weac.types;

import org.jg.weac.*;

public class StringType extends WeaCType
{

	public StringType(String id)
	{
		super(id);
	}

	public WeaCValue add(WeaCValue a, WeaCValue b)
	{
		return new WeaCValue(a.value + "" + b.value, stringType);
	}

	public WeaCValue sub(WeaCValue a, WeaCValue b)
	{
		String value = "" + a.value;
		return new WeaCValue(value.replace(b.value + "", ""), stringType);
	}

	public boolean equal(WeaCValue a, WeaCValue b)
	{
		return ("" + a.value).equals(b.value + "");
	}
}
