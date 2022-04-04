package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.consoleui.MainUI;
import com.techiness.collegecoordinator.helpers.*;
import java.util.*;
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
        MainUI mainUI = new MainUI();
        //println2(LocalDate.now(ZoneId.systemDefault()).format(getDateFormatter()));
        //println2(InputDataValidator.validateDateString("01/04/2022"));
        //RequestLetterFactory.getInstance();
//        Faculty faculty = new Faculty("arun",21,
//                Gender.MALE,"7338817854","arun@gmail.com","Arun@262001",
//                new ArrayList<>(), EnumSet.allOf(Qualification.class),10,"");
//        new FacultyUI(faculty).displayUIAndExecuteActions();
        if(sessionManager.isFirstTime())
        {
            sessionManager.setFirstTime(false);
            mainUI.displayUIForFirstTime(false);
        }
        else if(accountsManager.noAdminAvailable())
        {
            mainUI.displayUIForFirstTime(false);
        }
        else if(sessionManager.isFactoryResetDone())
        {
            mainUI.displayUIForFirstTime(true);
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

