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
					newVal = ((Integer)convert(a.getValue(), this) + (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.getValue(), this) + (Float)convert(b.getValue(), this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.getValue(), this) + (Double)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) + (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) - (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.getValue(), this) - (Float)convert(b.getValue(), this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.getValue(), this) - (Double)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) - (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) * (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.getValue(), this) * (Float)convert(b.getValue(), this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.getValue(), this) * (Double)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) * (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) / (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.getValue(), this) / (Float)convert(b.getValue(), this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.getValue(), this) / (Double)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) / (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) % (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("float"))
				{
					newVal = ((Float)convert(a.getValue(), this) % (Float)convert(b.getValue(), this));
				}
				else if(getID().equals("double"))
				{
					newVal = ((Double)convert(a.getValue(), this) % (Double)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) % (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) << (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) << (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) >> (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) >> (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) >>> (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) >>> (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) & (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) & (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) | (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) | (Long)convert(b.getValue(), this));
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
					newVal = ((Integer)convert(a.getValue(), this) ^ (Integer)convert(b.getValue(), this));
				}
				else if(getID().equals("long"))
				{
					newVal = ((Long)convert(a.getValue(), this) ^ (Long)convert(b.getValue(), this));
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

		public boolean isCompatible(WeaCType other)
		{
			if(other == null) return false;
			return other instanceof NumberType || other == WeaCType.wildcardType;
		}

	}

	private static HashMap<String, WeaCType> registred	= new HashMap<>();

	public static final NumberType		   intType	  = new NumberType("int");
	public static final NumberType		   longType	 = new NumberType("long");
	public static final WeaCType			 voidType	 = new WeaCType("void");
	public static final NumberType		   floatType	= new NumberType("float");
	public static final NumberType		   doubleType   = new NumberType("double");
	public static final StringType		   stringType   = new StringType("string");
	public static final BoolType			 boolType	 = new BoolType("bool");
	public static final BoolType			 booleanType  = new BoolType("boolean");
	public static final WeaCType			 wildcardType = new WeaCType("*")
														  {
															  public boolean isCompatible(WeaCType other)
															  {
																  if(other == null)
																  {
																	  return false;
																  }
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
		if(id.endsWith("[]"))
		{
			registred.put(id, new ArrayType(id));
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
		if(other.getID().contains("[]"))
		{
			WeaCType other1 = WeaCType.get(other.getID().replace("[]", ""));
			if(WeaCHelper.countChar(id, '[') == WeaCHelper.countChar(other.getID(), '['))
				return other1.isCompatible(WeaCType.get(id.replace("[]", "")));
			else
				return false;
		}
		return other.compatibles.contains(this) || other == this || other == wildcardType;
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

	public boolean equal(WeaCValue a, WeaCValue b)
	{
		return a.getValue() == b.getValue();
	}

	public Object correctValue(Object v)
	{
		return v;
	}

	public WeaCValue newValue(Object v)
	{
		return new WeaCValue(v, this);
	}
}
