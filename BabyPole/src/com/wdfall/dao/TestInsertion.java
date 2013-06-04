// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   TestInsertion.java

package com.wdfall.dao;

import com.wdfall.beans.BabyPoleResult;
import java.io.PrintStream;

// Referenced classes of package com.wdfall.dao:
//            BabyPoleDao

public class TestInsertion
{

    public TestInsertion()
    {
    }

    public void testInsert()
    {
        BabyPoleResult bean = new BabyPoleResult();
        bean.setHost("1.214.982.98");
        bean.setResult("1={}, 2={}");
        System.out.println((new StringBuilder("xxxx=")).append(BabyPoleDao.getInstance().insertPoleResult(bean)).toString());
    }
}
