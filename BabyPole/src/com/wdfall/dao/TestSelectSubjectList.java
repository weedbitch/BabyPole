// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   TestSelectSubjectList.java

package com.wdfall.dao;

import com.wdfall.beans.BabyPoleSubject;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.wdfall.dao:
//            BabyPoleDao

public class TestSelectSubjectList
{

    public TestSelectSubjectList()
    {
    }

    public void testSubjectList()
    {
        List subjectList = BabyPoleDao.getInstance().getSubjectList();
        BabyPoleSubject subject;
        for(Iterator iterator = subjectList.iterator(); iterator.hasNext(); System.out.println(subject))
            subject = (BabyPoleSubject)iterator.next();

    }
}
