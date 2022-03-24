package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.consoleui.MainUI;
import com.techiness.collegecoordinator.consoleui.UserUI;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;
import java.util.HashMap;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public class Main
{
    private static final AccountsManager accountsManager;
    private static final SessionManager sessionManager;
    //Retrieving state
    static
    {
        accountsManager = AccountsManager.getInstance();
        sessionManager =  SessionManager.getInstance();
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
            sessionManager.loginUser(admin.getId(), admin.getPassword());
            new UserUI<Admin>(admin).displayUIAndExecuteActions();
        }
        new MainUI().displayUIAndExecuteActions();

        //Persisting State
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            //scanner.close();
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
        }));
    }
}

