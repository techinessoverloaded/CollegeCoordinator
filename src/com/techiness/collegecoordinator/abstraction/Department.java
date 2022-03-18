package com.techiness.collegecoordinator.abstraction;

import com.sun.istack.internal.Nullable;
import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.concrete.Student;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Department implements Serializable
{
    protected static int idGen = 1;
    protected String id;
    protected String name;
    protected HoD hod;
    protected Map<String, Student> students;
    protected Map<String, Faculty> faculties;

    public Department(String name, HoD hod, Map<String,Faculty> faculties, Map<String, Student> students)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.name = name;
        this.hod = hod;
        this.students = students;
        this.faculties = faculties;
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

    public Map<String, Student> getStudents()
    {
        return students;
    }

    public Student getStudents(String studentId)
    {
        return !students.containsKey(studentId) || students.get(studentId)==null ? null : students.get(studentId);
    }

    public void setStudents(HashMap<String, Student> students)
    {
        this.students = students;
    }

    public Map<String, Faculty> getFaculties()
    {
        return faculties;
    }

    public Faculty getFaculties(String facultyId)
    {
        return !faculties.containsKey(facultyId) || faculties.get(facultyId) == null ? null : faculties.get(facultyId);
    }

    public void setFaculties(Map<String, Faculty> faculties)
    {
        this.faculties = faculties;
    }
}
