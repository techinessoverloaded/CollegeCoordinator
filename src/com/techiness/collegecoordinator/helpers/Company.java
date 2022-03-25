package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Nameable;
import javafx.util.Pair;
import java.io.Serializable;
import java.util.Map;

public class Company implements Serializable, Nameable
{
    private String name;
    private String id;
    private String location;
    private Map<String,Pair<Double,Integer>> jobRoles;

    public Company(int id, String name, String location, Map<String, Pair<Double, Integer>> jobRoles)
    {
        this.id = String.valueOf(id);
        this.name = name;
        this.location = location;
        this.jobRoles = jobRoles;
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
    public String getId()
    {
        return id;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Map<String, Pair<Double, Integer>> getJobRoles()
    {
        return jobRoles;
    }

    public void setJobRoles(Map<String, Pair<Double, Integer>> jobRoles) {
        this.jobRoles = jobRoles;
    }
}
