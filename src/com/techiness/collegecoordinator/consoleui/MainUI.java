package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Menu;
import com.techiness.collegecoordinator.helpers.SessionManager;
import com.techiness.collegecoordinator.factories.UserFactory;

import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public final class MainUI
{
    private final Menu mainMenu;
    private final SessionManager sessionManager;
    private final AccountsManager accountsManager;

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
        this.accountsManager = AccountsManager.getInstance();
    }

    public void displayUIForFirstTime(boolean onFactoryRest)
    {
        if(onFactoryRest)
        {
            sessionManager.setFactoryResetDone(false);
        }
        printTextWithinStarPattern("Welcome to CollegeCoordinator");
        println2("You have to create an Admin account to proceed further....");
        Admin admin = (Admin) UserFactory.getInstance().getNewUser(UserType.ADMIN);
        accountsManager.getUsers().put(admin.getId(), admin);
        accountsManager.setAdmin(admin);
        printAccountDetails(admin,true);
        sessionManager.loginAdmin(admin.getId(), admin.getPassword());
        sessionManager.redirectToRespectiveUI();
        sessionManager.redirectToRespectiveUI();
    }

    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = mainMenu.displayMenuAndGetChoice();
            if(choice == -1)
                continue;
            switch (choice)
            {
                case 1:
                    if(accountsManager.noAdminAvailable())
                    {
                        println2("No Admin account exists !");
                        continue;
                    }
                    String adminId="", adminPassword="";
                    adminId = getUserInput(adminId,"Admin ID");
                    adminPassword = getPasswordInput("admin Password");
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
                    if(!accountsManager.checkIfAnyUserAvailable(UserType.TRAINING_HEAD))
                    {
                        println2("No Training Head account exists !");
                        continue;
                    }
                    String trainingHeadId = "", trainingHeadPassword = "";
                    trainingHeadId = getUserInput(trainingHeadId,"Training Head ID");
                    trainingHeadPassword = getPasswordInput("Training Head Password");
                    printlnWithAnim("Trying to login as Training Head...");
                    if(sessionManager.loginUser(trainingHeadId, trainingHeadPassword))
                    {
                        println2("Logged in as Training Head successfully !");
                        sessionManager.redirectToRespectiveUI();
                    }
                    else
                        println2("Invalid ID or Password ! Check your User ID and Password");
                    continue;

                case 3:
                    if(!accountsManager.checkIfAnyUserAvailable(UserType.HOD))
                    {
                        println2("No HoD account exists !");
                        continue;
                    }
                    String hodId="", hodPassword="";
                    hodId = getUserInput(hodId,"HoD ID");
                    hodPassword = getPasswordInput("HoD Password");
                    printlnWithAnim("Trying to login as HoD...");
                    if(sessionManager.loginUser(hodId, hodPassword))
                    {
                        println2("Logged in as HoD successfully !");
                        sessionManager.redirectToRespectiveUI();
                    }
                    else
                        println2("Invalid ID or Password ! Check your User ID and Password");
                    continue;

                case 4:
                    if(!accountsManager.checkIfAnyUserAvailable(UserType.FACULTY))
                    {
                        println2("No Faculty account exists !");
                        continue;
                    }
                    String facultyId = "", facultyPassword = "";
                    facultyId = getUserInput(facultyId,"Faculty ID");
                    facultyPassword = getPasswordInput("Faculty Password");
                    printlnWithAnim("Trying to login as Faculty...");
                    if(sessionManager.loginUser(facultyId, facultyPassword))
                    {
                        println2("Logged in as Faculty successfully !");
                        sessionManager.redirectToRespectiveUI();
                    }
                    else
                        println2("Invalid ID or Password ! Check your User ID and Password");
                    continue;

                case 5:
                    if(!accountsManager.checkIfAnyUserAvailable(UserType.STUDENT))
                    {
                        println2("No Student account exists !");
                        continue;
                    }
                    String studentId="", studentPassword="";
                    studentId = getUserInput(studentId,"Student ID");
                    studentPassword = getPasswordInput("Student Password");
                    printlnWithAnim("Trying to login as Student...");
                    if(sessionManager.loginUser(studentId, studentPassword))
                    {
                        println2("Logged in as Student successfully !");
                        sessionManager.redirectToRespectiveUI();
                    }
                    else
                        println2("Invalid ID or Password ! Check your User ID and Password");
                    continue;

                case 6:
                    break;

                default:
                    println2("Invalid Choice ! Enter a valid choice !");
                    continue;
            }
            break;
        }
        if(sessionManager.isFactoryResetDone())
        {
            sessionManager.setFactoryResetDone(false);
            displayUIForFirstTime(true);
        }
    }
}
