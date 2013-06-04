// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   BabyPoleItem.java

package com.wdfall.beans;


public class BabyPoleItem
{

    public BabyPoleItem()
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

    public int getSubjectSeq()
    {
        return subjectSeq;
    }

    public void setSubjectSeq(int subjectSeq)
    {
        this.subjectSeq = subjectSeq;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return (new StringBuilder("\n\tBabyPoleItem [seq=")).append(seq).append(", subjectSeq=").append(subjectSeq).append(", type=").append(type).append(", name=").append(name).append(", value=").append(value).append("]").toString();
    }

    private int seq;
    private int subjectSeq;
    private String type;
    private String name;
    private String value;
}
