package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.concrete.Student;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;
public class OfferCreationHelper
{
    private String studentId;
    private String placementDeptId;
    private String designation;
    private double compensation;

    public OfferCreationHelper()
    {

    }

    private int getOfferIdGen()
    {
        PlacementDepartment placementDepartment = (PlacementDepartment) AccountsManager.getInstance().getDepartments(placementDeptId);
        Student currentStudent = placementDepartment.getStudents(studentId);
        return currentStudent.getOffers().size()+1;
    }

    private void getOfferDetails()
    {
        println("Enter the placement department ID:");
        placementDeptId = readLine();
        println("Enter the student ID:");
        studentId = readLine();
    }


    public Offer getNewOffer()
    {
        return null;
    }




}
