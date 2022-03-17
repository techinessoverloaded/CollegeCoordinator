package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.enums.DepartmentType;

import java.util.Map;

public class PlacementDepartment extends Department
{
    private TrainingHead trainingHead;

    public PlacementDepartment(String name, TrainingHead trainingHead, Map<String, Faculty> faculties, Map<String, Student> students)
    {
        super(name, trainingHead, faculties, students);
        this.trainingHead = trainingHead;
    }

    @Override
    public String getId()
    {
        return id+"*"+name+"@"+DepartmentType.PLACEMENT;
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
        this.hod = this.trainingHead = (TrainingHead) hod;
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
