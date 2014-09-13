package org.jg.weac;

public abstract class WeaCLib
{

	public abstract String getName();

	public abstract WeaCMethod[] getMethods();

	public boolean isCompiledDirectly()
	{
		return false;
	}
}
