package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Menu;
import com.techiness.collegecoordinator.helpers.SessionManager;

import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public final class MainUI
{
    private Menu mainMenu;
    private SessionManager sessionManager;
    private AccountsManager accountsManager;
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
                    if(!accountsManager.checkIfAnyUserAvailable(UserType.TRAINING_HEAD))
                    {
                        println2("No Training Head account exists !");
                        continue;
                    }
                    String trainingHeadId, trainingHeadPassword;
                    println("Enter Training Head ID:");
                    trainingHeadId = readLine();
                    println("Enter Training Head Password:");
                    trainingHeadPassword = readPassword();
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
                    String hodId, hodPassword;
                    println("Enter HoD ID:");
                    hodId = readLine();
                    println("Enter HoD Password:");
                    hodPassword = readPassword();
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
                    String facultyId, facultyPassword;
                    println("Enter Faculty ID:");
                    facultyId = readLine();
                    println("Enter Faculty Password:");
                    facultyPassword = readPassword();
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
                    String studentId, studentPassword;
                    println("Enter Student ID:");
                    studentId = readLine();
                    println("Enter Student Password:");
                    studentPassword = readPassword();
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

    }
}
