package org.jg.weac;

import org.jg.weac.libs.*;

public abstract class WeaCLib
{

	public abstract String getName();

	public abstract WeaCMethod[] getMethods();

	public boolean isCompiledDirectly()
	{
		return false;
	}

	public static final WeaCLib getStandardLib(String name)
	{
		switch(name)
		{
			case "math":
				return new MathLib();

			case "time":
				return new TimeLib();

			case "std":
				return new StdLib();
		}
		return null;
	}
}
