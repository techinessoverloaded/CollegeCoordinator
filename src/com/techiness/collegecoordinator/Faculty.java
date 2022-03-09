package com.techiness.collegecoordinator;

import com.techiness.collegecoordinator.enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Faculty extends User
{
    protected List<String> subjectsHandled;
    protected List<String> qualifications;
    protected int experience;
    protected Map<String,Student> students;

    public Faculty()
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.subjectsHandled = new ArrayList<>();
        this.qualifications = new ArrayList<>();
        this.students = new HashMap<>();
        this.experience = 1;
    }

    public Faculty(List<String> subjectsHandled, List<String> qualifications, int experience, HashMap<String, Student> students)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.subjectsHandled = subjectsHandled;
        this.qualifications = qualifications;
        this.experience = experience;
        this.students = students;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.FACULTY;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getSubjectsHandled()
    {
        return subjectsHandled;
    }

    public void setSubjectsHandled(List<String> subjectsHandled)
    {
        this.subjectsHandled = subjectsHandled;
    }

    public List<String> getQualifications()
    {
        return qualifications;
    }

    public void setQualifications(List<String> qualifications)
    {
        this.qualifications = qualifications;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public Map<String, Student> getStudents()
    {
        return students;
    }

    public void setStudents(HashMap<String, Student> students)
    {
        this.students = students;
    }

    public boolean setGrade(String id, String grade)
    {
        if(!students.containsKey(id))
            return false;
        Student currentStudent = students.get(id);
        if(currentStudent==null)
            return false;
        currentStudent.setGrade(grade);
        return true;
    }

    public boolean setNeedsTraining(String id, boolean needsTraining)
    {
        if(!students.containsKey(id))
            return false;
        Student currentStudent = students.get(id);
        if(currentStudent==null)
            return false;
        currentStudent.setNeedsTraining(needsTraining);
        return true;
    }

    public boolean addQualification(String qualification)
    {
        for(String q : qualifications)
        {
            if(q.equalsIgnoreCase(qualification))
                return false;
        }
        qualifications.add(qualification);
        return true;
    }

    public boolean removeQualification(String qualification)
    {
        if(!qualifications.contains(qualification))
            return false;
        qualifications.remove(qualification);
        return true;
    }

    public void requestLeaveOrOD()
    {

    }
}
