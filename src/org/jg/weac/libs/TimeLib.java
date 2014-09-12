package org.jg.weac.libs;

import org.jg.weac.*;

public class TimeLib extends WeaCLib
{

	public TimeLib()
	{
	}

	public class TimestampMethod extends WeaCMethod
	{

		public TimestampMethod()
		{
			super(TimeLib.this.getName(), "timestamp", "long()");
		}

		public WeaCValue invoke(WeaCValue... args)
		{
			return new WeaCValue(System.currentTimeMillis(), WeaCType.longType);
		}

	}

	@Override
	public String getName()
	{
		return "time";
	}

	@Override
	public WeaCMethod[] getMethods()
	{
		return new WeaCMethod[]
		{
			new TimestampMethod()
		};
	}
}
