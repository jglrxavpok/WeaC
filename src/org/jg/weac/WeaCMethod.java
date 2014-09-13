package org.jg.weac;

import java.io.*;
import java.util.*;

import org.jg.weac.insn.*;

public class WeaCMethod
{

	public static class MethodDesc
	{
		private String   from;
		private WeaCType returnType;

		public MethodDesc(String from)
		{
			this.from = from;
			String returnTypeStr = from.substring(0, from.indexOf("("));
			returnType = WeaCType.get(returnTypeStr);
		}

		public MethodDesc(WeaCType returnType, WeaCType... args)
		{
			this.returnType = returnType;
			this.from = returnType.getID() + "(";
			for(WeaCType type : args)
			{
				from += type.getID() + ";";
			}
			from += ")";
		}

		public String toString()
		{
			return from;
		}

		public WeaCType getReturnType()
		{
			return returnType;
		}

	}

	private String				  owner;
	private String				  name;
	private MethodDesc			  desc;
	private ArrayList<WeaCVariable> locals;

	public WeaCMethod(String owner, String name, String desc)
	{
		this(owner, name, new MethodDesc(desc.replace("\n", "").replace("\r", "")));
	}

	public WeaCMethod(String owner, String name, MethodDesc desc)
	{
		this.owner = owner.replace("\n", "").replace("\r", "");
		this.name = name.replace("\n", "").replace("\r", "");
		this.desc = desc;
		locals = new ArrayList<>();
	}

	public String getOwner()
	{
		return owner;
	}

	public String getName()
	{
		return name;
	}

	public MethodDesc getDesc()
	{
		return desc;
	}

	public WeaCValue invoke(WeaCValue... args)
	{
		return null;
	}

	public ArrayList<WeaCVariable> getLocals()
	{
		return locals;
	}

	public void addLocals(ArrayList<WeaCVariable> varList)
	{
		locals.addAll(varList);
	}

	public void writeInfos(WeaCBuffer buffer) throws IOException
	{
		buffer.writeString(owner);
		buffer.writeString(name);
		buffer.writeString(desc.toString());
		buffer.writeInt(locals.size());
		for(WeaCVariable local : locals)
		{
			buffer.writeInt(local.index);
			buffer.writeString(local.name);
			buffer.writeString(local.type.getID());
		}
	}

	public void readInfos(WeaCBuffer buffer) throws IOException
	{
		owner = buffer.readString();
		name = buffer.readString();
		desc = new MethodDesc(buffer.readString());
		int n = buffer.readInt();
		for(int i = 0; i < n; i++ )
		{
			locals.add(new WeaCVariable(buffer.readInt(), buffer.readString(), WeaCType.get(buffer.readString()), true, false, null));
		}
	}
}
