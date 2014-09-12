package org.jg.weac.libs;

import org.jg.weac.*;
import org.jg.weac.WeaCType.NumberType;

public class MathLib extends WeaCLib
{

	public class AbsMethod extends WeaCMethod
	{

		public AbsMethod()
		{
			super("math", "abs", new MethodDesc(WeaCType.doubleType, WeaCType.doubleType));
		}

		public WeaCValue invoke(WeaCValue... args)
		{
			WeaCValue toRaise = args[0];
			return new WeaCValue(Math.sqrt((double)NumberType.convert(toRaise.value, WeaCType.doubleType)), WeaCType.doubleType);
		}
	}

	public class SqrtMethod extends WeaCMethod
	{
		public SqrtMethod()
		{
			super("math", "sqrt", new MethodDesc(WeaCType.doubleType, WeaCType.doubleType));
		}

		public WeaCValue invoke(WeaCValue... args)
		{
			WeaCValue toRaise = args[0];
			return new WeaCValue(Math.sqrt((double)NumberType.convert(toRaise.value, WeaCType.doubleType)), WeaCType.doubleType);
		}
	}

	public class PowMethod extends WeaCMethod
	{
		public PowMethod()
		{
			super("math", "pow", new MethodDesc(WeaCType.doubleType, WeaCType.doubleType, WeaCType.intType));
		}

		public WeaCValue invoke(WeaCValue... args)
		{
			WeaCValue toRaise = args[0];
			WeaCValue power = args[1];
			return new WeaCValue(Math.pow((double)NumberType.convert(toRaise.value, WeaCType.doubleType), (int)NumberType.convert(power.value, WeaCType.intType)), WeaCType.doubleType);
		}
	}

	@Override
	public String getName()
	{
		return "math";
	}

	@Override
	public WeaCMethod[] getMethods()
	{
		return new WeaCMethod[]
		{
				new PowMethod(), new SqrtMethod(), new AbsMethod()
		};
	}
}
