package com.techiness.collegecoordinator;

public class CSE extends CourseDepartment
{
    public CSE(String name, String courseId,HoD hod)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.hod = hod;
        this.name = name;
        this.courseId = courseId;
    }

    @Override
    public String getId()
    {
        return id+"_"+name;
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
    public HoD getHod()
    {
        return hod;
    }

    @Override
    public void setHod(HoD hod)
    {
        this.hod = hod;
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
