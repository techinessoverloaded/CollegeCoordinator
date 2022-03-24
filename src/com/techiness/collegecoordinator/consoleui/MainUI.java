package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.helpers.Menu;
import com.techiness.collegecoordinator.helpers.SessionManager;

import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public final class MainUI
{
    private Menu mainMenu;
    private SessionManager sessionManager;
    public MainUI()
    {
        this.mainMenu = new Menu.MenuBuilder().setHeader("Main Menu")
                .addOption("Login as Admin")
                .addOption("Login as TrainingHead")
                .addOption("Login as HoD")
                .addOption("Login as Faculty")
                .addOption("Login as Student")
                .addOption("Exit Application")
                .build();
        this.sessionManager = SessionManager.getInstance();
    }

    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = mainMenu.displayMenuAndGetChoice();
            switch (choice)
            {
                case 1:
                    String adminId, adminPassword;
                    println("Enter admin ID:");
                    adminId = readLine();
                    println("Enter admin Password:");
                    adminPassword = readPassword();
                    printlnWithAnim("Trying to login as Admin...");
                    if(sessionManager.loginAdmin(adminId, adminPassword))
                    {
                        println2("Logged in as Admin successfully !");
                        sessionManager.redirectToRespectiveUI();
                    }
                    else
                        println2("Invalid ID or Password ! Check your User ID and Password");
                    continue;
                case 2:
                    break;
            }
            break;
        }

    }
}
