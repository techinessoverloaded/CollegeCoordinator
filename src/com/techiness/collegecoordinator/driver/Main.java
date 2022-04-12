package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.EntryPointUI;
import com.techiness.collegecoordinator.consoleui.MainUI;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.managers.SessionManager;
import java.util.*;
import static com.techiness.collegecoordinator.utils.IOUtils.*;

public final class Main
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

    public static void main(String[] args)
    {
        EntryPointUI entryPointUI = new MainUI();
        if(sessionManager.isFirstTime())
        {
            sessionManager.setFirstTime(false);
            entryPointUI.displayUIForFirstTimeAndExecuteActions();
        }
        else if(accountsManager.noAdminAvailable())
        {
            entryPointUI.displayUIForFirstTimeAndExecuteActions();
        }
        else if(sessionManager.isFactoryResetDone())
        {
            entryPointUI.displayUIForFirstTimeAndExecuteActions();
        }
        else
        {
            entryPointUI.displayUIAndExecuteActions();
        }

        //Persisting State
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
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

