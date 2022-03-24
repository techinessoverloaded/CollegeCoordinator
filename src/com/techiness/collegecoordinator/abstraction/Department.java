package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.concrete.Student;
import static com.techiness.collegecoordinator.consoleui.IOUtils.getStringOfNameableMap;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The Basic Root Abstract Class for the departments of this Application. Has the basic details of a department.
 * Implements the {@link Nameable} interface. Also, implements the {@link Serializable} interface, and hence can be serialized.
 * The subclasses of this class must override {@link #getId()} and {@link #setId(String)} methods of the {@link Identifiable} interface and also
 * {@link #getName()} and {@link #setName(String)} methods of the {@link Nameable} interface.
 */
public abstract class Department implements Serializable, Nameable, Comparable<Department>
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

    public static int getIdGen()
    {
        return idGen;
    }

    public static void setIdGen(int idGen)
    {
        Department.idGen = idGen;
    }

    @Override
    public abstract String getId();

    @Override
    public abstract void setId(String id);

    @Override
    public abstract String getName();

    @Override
    public abstract void setName(String name);

    public abstract String getDeptShortName();

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

    @Override
    public int compareTo(Department o)
    {
        return name.compareTo(o.name);
    }

    @Override
    public String toString()
    {
        if(hod == null)
        {
            return " [ \nid = "+getId()+
                    ", \nname = "+name+
                    ", \nhod = "+ "None"+
                    ", \nstudents = "+getStringOfNameableMap(students)+
                    ", \nfaculties = "+getStringOfNameableMap(faculties);
        }
        return " [ \nid = "+getId()+
                ", \nname = "+name+
                ", \nhod = "+ hod.getName()+
                ", \nstudents = "+getStringOfNameableMap(students)+
                ", \nfaculties = "+getStringOfNameableMap(faculties);
    }
}
