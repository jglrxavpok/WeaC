package org.jg.weac;

import java.util.*;

import org.jg.weac.types.*;

public class WeaCType
{

	public static class NumberType extends WeaCType
	{

		public NumberType(String id)
		{
			super(id);
		}

		public WeaCValue add(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) + (Integer)convert(b.value, this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.value, this) + (Float)convert(b.value, this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.value, this) + (Double)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) + (Long)convert(b.value, this));
				}
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue sub(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) - (Integer)convert(b.value, this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.value, this) - (Float)convert(b.value, this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.value, this) - (Double)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) - (Long)convert(b.value, this));
				}
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue mul(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) * (Integer)convert(b.value, this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.value, this) * (Float)convert(b.value, this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.value, this) * (Double)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) * (Long)convert(b.value, this));
				}
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue div(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) / (Integer)convert(b.value, this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.value, this) / (Float)convert(b.value, this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.value, this) / (Double)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) / (Long)convert(b.value, this));
				}
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue mod(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) % (Integer)convert(b.value, this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.value, this) % (Float)convert(b.value, this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.value, this) % (Double)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) % (Long)convert(b.value, this));
				}
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue lsh(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) << (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) << (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue rsh(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) >> (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) >> (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue ursh(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) >>> (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) >>> (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue bitAnd(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) & (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) & (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue bitOr(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) | (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) | (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public WeaCValue bitXor(WeaCValue a, WeaCValue b)
		{
			if(this.isCompatible(a.type) && this.isCompatible(b.type))
			{
				Object newVal = null;
				if(getID().equals("int"))
				{
					newVal = ((Integer)convert(a.value, this) ^ (Integer)convert(b.value, this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.value, this) ^ (Long)convert(b.value, this));
				}
				else
					return null;
				return new WeaCValue(newVal, WeaCType.intType);
			}
			return null;
		}

		public static Object convert(Object value, WeaCType type)
		{
			if(value == null)
			{
				if(type == WeaCType.intType)
				{
					return 0;
				}
				if(type == WeaCType.longType)
				{
					return 0L;
				}
				if(type == WeaCType.floatType)
				{
					return 0f;
				}
				if(type == WeaCType.doubleType)
				{
					return .0;
				}
			}
			if(type.equals(WeaCType.intType))
			{
				if(value instanceof Double)
				{
					return (int)((double)value);
				}
				if(value instanceof Integer)
				{
					return (int)value;
				}
				if(value instanceof Float)
				{
					return (int)((float)value);
				}
				if(value instanceof Long)
				{
					return (int)((long)value);
				}
			}
			if(type.equals(WeaCType.longType))
			{
				if(value instanceof Double)
				{
					return (long)((double)value);
				}
				if(value instanceof Integer)
				{
					return (long)((int)value);
				}
				if(value instanceof Float)
				{
					return (long)((float)value);
				}
				if(value instanceof Long)
				{
					return (long)((long)value);
				}
			}
			if(type.equals(WeaCType.floatType))
			{
				if(value instanceof Double)
				{
					return (float)((double)value);
				}
				if(value instanceof Integer)
				{
					return (float)((int)value);
				}
				if(value instanceof Float)
				{
					return (float)((float)value);
				}
				if(value instanceof Long)
				{
					return (float)((long)value);
				}
			}
			if(type.equals(WeaCType.doubleType))
			{
				if(value instanceof Double)
				{
					return (double)((double)value);
				}
				if(value instanceof Integer)
				{
					return (double)((int)value);
				}
				if(value instanceof Float)
				{
					return (double)((float)value);
				}
				if(value instanceof Long)
				{
					return (double)((long)value);
				}
			}
			return null;
		}

	}

	private static HashMap<String, WeaCType> registred	= new HashMap<>();

	public static final WeaCType			 intType	  = new NumberType("int");
	public static final WeaCType			 longType	 = new NumberType("long");
	public static final WeaCType			 voidType	 = new WeaCType("void");
	public static final WeaCType			 floatType	= new NumberType("float");
	public static final WeaCType			 doubleType   = new NumberType("double");
	public static final WeaCType			 stringType   = new StringType("string");
	public static final WeaCType			 wildcardType = new WeaCType("*")
														  {
															  public boolean isCompatible(WeaCType other)
															  {
																  return true;
															  }
														  };

	static
	{
		intType.addCompatible(longType);
		intType.addCompatible(floatType);
		intType.addCompatible(doubleType);

		longType.addCompatible(floatType);
		longType.addCompatible(doubleType);

		floatType.addCompatible(doubleType);
	}

	public static WeaCType get(String id)
	{
		if(registred.containsKey(id))
		{
			return registred.get(id);
		}
		return null;
	}

	private String			  id;
	private ArrayList<WeaCType> compatibles;

	public WeaCType(String id)
	{
		compatibles = new ArrayList<>();
		this.id = id;
		registred.put(id, this);
	}

	public String getID()
	{
		return id;
	}

	/**
	 * 
	 */
	public boolean isCompatible(WeaCType other)
	{
		if(other == null)
		{
			return false;
		}
		return other.compatibles.contains(this) || other == this;
	}

	public void addCompatible(WeaCType other)
	{
		compatibles.add(other);
	}

	public boolean equals(Object o)
	{
		if(o instanceof String)
		{
			return ((String)o).equals(id);
		}
		else if(o instanceof WeaCType)
		{
			return ((WeaCType)o).getID().equals(id);
		}
		return false;
	}

	public WeaCValue add(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue sub(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue mul(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue div(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue mod(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue lsh(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue rsh(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue ursh(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue bitAnd(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue bitOr(WeaCValue a, WeaCValue b)
	{
		return a;
	}

	public WeaCValue bitXor(WeaCValue a, WeaCValue b)
	{
		return a;
	}
}
