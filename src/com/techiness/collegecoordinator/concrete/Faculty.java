package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.LetterType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Faculty extends User
{
    protected List<String> subjectsHandled;
    protected List<String> qualifications;
    protected int experience;
    protected Map<String,Student> students;
    protected String deptId;

    public Faculty(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
                   List<String> qualifications, int experience, HashMap<String, Student> students, String deptId)
    {
        super(name, age, gender, phone, email, password);
        this.subjectsHandled = subjectsHandled;
        this.qualifications = qualifications;
        this.experience = experience;
        this.students = students;
        this.deptId = deptId;
    }

    @Override
    public String getId()
    {
        return id+"#"+deptId+"_"+ UserType.FACULTY;
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

    public boolean addStudent(Student student)
    {
        String studentId = student.getId();
        if(students.containsKey(studentId)&&students.get(studentId)!=null)
            return false;
        else if(students.get(studentId)==null)
        {
            students.remove(studentId);
            students.put(studentId,student);
            return true;
        }
        else
        {
            students.put(studentId, student);
            return true;
        }
    }

    public boolean removeStudent(String id)
    {
        if(!students.containsKey(id))
            return false;
        students.remove(id);
        return true;
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
        currentStudent.setNeedsTraining(needsTraining);
        return true;
    }

    public String requestLeaveOrOD(Letter letter, String adminId)
    {
        Admin admin = (Admin) LoginManager.getInstance().getUsers().get(adminId);
        admin.getDepartments().get(deptId).getHod().addLetter(letter);
        return letter.getLetterId();
    }

    public boolean checkLeaveOrODGranted(String letterId, String adminId)
    {
        Admin admin = (Admin) LoginManager.getInstance().getUsers().get(adminId);
        Letter requestedLetter = admin.getDepartments().get(deptId).getHod().getLetters().get(letterId);
        return requestedLetter.getIsGranted();
    }
}
