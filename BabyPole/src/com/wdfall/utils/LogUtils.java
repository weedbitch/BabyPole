// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   LogUtils.java

package com.wdfall.utils;

import java.io.*;

public class LogUtils
{

    public LogUtils()
    {
    }

    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        String message = sw.toString();
        try
        {
            pw.close();
            sw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return message;
    }
}
