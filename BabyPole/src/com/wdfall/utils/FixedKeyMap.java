// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   FixedKeyMap.java

package com.wdfall.utils;

import java.util.HashMap;
import java.util.Map;

public class FixedKeyMap extends HashMap
{

    public FixedKeyMap()
    {
    }

    public Object get(Object key)
    {
        key = ((String)key).toLowerCase();
        Object val = super.get(key);
        return val;
    }

    public Object put(Object key, Object value)
    {
        value = value != null ? value : "";
        key = ((String)key).toLowerCase();
        return super.put(key, value);
    }

    public Object remove(Object key)
    {
        key = ((String)key).toLowerCase();
        return super.remove(key);
    }

    public void putAll(Map t)
    {
        throw new RuntimeException(" FixedKeyMap ::  putAll() is not supported method");
    }

    public long getLongValue(String key)
    {
        long intVal = -1L;
        Object val = get(key);
        try
        {
            intVal = ((Long)val).longValue();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return intVal;
    }

    public int getIntValue(String key)
    {
        int intVal = -1;
        Object val = get(key);
        try
        {
            intVal = ((Integer)val).intValue();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return intVal;
    }

    public float getFloatValue(String key)
    {
        float floatVal = -1F;
        Object val = get(key);
        try
        {
            floatVal = ((Integer)val).floatValue();
        }
        catch(Exception exception) { }
        return floatVal;
    }

    public String getStringValue(String key)
    {
        String stringVal = null;
        Object val = get(key);
        try
        {
            stringVal = val.toString();
        }
        catch(Exception exception) { }
        return stringVal;
    }

    private static final long serialVersionUID = 0x236954cecc0fd0cbL;
}
