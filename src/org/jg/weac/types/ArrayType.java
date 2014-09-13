package org.jg.weac.types;

import org.jg.weac.*;

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

}
