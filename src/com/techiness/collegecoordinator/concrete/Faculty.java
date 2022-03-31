package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.Grade;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import javafx.util.Pair;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class Faculty extends User
{
    protected List<String> subjectsHandled;
    protected EnumSet<Qualification> qualifications;
    protected int experience;
    protected String deptId;

    public Faculty(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
                   EnumSet<Qualification> qualifications, int experience, String deptId)
    {
        super(name, age, gender, phone, email, password);
        this.subjectsHandled = subjectsHandled;
        this.qualifications = qualifications;
        this.experience = experience;
        this.deptId = deptId;
    }

    public Faculty(HoD existingHoD, String newDeptId)
    {
        super(existingHoD.name, existingHoD.age, existingHoD.gender, existingHoD.phone, existingHoD.email, existingHoD.password);
        this.subjectsHandled = existingHoD.subjectsHandled;
        this.qualifications = existingHoD.qualifications;
        this.experience = existingHoD.experience;
        this.deptId = newDeptId;
    }

    public Faculty(TrainingHead existingTrainingHead, String newDeptId)
    {
        super(existingTrainingHead.name, existingTrainingHead.age, existingTrainingHead.gender, existingTrainingHead.phone, existingTrainingHead.email, existingTrainingHead.password);
        this.subjectsHandled = existingTrainingHead.subjectsHandled;
        this.qualifications = existingTrainingHead.qualifications;
        this.experience = existingTrainingHead.experience;
        this.deptId = newDeptId;
    }

    @Override
    public String getId()
    {
        return id+"@"+deptId+"_"+ UserType.FACULTY;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
        setChanged();
        notifyObservers();
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
        setChanged();
        notifyObservers();
    }

    public List<String> getSubjectsHandled()
    {
        return subjectsHandled;
    }

    public void setSubjectsHandled(List<String> subjectsHandled)
    {
        this.subjectsHandled = subjectsHandled;
        setChanged();
        notifyObservers();
    }

    public EnumSet<Qualification> getQualifications()
    {
        return qualifications;
    }

    public void setQualifications(EnumSet<Qualification> qualifications)
    {
        this.qualifications = qualifications;
        setChanged();
        notifyObservers();
    }

    public boolean addQualification(Qualification qualification)
    {
        if(qualifications.contains(qualification))
            return false;
        qualifications.add(qualification);
        setChanged();
        notifyObservers();
        return true;
    }

    public boolean removeQualification(String qualification)
    {
        if(!qualifications.contains(qualification))
            return false;
        qualifications.remove(qualification);
        setChanged();
        notifyObservers();
        return true;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
        setChanged();
        notifyObservers();
    }

    public boolean addStudent(Student student)
    {
        String studentId = student.getId();
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        return students.putIfAbsent(studentId,student) == null;
    }

    public boolean removeStudent(String studentId)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        return students.remove(studentId) != null;
    }

    public boolean setGrade(String studentId, Map<String, Grade> allSubjectGrades)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId) || students.get(studentId) == null)
            return false;
        Student currentStudent = students.get(studentId);
        currentStudent.setGrades(allSubjectGrades);
        return true;
    }

    public boolean setGrade(String studentId, Pair<String, Grade> singleSubjectGrade)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId) || students.get(studentId) == null)
            return false;
        Student currentStudent = students.get(studentId);
        currentStudent.getGrades().replace(singleSubjectGrade.getKey(), singleSubjectGrade.getValue());
        return true;
    }

    public boolean setNeedsTraining(String studentId, boolean needsTraining)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId) || students.get(studentId) == null)
            return false;
        Student currentStudent = students.get(studentId);
        currentStudent.setNeedsTraining(needsTraining);
        return true;
    }

    public String submitLetterToHoD(RequestLetter requestLetter)
    {
        return AccountsManager.getInstance().getDepartments().get(deptId).getHod().addLetter(requestLetter) ? requestLetter.getId() : null;
    }

    public boolean checkLetterApproved(String letterId)
    {
       return AccountsManager.getInstance().getDepartments().get(deptId).getHod().checkIfLetterApproved(letterId);
    }

    @Override
    public String toString()
    {
        return "Faculty"+super.toString()+", \ndepartmentID = "+deptId+", \nqualifications = "+qualifications
                +", \nexperience = "+experience+", \nsubjectsHandled = "+subjectsHandled+" ]";
    }
}
