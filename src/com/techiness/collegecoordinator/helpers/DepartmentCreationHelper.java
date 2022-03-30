package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import java.util.*;

import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class DepartmentCreationHelper
{
    private DepartmentType departmentType;
    private String courseId = "", deptName = "", courseSubjectString = "";
    private HoD hod = null;
    private TrainingHead trainingHead = null;
    private Set<String> courseSubjects = null;

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
        courseId = deptName = courseSubjectString = "";
        hod = trainingHead = null;
        courseSubjects = null;
    }

    private void getCourseDepartmentDetails()
    {
        resetVariables();
        while(deptName.equals(""))
        {
            deptName = getUserInput(deptName, "Name of the Department");
            if(deptName.equals(""))
                println("Enter a valid Department Name...");
        }
        while(courseId.equals(""))
        {
            courseId = getUserInput(courseId, "Course ID of the Department");
            if(courseId.equals(""))
                println("Enter a valid Course ID...");
        }
        while(courseSubjectString.equals(""))
        {
            courseSubjectString = getUserInput(courseSubjectString, "Names of the Course Subjects in a single line, separated by commas");
            if(courseSubjectString.equals(""))
                println("Enter valid subjects...");
        }
        courseSubjects.addAll(Arrays.asList(courseSubjectString.split(",")));
    }

    public Department getNewDepartment()
    {
        switch (departmentType)
        {
            case COURSE:
                getCourseDepartmentDetails();
                if(AccountsManager.getInstance().checkIfDeptAlreadyExists(deptName))
                    return null;
                return new CourseDepartment(deptName,null, courseSubjects, new HashMap<>(), new HashMap<>(),courseId);
            case PLACEMENT:
                if(AccountsManager.getInstance().checkIfDeptAlreadyExists("Training and Placement Department"))
                    return null;
                return new PlacementDepartment("Training and Placement Department",null, new HashMap<>(), new HashMap<>());
            default:
                return null;
        }
    }
}
