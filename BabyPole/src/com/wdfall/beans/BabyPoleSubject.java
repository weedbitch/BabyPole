// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   BabyPoleSubject.java

package com.wdfall.beans;

import java.util.List;

public class BabyPoleSubject
{

    public BabyPoleSubject()
    {
    }

    public int getSeq()
    {
        return seq;
    }

    public void setSeq(int seq)
    {
        this.seq = seq;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public List getItemList()
    {
        return itemList;
    }

    public void setItemList(List itemList)
    {
        this.itemList = itemList;
    }

    public String toString()
    {
        return (new StringBuilder("BabyPoleSubject [seq=")).append(seq).append(", number=").append(number).append(", subject=").append(subject).append("itemList=").append(itemList).append("]").toString();
    }

    private int seq;
    private int number;
    private String subject;
    private List itemList;
}
