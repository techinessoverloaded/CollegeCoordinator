package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Offer;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class OfferCreator
{
    private String studentId;
    private String placementDeptId;
    private String designation;
    private double compensation;

    public OfferCreator()
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


    public synchronized Offer getNewOffer()
    {
        return null;
    }




}
