package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import java.util.HashMap;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class DepartmentCreationHelper
{
    private DepartmentType departmentType;
    private String courseId = "", deptName = "";
    private HoD hod = null;
    private TrainingHead trainingHead = null;

    public DepartmentCreationHelper(DepartmentType departmentType)
    {
        this.departmentType = departmentType;
    }

    public DepartmentType getDepartmentType()
    {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType)
    {
        this.departmentType = departmentType;
    }

    private void resetVariables()
    {
        courseId = deptName = "";
        hod = trainingHead = null;
    }

    private void getCourseDepartmentDetails()
    {
        resetVariables();
        println("Enter the name of the department:");
        deptName = readLine();
        println("Enter the course id:");
        courseId = readLine();
    }

    public Department getNewDepartment()
    {
        switch (departmentType)
        {
            case COURSE:
                getCourseDepartmentDetails();
                if(AccountsManager.getInstance().checkIfDeptAlreadyExists(deptName))
                    return null;
                return new CourseDepartment(deptName,null, new HashMap<>(), new HashMap<>(),courseId);
            case PLACEMENT:
                if(AccountsManager.getInstance().checkIfDeptAlreadyExists("Training and Placement Department"))
                    return null;
                return new PlacementDepartment("Training and Placement Department",null, new HashMap<>(), new HashMap<>(), new HashMap<>());
            default:
                return null;
        }
    }
}
