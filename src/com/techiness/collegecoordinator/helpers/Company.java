package com.techiness.collegecoordinator.helpers;

import javafx.util.Pair;
import java.util.Map;

public class Company
{
    private String name;
    private String id;
    private static int idGen = 1;
    private String location;
    private Map<String,Pair<Double,Integer>> jobRoles;

    public Company(String name, String location, Map<String, Pair<Double, Integer>> jobRoles)
    {
        this.id = idGen+"_COMPANY";
        idGen += 1;
        this.name = name;
        this.location = location;
        this.jobRoles = jobRoles;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

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
