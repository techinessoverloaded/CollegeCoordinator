package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.consoleui.AdminUI;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;
import java.util.HashMap;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public class Main
{
    private static final AccountsManager accountsManager;

    //Retrieving state
    static
    {
        accountsManager = AccountsManager.getInstance();
        try
        {
            accountsManager.restoreState();
        }
        catch (Exception e)
        {
            if(accountsManager.getUsers() == null)
                accountsManager.setUsers(new HashMap<>());
            if(accountsManager.getDepartments() == null)
                accountsManager.setDepartments(new HashMap<>());
            println("User Data Lost/Not obtained Unfortunately!!!");
            println("The Application may behave like opening for the first time...");
            println();
        }
    }

    public static void main(String args[])
    {
        if(accountsManager.isFirstTime())
        {
            accountsManager.setFirstTime(false);
        }
        //println(accountsManager.getUsers());
        //println(accountsManager.isFirstTime());
        printTextWithinStarPattern("Welcome to CollegeCoordinator !");
        if (accountsManager.noAdminAvailable())
        {
            UserCreationHelper userCreatorHelper = new UserCreationHelper(UserType.ADMIN);
            println("You have to create an Admin account to proceed further....");
            println();
            Admin admin = (Admin) userCreatorHelper.getNewUser();
            accountsManager.getUsers().put(admin.getId(), admin);
            accountsManager.setAdmin(admin);
            printAccountCreationSuccess(admin);
            accountsManager.loginUser(admin.getId(), admin.getPassword());
            new AdminUI().displayUIAndExecuteActions(admin);
        }
        else
        {
            println("Welcome user, choose an option (1-3) to proceed...");
            int loginRegisterOptions = -1;
            do {
                println("\n1. Login as Admin \n2.. Exit");
                loginRegisterOptions = readInt();
                readLine();
                switch (loginRegisterOptions)
                {
                    case 1:
                        //registerPrompt();
                        loginRegisterOptions = 4;
                    case 2:
                        loginRegisterOptions = 4;
                    case 3:
                        printlnWithAnim("Exiting now...");
                        break;
                    case 4:
                        break;
                    default:
                        println("Invalid choice ! Enter a valid choice...");
                        break;
                }

            } while (loginRegisterOptions < 3);
        }

        //Persisting State
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            scanner.close();
            for(Department department : accountsManager.getDepartments().values())
            {
                department.getHod().getLetters().entrySet().removeIf(entry -> entry.getValue().getIsNotifiedToRequester());
            }

            try
            {
                accountsManager.persistState();
                printlnWithAnim("Saving data before exiting...");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                println("Unable to store user data!!! Application might behave differently next time!");
            }
            System.gc();
        }));
    }
}

