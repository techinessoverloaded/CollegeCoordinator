package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;

import java.util.HashMap;
import java.util.Map;


public class Admin extends User
{
    private Map<String, Department> departments;

    public Admin(String name, int age, Gender gender, String phone, String email, String password, Map<String,Department> departments)
    {
        super(name, age, gender, phone, email, password);
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

    public boolean assignHoD(String deptId, HoD hod)
    {
        if(!departments.containsKey(deptId)||departments.get(deptId) == null)
            return false;
        Department currentDepartment = departments.get(deptId);
        currentDepartment.setHod(hod);
        return true;
    }
}
