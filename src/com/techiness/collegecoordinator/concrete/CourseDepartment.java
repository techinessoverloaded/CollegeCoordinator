package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.enums.DepartmentType;
import java.util.Map;

public final class CourseDepartment extends Department
{
    private String courseId;

    public CourseDepartment(String name, HoD hod, Map<String, Faculty> faculties, Map<String, Student> students, String courseId)
    {
        super(name, hod, faculties, students);
        this.courseId = courseId;
    }

    @Override
    public String getId()
    {
        return id+"*"+name+"@"+ DepartmentType.COURSE;
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

    public String getCourseId()
    {
        return courseId;
    }

    public void setCourseId(String courseId)
    {
        this.courseId = courseId;
    }
}
