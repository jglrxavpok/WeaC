package org.jg.weac.libs;

import org.jg.weac.*;

public class StdLib extends WeaCLib
{

	private static class Stdout extends WeaCMethod
	{

		public Stdout()
		{
			super("std", "out", "void(*;)");
		}

		public WeaCValue invoke(WeaCValue... args)
		{
			System.out.println("[Program Out] " + args[0].toString() + " type is " + args[0].type.getID() + " & instanceof " + args[0].getClass().getCanonicalName());
			return null;
		}

	}

	@Override
	public String getName()
	{
		return "std";
	}

	@Override
	public WeaCMethod[] getMethods()
	{
		return new WeaCMethod[]
		{
			new Stdout()
		};
	}
}
