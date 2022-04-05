package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.factories.UserFactory;
import com.techiness.collegecoordinator.helpers.Menu;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class HoDUI extends FacultyUI
{
    private HoD hoD;

    public HoDUI(HoD hoD)
    {
        this.hoD = hoD;
        prepareMenu();
    }

    protected HoDUI()
    {
        prepareMenu();
    }

    private void prepareMenu()
    {
        userMenu.removeOption(userMenu.indexOf("Logout"));
        userMenu.getOptions().replace(userMenu.indexOf("Request Leave to HoD"),
                        userMenu.getOptions(userMenu.indexOf("Request Leave to HoD")).replace("HoD","Admin"));
        userMenu.getOptions().replace(userMenu.indexOf("Request On Duty to HoD"),
                userMenu.getOptions(userMenu.indexOf("Request On Duty to HoD")).replace("HoD","Admin"));
        userMenu.extendMenu(new Menu.MenuBuilder().setHeader("HoD Menu")
                .addOption("Add a Faculty to the Department")
                .addOption("Remove a Faculty from the Department and relieve the Faculty from Job")
                .addOption("Transfer a Faculty to another Department")
                .addOption("Display all the faculties under the Department")
                .addOption("Add Subject(s) handled by a Faculty")
                .addOption("Remove Subject(s) handled by a Faculty")
                .addOption("Display all the Request Letters")
                .addOption("View and Approve/Disapprove a Request Letter")
                .addOption("Logout")
                .build());
    }

    protected final void executeGeneralHoDActions(HoD hod, int selection)
    {
        switch (selection)
        {
            //Add a Faculty to the Department
            case 27:
                Faculty newFaculty = (Faculty) UserFactory.getInstance().getNewUser(UserType.FACULTY);
                printlnWithAnim("Adding new Faculty to the Department...");
                if(hod.addFaculty(newFaculty))
                {
                    accountsManager.getUsers().putIfAbsent(newFaculty.getId(), newFaculty);
                    println2("New Faculty added to the Department successfully with Account Details :");
                    printAccountDetails(newFaculty,true);
                }
                else
                {
                    println2("An error occurred ! Unable to add new Faculty to the Department ! Try again !");
                }
                break;
            //Remove a Faculty from the Department and relieve the Faculty from Job
            case 28:
                String facultyIdToBeRemoved = "";
                Department currentDepartment = accountsManager.getDepartments(hod.getDeptId());
                while(!currentDepartment.checkIfFacultyIdValid(facultyIdToBeRemoved))
                {
                    facultyIdToBeRemoved = getUserInput(facultyIdToBeRemoved, "Faculty ID of the Faculty whom you want to remove from the Department");
                }
            //Transfer a Faculty to another Department
            case 29:
            //Display all the faculties under the Department
            case 30:
            //Add Subject(s) handled by a Faculty
            case 31:
            //Remove Subject(s) handled by a Faculty
            case 32:
            //Display all the Request Letters
            case 33:
                //View and Approve/Disapprove a Request RequestLetter
            case 34:
        }
    }

    private void executeGeneralHoDActions(int selection)
    {
        executeGeneralHoDActions(hoD, selection);
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if(choice >= 1 && choice <= 7)
                executeGeneralUserActions(hoD, choice);
            else if(choice >= 8 && choice <= 25)
                executeGeneralFacultyActions(hoD, choice);
            else if(choice >= 26 && choice <= userMenu.getOptions().size()-1)
                executeGeneralHoDActions(choice);
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
