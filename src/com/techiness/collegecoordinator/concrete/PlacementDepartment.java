package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;

public class PlacementDepartment extends Department
{
    private TrainingHead trainingHead;

    public PlacementDepartment(String name,TrainingHead trainingHead)
    {
        super(name, trainingHead);
        this.trainingHead = trainingHead;
    }

    @Override
    public String getId()
    {
        return id+"_"+name;
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

    @Override
    public HoD getHod()
    {
        return trainingHead;
    }

    @Override
    public void setHod(HoD hod)
    {
        this.trainingHead = (TrainingHead) hod;
    }

    public TrainingHead getTrainingHead()
    {
        return trainingHead;
    }

    public void setTrainingHead(TrainingHead trainingHead)
    {
        this.trainingHead = trainingHead;
    }

}
