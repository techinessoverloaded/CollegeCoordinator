package com.techiness.collegecoordinator;

import java.util.List;

public abstract class Department
{
    protected static int idGen = 1;
    protected String id;
    protected String name;
    protected HoD hod;
    protected List<Faculty> faculties;

    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract HoD getHod();

    public abstract void setHod(HoD hod);
}
