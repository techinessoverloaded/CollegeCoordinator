package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.consoleui.MainUI;
import com.techiness.collegecoordinator.helpers.*;
import java.util.HashMap;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

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
            printlnWithAnim("Retrieving Stored Data...");
            println2();
            accountsManager.restoreState();
        }
        catch (Exception e)
        {
            if(accountsManager.getUsers() == null)
                accountsManager.setUsers(new HashMap<>());
            if(accountsManager.getDepartments() == null)
                accountsManager.setDepartments(new HashMap<>());
            println("User Data Lost/Not obtained Unfortunately!!!");
            println2("The Application may behave like opening for the first time...");
        }
    }

    public static void main(String args[])
    {
        MainUI mainUI = new MainUI();
        if(sessionManager.isFirstTime())
        {
            sessionManager.setFirstTime(false);
            mainUI.displayUIForFirstTime(false);
            mainUI.displayUIAndExecuteActions();
        }
        else if(accountsManager.noAdminAvailable())
        {
            mainUI.displayUIForFirstTime(false);
            mainUI.displayUIAndExecuteActions();
        }
        else if(sessionManager.isFactoryResetDone())
        {
            mainUI.displayUIForFirstTime(true);
            mainUI.displayUIAndExecuteActions();
        }
        else
        {
            mainUI.displayUIAndExecuteActions();
        }

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
                printlnValLn("Don't terminate application abruptly ! It might result in loss of data !");
                printlnWithAnim("Saving data before exiting...");
                accountsManager.persistState();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                println("Unable to store user data!!! Application might behave differently next time!");
            }
        }));
    }
}

