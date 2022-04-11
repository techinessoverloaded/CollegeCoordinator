package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.Grade;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.abstraction.RequestLetter;

import java.util.Map;
import static com.techiness.collegecoordinator.utils.IOUtils.getStringOfIdentifiableMap;

public final class Student extends User
{
    private boolean isPlaced;
    private boolean needsTraining;
    private Map<String, Offer> offers;
    private String deptId;
    private Map<String, Grade> grades;

    public Student(String name, int age, Gender gender, String phone, String email, String password, Map<String, Grade> grades, Map<String, Offer> offers, String deptId)
    {
        super(name, age, gender, phone, email, password);
        this.offers = offers;
        this.grades = grades;
        this.isPlaced = false;
        this.needsTraining = false;
        this.deptId = deptId;
    }

    @Override
    public String getId()
    {
        return id+"@"+deptId+"_"+ UserType.STUDENT;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public Map<String, Grade> getGrades()
    {
        return grades;
    }

    public Grade getGrades(String subjectName)
    {
        return grades.get(subjectName);
    }

    public void setGrades(Map<String, Grade> grades)
    {
        this.grades = grades;
    }

    public boolean getIsPlaced()
    {
        return isPlaced;
    }

    public void setIsPlaced(boolean placed)
    {
        isPlaced = placed;
    }

    public boolean isNeedsTraining()
    {
        return needsTraining;
    }

    public void setNeedsTraining(boolean needsTraining)
    {
        this.needsTraining = needsTraining;
    }

    public Map<String, Offer> getOffers()
    {
        return offers;
    }

    public void setOffers(Map<String, Offer> offers)
    {
        this.offers = offers;
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
        return "Student"+super.toString()+
                ", \ngrades = "+grades+
                ", \nisPlaced = "+isPlaced+
                ", \nneedsTraining = "+needsTraining+
                ", \noffers = "+getStringOfIdentifiableMap(offers)+
                ", \ndeptId = "+deptId+" ]";
    }
}
