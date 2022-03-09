package com.techiness.collegecoordinator;

import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Offer;

import java.util.List;

public class Student extends User
{
    private String grade;
    private boolean isPlaced;
    private boolean needsTraining;
    private List<Offer> offers;

    @Override
    public String getId()
    {
        return id+"_"+ UserType.STUDENT;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public boolean isPlaced()
    {
        return isPlaced;
    }

    public void setPlaced(boolean placed)
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

    public List<Offer> getOffers()
    {
        return offers;
    }

    public void setOffers(List<Offer> offers)
    {
        this.offers = offers;
    }
}
