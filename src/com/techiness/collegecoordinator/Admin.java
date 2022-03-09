package com.techiness.collegecoordinator;

import com.techiness.collegecoordinator.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User
{
    private List<Department> departments;

    public Admin()
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.departments = new ArrayList<>();
    }

    public Admin(List<Department> departments)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.departments = departments;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.ADMIN;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

}
