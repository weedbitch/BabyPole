// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   BabyPoleDao.java

package com.wdfall.dao;

import com.wdfall.beans.BabyPoleResult;
import com.wdfall.beans.BabyPoleSubject;
import com.wdfall.utils.LogUtils;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

// Referenced classes of package com.wdfall.dao:
//            SqlMapManager, DaoHelper

public class BabyPoleDao
{

    public BabyPoleDao()
    {
    }

    public static BabyPoleDao getInstance()
    {
        return instance;
    }

    public int insertPoleResult(BabyPoleResult bean)
    {
        int result = 0;
        try
        {
            result = DaoHelper.insert(SqlMapManager.SQL_MAP_DB_TYPE, "babypole.INSERT_BABY_POLE_RESULT", bean);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.fatal(LogUtils.getStackTrace(e));
        }
        return result;
    }

    public List getSubjectList()
    {
        List subjectList = null;
        try
        {
            subjectList = DaoHelper.getList(SqlMapManager.SQL_MAP_DB_TYPE, "babypole.SELECT_BABY_POLE_SUBJECT");
            BabyPoleSubject subject;
            for(Iterator iterator = subjectList.iterator(); iterator.hasNext(); subject.setItemList(DaoHelper.getList(SqlMapManager.SQL_MAP_DB_TYPE, "babypole.SELECT_BABY_POLE_ITEM_BY_SUBJECT", Integer.valueOf(subject.getSeq()))))
                subject = (BabyPoleSubject)iterator.next();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.fatal(LogUtils.getStackTrace(e));
        }
        return subjectList;
    }

    private static final Logger logger = Logger.getLogger(BabyPoleDao.class);
    private static BabyPoleDao instance = new BabyPoleDao();

}
