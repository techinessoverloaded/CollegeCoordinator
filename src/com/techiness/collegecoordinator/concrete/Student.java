package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import com.techiness.collegecoordinator.helpers.Offer;
import java.util.Map;
import static com.techiness.collegecoordinator.consoleui.IOUtils.getStringOfIdentifiableMap;

public final class Student extends User
{
    private String grade;
    private boolean isPlaced;
    private boolean needsTraining;
    private Map<String, Offer> offers;
    private String deptId;

    public Student(String name, int age, Gender gender, String phone, String email, String password, Map<String, Offer> offers, String deptId)
    {
        super(name, age, gender, phone, email, password);
        this.offers = offers;
        this.grade = "";
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

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
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

    public String requestLeaveOrOD(Letter letter, String adminId)
    {
        AccountsManager.getInstance().getDepartments().get(deptId).getHod().addLetter(letter);
        return letter.getId();
    }

    public boolean checkLeaveOrODGranted(String letterId, String adminId)
    {
        return AccountsManager.getInstance().getDepartments().get(deptId).getHod().getLetters().get(letterId).getIsGranted();
    }

    @Override
    public String toString()
    {
        return "Student"+super.toString()+
                ", \ngrade = "+grade+
                ", \nisPlaced = "+isPlaced+
                ", \nneedsTraining = "+needsTraining+
                ", \noffers = "+getStringOfIdentifiableMap(offers)+
                ", \ndeptId = "+deptId+" ]";
    }
}
