// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   HugeListRowHandler.java

package com.wdfall.dao;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.wdfall.utils.FixedKeyMap;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class HugeListRowHandler
    implements RowHandler
{

    public HugeListRowHandler()
    {
        list = new ArrayList();
    }

    public void handleRow(Object obj)
    {
        list.add(obj);
    }

    public List getResultList()
    {
        return list;
    }

    public static void main(String args[])
    {
        long start = System.currentTimeMillis();
        List list = new ArrayList();
        for(int i = 0; i < 0x30d40; i++)
        {
            java.util.Map map = new FixedKeyMap();
            list.add(map);
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    List list;
}
