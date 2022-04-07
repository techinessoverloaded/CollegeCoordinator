package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.managers.AccountsManager;
import java.util.*;
import static com.techiness.collegecoordinator.utils.IOUtils.*;

// Factory Pattern
public final class DepartmentFactory
{
    private String courseId = "", deptName = "", courseSubjectString = "";
    private HoD hod = null;
    private TrainingHead trainingHead = null;
    private Set<String> courseSubjects = null;
    private static DepartmentFactory instance = null;

    private DepartmentFactory()
    {

    }

    public synchronized static DepartmentFactory getInstance()
    {
        if (instance == null)
            instance = new DepartmentFactory();
        return instance;
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
                println("Enter valid Subjects...");
        }
        courseSubjects.addAll(Arrays.asList(courseSubjectString.split(",")));
    }

    public synchronized Department getNewDepartment(DepartmentType departmentType)
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
