package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.concrete.Company;
import com.techiness.collegecoordinator.utils.InputDataValidator;
import static com.techiness.collegecoordinator.utils.IOUtils.*;
import com.techiness.collegecoordinator.utils.Menu;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;

public final class CompanyCreator
{
    private static CompanyCreator instance = null;
    private String name;
    private int id;
    private String location;
    private Map<String, Pair<Double,Integer>> jobRoles;
    private String roleName = "";
    private double compensation = -1;
    private int noOfOpenPositions = -1;
    private Pair<Double, Integer> rolePair = null;
    private final PlacementDepartment placementDepartment;

    private CompanyCreator()
    {
        placementDepartment = AccountsManager.getInstance().getPlacementDepartment();
    }

    public synchronized static CompanyCreator getInstance()
    {
        if(instance == null)
            instance = new CompanyCreator();
        return instance;
    }

    private void resetVariables()
    {
        name = location = "";
        id = -1;
        jobRoles = new HashMap<>();
    }

    private void resetJobRoleVariables()
    {
        roleName = "";
        compensation = -1;
        noOfOpenPositions = -1;
        rolePair = null;
    }

    public void getJobRoles()
    {
        Menu yesOrNoMenu = Menu.getYesOrNoMenu();
        int continueEnteringJobRoles = 1;
        while(continueEnteringJobRoles != 2)
        {
            if(continueEnteringJobRoles == -1)
            {
                println("Invalid Choice ! Enter a valid choice !");
                continue;
            }
            resetJobRoleVariables();
            while(roleName.equals(""))
            {
                roleName = getUserInput(roleName, "Name of the Job Role");
                if(roleName.equals(""))
                    println("Invalid Role Name ! Enter a Valid Role Name !");
            }
            while(compensation == -1)
            {
                compensation = getUserInput(compensation, "Compensation provided for the Job in Lakhs (Example Enter 4 for 4LPA package)");
                if(compensation == -1)
                    println("Invalid Compensation ! Enter a valid Compensation !");
            }
            while(noOfOpenPositions == -1)
            {
                noOfOpenPositions = getUserInput(noOfOpenPositions, "Number of positions open for the Job Role");
                if(noOfOpenPositions == -1)
                    println("Invalid Number of Positions ! Enter a Valid Number of Positions !");
            }
            rolePair = new Pair<>(compensation,noOfOpenPositions);
            jobRoles.put(roleName, rolePair);
            println2("Do you want to keep on adding roles ?");
            continueEnteringJobRoles = yesOrNoMenu.displayMenuAndGetChoice();
        }
    }

    public synchronized Company getCompany()
    {
        resetVariables();
        id = placementDepartment.getCompanyIdGen();
        while(!InputDataValidator.validateCompanyName(name))
        {
            name = getUserInput(name,"Name of the Company");
            if(!InputDataValidator.validateCompanyName(name))
            {
                println("Invalid Company Name ! Enter a valid Company Name !");
            }
        }
        while(location.equals(""))
        {
            location = getUserInput(location, "Location of the Company");
            if(location.equals(""))
            {
                println("Invalid Company Location ! Enter a valid Company Location !");
            }
        }
        getJobRoles();
        return new Company(id, name, location, jobRoles);
    }
}
