package com.techiness.collegecoordinator;

import java.util.List;

public class PlacementDepartment extends Department
{
    private TrainingHead trainingHead;
    public PlacementDepartment()
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
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

    @Override
    public List<Faculty> getFaculties()
    {
        return faculties;
    }

    @Override
    public void setFaculties(List<Faculty> faculties)
    {
        this.faculties = faculties;
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
