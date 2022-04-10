package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.enums.Grade;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.managers.SessionManager;
import com.techiness.collegecoordinator.utils.Offer;

import java.util.Map;

import static com.techiness.collegecoordinator.utils.IOUtils.*;

public final class StudentUI extends AbstractUserUI
{
    private final Student student;

    public StudentUI(Student student)
    {
        super();
        this.student = student;
        this.accountsManager = AccountsManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
        userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Student Menu")
                .addOption("Check if I need Training")
                .addOption("Check if got Placed")
                .addOption("Display all my Offers")
                .addOption("Check my Grades")
                .addOption("Request Leave to HoD")
                .addOption("Request On Duty to HoD")
                .addOption("Request Transfer Certificate to Admin")
                .addOption("Check if Request Letter got Approved or not")
                .addOption("Logout")
                .build());
    }

    private void executeGeneralStudentActions(int choice)
    {
        switch (choice)
        {
            //Check if I need Training
            case 8:
                if(student.isNeedsTraining())
                {
                    println2("Yes, you need Placement Training !");
                }
                else
                {
                    println2("No, you don't need Placement Training !");
                }
            break;
            //Check if got Placed
            case 9:
                if(student.getIsPlaced())
                {
                    println2("Yes, you got placed !");
                }
                else
                {
                    println2("No, you did not get placed !");
                }
            break;
            //Display all my Offers
            case 10:
                if(!student.getIsPlaced())
                {
                    println2("You don't have any offers !");
                }
                else
                {
                    Map<String, Offer> offerMap = student.getOffers();
                    for(Offer offer: offerMap.values())
                    {
                        println2(offer);
                    }
                }
            break;
            //Check my Grades
            case 11:
                Map<String, Grade> gradeMap = student.getGrades();
                println2("Your grades are:");
                println("Course Subject - Grade");
                for(Map.Entry<String, Grade> gradeEntry: gradeMap.entrySet())
                {
                    println(gradeEntry.getKey()+" - "+gradeEntry.getValue());
                }
                println2();
            break;
            //Request Leave to HoD
            case 12:

            break;
            //Request On Duty to HoD
            case 13:

            break;
            //Request Transfer Certificate to Admin
            case 14:

            break;
            //Check if Request Letter got Approved or not
            case 15:

            break;
        }
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if(choice >= 1 && choice <= 7)
                executeGeneralUserActions(student, choice);
            else if(choice >= 8 && choice <= userMenu.getOptions().size()-1)
                executeGeneralStudentActions(choice);
            else if(choice == userMenu.getOptions().size())
            {
                printlnWithAnim("Logging out...");
                sessionManager.logoutUser();
                return;
            }
            else
            {
                println2("Invalid choice ! Enter a valid choice...");
            }
        }
    }
}
