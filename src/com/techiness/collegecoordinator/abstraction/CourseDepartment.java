package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.concrete.Student;

import java.util.Map;

public abstract class CourseDepartment extends Department
{
    protected String courseId;

    public CourseDepartment(String name, HoD hod, Map<String, Faculty> faculties, Map<String, Student> students, String courseId)
    {
        super(name, hod, faculties, students);
        this.courseId = courseId;
    }

    @Override
    public abstract String getId();

    @Override
    public abstract void setId(String id);

    @Override
    public abstract String getName();

    @Override
    public abstract void setName(String name);

    public abstract String getCourseId();

    public abstract void setCourseId(String courseId);
}
