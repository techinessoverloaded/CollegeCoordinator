package com.techiness.collegecoordinator;

import java.util.List;

public abstract class CourseDepartment extends Department
{
    protected String courseId;

    @Override
    public abstract String getId();

    @Override
    public abstract void setId(String id);

    @Override
    public abstract String getName();

    @Override
    public abstract void setName(String name);

    @Override
    public abstract HoD getHod();

    @Override
    public abstract void setHod(HoD hod);

    public abstract String getCourseId();

    public abstract void setCourseId(String courseId);
}
