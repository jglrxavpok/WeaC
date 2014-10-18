package org.jg.weac;

public class WeaCValue
{

    private Object  value;
    public WeaCType type;

    public WeaCValue(Object v, WeaCType type)
    {
        this.type = type;
        this.value = type.correctValue(v);
    }

    public WeaCValue getField(String name) throws NoSuchFieldException
    {
        if(getValue() instanceof WeaCValue)
            return ((WeaCValue) getValue()).getField(name);
        throw new NoSuchFieldException(name + " field doesn't exist for type " + type.getID());
    }

    public Object getValue()
    {
        return value;
    }

    public String toString()
    {
        if(getValue() == null)
            return "nil";
        return getValue().toString();
    }

    public void setValue(Object v)
    {
        this.value = v;
    }

}
