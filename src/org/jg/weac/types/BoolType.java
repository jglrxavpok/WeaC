package org.jg.weac.types;

import org.jg.weac.*;

public class BoolType extends WeaCType
{
	public final WeaCValue TRUE  = new WeaCValue(true, this);
	public final WeaCValue FALSE = new WeaCValue(false, this);

	public BoolType(String id)
	{
		super(id);
	}

	public boolean equals(Object o)
	{
		if(o instanceof BoolType)
		{
			return true;
		}
		return super.equals(o);
	}

	public boolean equal(WeaCValue a, WeaCValue b)
	{
		if(a.getValue() instanceof Boolean && b.getValue() instanceof Boolean) return a.getValue() == b.getValue();
		if(a.type == this && b.type == this)
		{
			return a.getValue() == b.getValue();
		}
		else if(a.type == this)
		{
			return ((int)NumberType.convert(b.getValue(), WeaCType.intType) != 0) == (boolean)a.getValue();
		}
		else if(b.type == this)
		{
			return ((int)NumberType.convert(a.getValue(), WeaCType.intType) != 0) == (boolean)b.getValue();
		}
		return false;
	}

	public WeaCValue add(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue sub(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue mul(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue div(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue mod(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue lsh(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue rsh(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue ursh(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue bitAnd(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue bitOr(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public WeaCValue bitXor(WeaCValue a, WeaCValue b)
	{
		return null;
	}

	public boolean isCompatible(WeaCType other)
	{
		return true;
	}

	public Object correctValue(Object v)
	{
		if(v instanceof Integer)
			return ((Integer)v != 0);
		else if(v instanceof Float)
			return ((Float)v != 0f);
		else if(v instanceof Long)
			return ((Long)v != 0L);
		else if(v instanceof Double)
			return ((Double)v != 0.0);
		else if(v instanceof Boolean)
			return v;
		else
			return v != null;
	}
}
