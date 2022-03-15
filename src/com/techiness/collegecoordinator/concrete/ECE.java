package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.CourseDepartment;

public class ECE extends CourseDepartment
{
    public ECE(String name, HoD hod, String courseId)
    {
        super(name, hod, courseId);
    }

    @Override
    public String getId()
    {
        return id+"@"+name;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getCourseId()
    {
        return courseId;
    }

    @Override
    public void setCourseId(String courseId)
    {
        this.courseId = courseId;
    }
}
