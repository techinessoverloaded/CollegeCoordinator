package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.helpers.Menu;

import static com.techiness.collegecoordinator.helpers.IOUtils.println;
import static com.techiness.collegecoordinator.helpers.IOUtils.printlnWithAnim;

public class FacultyUI extends AbstractUserUI
{
    private Faculty faculty;

    public FacultyUI(Faculty faculty)
    {
        this.faculty = faculty;
        prepareMenu();
    }

    public FacultyUI()
    {
        prepareMenu();
    }

    private void prepareMenu()
    {
        this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Faculty Menu")
                .addOption("Display the Subjects to be handled")
                .addOption("Add a Qualification")
                .addOption("Remove a Qualification")
                .addOption("Display my Qualifications")
                .addOption("Set my experience")
                .addOption("Display my experience")
                .addOption("Add a Student to the Department")
                .addOption("Remove a Student from the Department")
                .addOption("Display the Students under the Department")
                .addOption("Set Grade for a Student")
                .addOption("Set if a Student needs Training or not")
                .addOption("Request Leave or OD")
                .addOption("Check if Leave or OD got Approved or not")
                .addOption("Logout")
                .build());
    }

    protected void executeGeneralFacultyActions(Faculty faculty , int selection)
    {
        switch (selection)
        {
            //Display the Subjects to be handled
            case 8:
                println(faculty.getSubjectsHandled());
                break;
            //Add a Qualification
            case 9:
                break;
            //Remove a Qualification
            case 10:
                break;
            //Display my Qualifications
            case 11:
                break;
            //Set my experience
            case 12:
                break;
            //Display my experience
            case 13:
                break;
            //Add a Student to the Department
            case 14:
                break;
            //Remove a Student from the Department
            case 15:
                break;
            //Display the Students under the Department
            case 16:
                break;
            //Set Grade for a Student
            case 17:
                break;
            //Set if a Student needs Training or not
            case 18:
                break;
            //Request Leave or OD
            case 19:
                break;
            //Check if Leave or OD got Approved or not
            case 20:
                break;
        }
    }

    private void executeGeneralFacultyActions(int selection)
    {
        executeGeneralFacultyActions(faculty, selection);
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if (choice == -1)
                continue;

            if(choice >= 1 && choice <= 7)
            {
                executeGeneralUserActions(faculty, choice);
            }

            else if(choice >= 8 && choice <= userMenu.getOptions().size()-2)
            {
                executeGeneralFacultyActions(choice);
            }

            else if(choice == userMenu.getOptions().size()-1)
            {
                printlnWithAnim("Logging out...");
                sessionManager.logoutUser();
                return;
            }
            else
            {
                println("Invalid Choice ! Enter a valid choice...");
            }
        }
    }
}
