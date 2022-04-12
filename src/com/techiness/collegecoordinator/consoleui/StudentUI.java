package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.enums.Grade;
import com.techiness.collegecoordinator.enums.RequestLetterType;
import com.techiness.collegecoordinator.factories.RequestLetterFactory;
import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.managers.SessionManager;
import com.techiness.collegecoordinator.concrete.Offer;
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
                Department currentDepartment = accountsManager.getDepartments(student.getDeptId());
                HoD currentHoD = currentDepartment.getHod();
                RequestLetter leaveLetter = RequestLetterFactory.getInstance().getLetter(student.getId(), currentHoD.getId(), currentDepartment.getId(), RequestLetterType.LEAVE);
                printlnWithAnim("Submitting Leave Request Letter to HoD...");
                if(currentHoD.addLetter(leaveLetter))
                {
                    println("Submitted Leave Request Letter to HoD. You can check the status of Approval after the HoD checks it.");
                    println2("Letter Details:\n" + leaveLetter);
                }
                else
                {
                    println2("An Error occurred ! Failed to Submit Leave Request Letter to HoD !");
                }
            break;
            //Request On Duty to HoD
            case 13:
                Department currentDepartment2 = accountsManager.getDepartments(student.getDeptId());
                HoD currentHoD2 = currentDepartment2.getHod();
                RequestLetter odLetter = RequestLetterFactory.getInstance().getLetter(student.getId(), currentHoD2.getId(), currentDepartment2.getId(), RequestLetterType.ON_DUTY);
                printlnWithAnim("Submitting On Duty Request Letter to HoD...");
                if(currentHoD2.addLetter(odLetter))
                {
                    println("Submitted On Duty Request Letter to HoD. You can check the status of Approval after the HoD checks it.");
                    println2("Letter Details:\n" + odLetter);
                }
                else
                {
                    println2("An Error occurred ! Failed to Submit On Duty Request Letter to HoD !");
                }
            break;
            //Request Transfer Certificate to Admin
            case 14:
                Admin admin = accountsManager.getAdmin();
                RequestLetter tcLetter = RequestLetterFactory.getInstance().getLetter(student.getId(), admin.getId(), student.getDeptId(), RequestLetterType.TC);
                printlnWithAnim("Submitting Transfer Certificate Request Letter to Admin...");
                if(admin.addLetter(tcLetter))
                {
                    println("Submitted Transfer Certificate Request Letter to Admin. You can check the status of Approval after the Admin checks it.");
                    println2("Letter Details:\n" + tcLetter);
                }
                else
                {
                    println2("An Error occurred ! Failed to Submit Transfer Certificate Request Letter to Admin !");
                }
            break;
            //Check if Request Letter got Approved or not
            case 15:
                Menu checkLetterTypeMenu = new Menu.MenuBuilder().setHeader("Type of Letter to be checked")
                        .addMultipleOptions(RequestLetterType.getStringArrayOfStudentLetterTypes())
                        .build();
                int checkLetterChoice = -1;
                RequestLetterType enteredRequestLetterType;
                while(checkLetterChoice == -1)
                {
                    checkLetterChoice = checkLetterTypeMenu.displayMenuAndGetChoice();
                    if(checkLetterChoice == -1)
                        println2("Invalid choice ! Enter a valid choice...");
                }
                enteredRequestLetterType = RequestLetterType.valueOf(checkLetterTypeMenu.getOptions(checkLetterChoice));
                Department currentDepartment3 = accountsManager.getDepartments(student.getDeptId());
                HoD hod3 = currentDepartment3.getHod();
                Admin admin2 = accountsManager.getAdmin();
                String letterId = "";
                while(letterId.equals(""))
                {
                    letterId = getUserInput(letterId, "Letter ID of the Letter");
                    if(letterId.equals(""))
                    {
                        println("Enter a valid letter ID...");
                    }
                }
                switch (enteredRequestLetterType)
                {
                    case LEAVE:
                        if(hod3.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(hod3.checkIfLetterApproved(letterId))
                            println2("Your Leave Request Letter got approved by the HoD !");
                        else
                            println2("Your Leave Request Letter was not approved by the HoD ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;

                    case ON_DUTY:
                        if(hod3.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(hod3.checkIfLetterApproved(letterId))
                            println2("Your OD Request Letter got approved by the HoD !");
                        else
                            println2("Your OD Request Letter was not approved by the HoD ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;

                    case TC:
                        if(hod3.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(hod3.checkIfLetterApproved(letterId))
                            println2("Your TC Request Letter got approved by the HoD !");
                        else
                            println2("Your TC Request Letter was not approved by the HoD ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;
                }
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
