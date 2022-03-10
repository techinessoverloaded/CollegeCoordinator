package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.concrete.HoD;

public abstract class Department
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

    public abstract HoD getHod();

    public abstract void setHod(HoD hod);
}
