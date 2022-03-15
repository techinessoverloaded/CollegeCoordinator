package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.concrete.HoD;
import java.io.Serializable;

public abstract class Department implements Serializable
{
    protected static int idGen = 1;
    protected String id;
    protected String name;
    protected HoD hod;

    public Department(String name, HoD hod)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.name = name;
        this.hod = hod;
    }
    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getName();

    public abstract void setName(String name);

    public HoD getHod()
    {
        /*StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        String callingClass = traceElements[2].getClassName();
        if(callingClass.equals(HoD.class.getName()))*/
        return hod;
    }

    public void setHod(HoD hod)
    {
        this.hod = hod;
    }
}
