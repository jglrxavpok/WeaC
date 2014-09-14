package org.jg.weac.types;

import org.jg.weac.*;
import org.jg.weac.values.*;

public class ArrayType extends WeaCType
{

	private WeaCType arrayType;

	public ArrayType(String id)
	{
		super(id);
		arrayType = WeaCType.get(id.substring(0, id.lastIndexOf("[")));
	}

	public WeaCType getArrayType()
	{
		return arrayType;
	}

	public WeaCValue newValue(Object v)
	{
		ArrayValue value = new ArrayValue(this);
		if(v instanceof ArrayValue)
		{
			ArrayValue given = (ArrayValue)v;
			int n;
			try
			{
				n = (int)given.getField("length").getValue();
				for(int i = 0; i < n; i++ )
				{
					value.addValue(given.getValue(i));
				}
			}
			catch(NoSuchFieldException e)
			{
				e.printStackTrace();
			}
		}
		return value;
	}

	public boolean isCompatible(WeaCType other)
	{
		if(other == null)
		{
			return false;
		}
		if(other instanceof ArrayType)
		{
			return arrayType.isCompatible(((ArrayType)other).arrayType);
		}
		else
			return false;
	}
}
