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
		return new WeaCValue(a.getValue() + "" + b.getValue(), stringType);
	}

	public WeaCValue sub(WeaCValue a, WeaCValue b)
	{
		String value = "" + a.getValue();
		return new WeaCValue(value.replace(b.getValue() + "", ""), stringType);
	}

	public boolean equal(WeaCValue a, WeaCValue b)
	{
		return ("" + a.getValue()).equals(b.getValue() + "");
	}
}
