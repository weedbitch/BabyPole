// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   BabyPoleResult.java

package com.wdfall.beans;

import org.apache.commons.lang.StringUtils;


public class BabyPoleResult
{

    public BabyPoleResult()
    {
    }

    public boolean validate() {
    	return !StringUtils.isEmpty( this.result );
    }
    
    public int getSeq()
    {
        return seq;
    }

    public void setSeq(int seq)
    {
        this.seq = seq;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
    

    public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}


	private int seq;
    private String host;
    private String result;
    private String referId;
    private String createDatetime;
    
	@Override
	public String toString() {
		return "BabyPoleResult [ host=" + host + ", result="
				+ result + ", referId=" + referId + ", createDatetime="
				+ createDatetime + "]";
	}
	
    
    
    
}
