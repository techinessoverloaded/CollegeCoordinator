package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import java.util.Map;


public final class Admin extends User
{
    public Admin(String name, int age, Gender gender, String phone, String email, String password, Map<String,Department> departments)
    {
        super(name, age, gender, phone, email, password);
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

    public boolean addDepartment(Department department)
    {
        Map<String,Department> departments = AccountsManager.getInstance().getDepartments();
        if(departments.containsKey(department.getId()))
            return false;
        else if(departments.get(department.getId())==null)
        {
            departments.remove(department.getId());
            departments.put(department.getId(),department);
            return true;
        }
        else
        {
            departments.put(department.getId(), department);
            return true;
        }
    }

    public boolean removeDepartment(String deptId)
    {
        Map<String,Department> departments = AccountsManager.getInstance().getDepartments();
        if(!departments.containsKey(deptId))
            return false;
        departments.remove(deptId);
        return true;
    }

    public boolean assignHoD(String deptId, HoD hod)
    {
        Map<String,Department> departments = AccountsManager.getInstance().getDepartments();
        if(!departments.containsKey(deptId)||departments.get(deptId) == null)
            return false;
        Department currentDepartment = departments.get(deptId);
        currentDepartment.setHod(hod);
        return true;
    }

    @Override
    public String toString()
    {
        return "Admin"+super.toString()+" ]";
    }
}
