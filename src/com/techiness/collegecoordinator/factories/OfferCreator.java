package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.concrete.Company;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.concrete.Offer;
import javafx.util.Pair;

import static com.techiness.collegecoordinator.utils.IOUtils.*;

public final class OfferCreator
{
    private String studentId = "";
    private String companyId = "";
    private String designation = "";
    private double compensation = -1;
    private static OfferCreator instance = null;
    private PlacementDepartment placementDepartment;

    private OfferCreator()
    {
        placementDepartment = AccountsManager.getInstance().getPlacementDepartment();
    }

    public synchronized static OfferCreator getInstance()
    {
        if(instance == null)
            instance = new OfferCreator();
        return instance;
    }

    private int getOfferIdGen()
    {
        Student currentStudent = placementDepartment.getStudents(studentId);
        return currentStudent.getOffers().size()+1;
    }

    private void resetVariables()
    {
        studentId = designation = companyId = "";
        compensation = -1;
    }

    private void getOfferDetails()
    {
        resetVariables();
        while(!placementDepartment.checkIfStudentIdValid(studentId))
        {
            studentId = getUserInput(studentId, "Student ID of the Student");
            if(!placementDepartment.checkIfStudentIdValid(studentId))
            {
                println("Invalid Student ID ! Enter a valid Student ID !");
            }
        }
        Menu companyIdMenu = new Menu.MenuBuilder().setHeader("Company ID Selection Menu")
                .addMultipleOptions(getStringOfNameableMap(placementDepartment.getCompanies())
                        .replaceAll("\n",",").split(","))
                .build();
        int selectedCompanyChoice = -1;
        while(selectedCompanyChoice == -1)
        {
            selectedCompanyChoice = companyIdMenu.displayMenuAndGetChoice();
            if(selectedCompanyChoice == -1)
                println("Invalid Choice ! Enter a Valid Choice !");
        }
        String selectedOption =  companyIdMenu.getOptions(selectedCompanyChoice);
        companyId = selectedOption.substring(0, selectedOption.indexOf(" : "));
        Company currentCompany = placementDepartment.getCompanies(companyId);
        Menu designationMenu = new Menu.MenuBuilder().setHeader("Designation Selection Menu")
                .addMultipleOptions(currentCompany.getJobRoles().keySet().toArray(new String[0]))
                .build();
        int selectedDesignationChoice = -1;
        while(selectedDesignationChoice == -1)
        {
            selectedDesignationChoice = designationMenu.displayMenuAndGetChoice();
            if(selectedDesignationChoice == -1)
                println("Invalid Choice ! Enter a Valid Choice !");
        }
        designation = designationMenu.getOptions(selectedDesignationChoice);
        compensation = currentCompany.getJobRoles().get(designation).getKey();
        int noOfOpenRoles = currentCompany.getJobRoles().get(designation).getValue();
        currentCompany.getJobRoles().replace(designation, new Pair<>(compensation, noOfOpenRoles-1));
    }

    public synchronized Offer getOffer()
    {
        getOfferDetails();
        return new Offer(getOfferIdGen(), studentId, companyId, designation, compensation);
    }
}
