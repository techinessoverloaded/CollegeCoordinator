package com.techiness.collegecoordinator.consoleui;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_TW;
import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.concrete.CourseDepartment;
import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.enums.RequestLetterType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.factories.RequestLetterFactory;
import com.techiness.collegecoordinator.factories.UserFactory;
import com.techiness.collegecoordinator.utils.Menu;

import java.util.HashSet;
import java.util.Set;

import static com.techiness.collegecoordinator.utils.IOUtils.*;

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
                Admin admin = accountsManager.getAdmin();
                Department currentDepartment = accountsManager.getDepartments(hod.getDeptId());
                while(!currentDepartment.checkIfFacultyIdValid(facultyIdToBeRemoved))
                {
                    facultyIdToBeRemoved = getUserInput(facultyIdToBeRemoved, "Faculty ID of the Faculty whom you want to remove from the Department");
                }
                RequestLetter facultyResignationLetter = RequestLetterFactory.getInstance().getLetter(facultyIdToBeRemoved, admin.getId(), RequestLetterType.RESIGNATION);
                printlnWithAnim("Submitting Resignation Letter of Faculty to Admin...");
                if(admin.addLetter(facultyResignationLetter))
                {
                    println2("Submitted the Resignation Request Letter of the Faculty to be removed to Admin successfully !");
                }
                else
                {
                    println2("Failed to submit the Resignation Request Letter of the Faculty to the Admin");
                }
                break;
            //Transfer a Faculty to another Department
            case 29:
                String facultyIdToBeTransferred = "";
                Admin admin2 = accountsManager.getAdmin();
                Department currentDepartment2 = accountsManager.getDepartments(hod.getDeptId());
                while(!currentDepartment2.checkIfFacultyIdValid(facultyIdToBeTransferred))
                {
                    facultyIdToBeTransferred = getUserInput(facultyIdToBeTransferred, "Faculty ID of the Faculty whom you want to transfer to another Department");
                }
                RequestLetter facultyTransferLetter = RequestLetterFactory.getInstance().getLetter(facultyIdToBeTransferred, admin2.getId(), RequestLetterType.DEPT_CHANGE, currentDepartment2.getId());
                printlnWithAnim("Submitting Department Change Request Letter of Faculty to Admin...");
                if(admin2.addLetter(facultyTransferLetter))
                {
                    println2("Submitted the Department Change Request Letter of the Faculty to be removed to Admin successfully !");
                }
                else
                {
                    println2("Failed to submit the Department Change Request Letter of the Faculty to the Admin");
                }
                break;
            //Display all the faculties under the Department
            case 30:
                println2("List of Faculties Under the Department");
                println2(getStringOfNameableMap(accountsManager.getDepartments(hod.getDeptId()).getFaculties()));
                break;
            //Add Subject(s) handled by a Faculty
            case 31:
                String facultyIdToAddSubjects = "";
                CourseDepartment currentDepartment3 = (CourseDepartment) accountsManager.getDepartments(hod.getDeptId());
                while(!currentDepartment3.checkIfFacultyIdValid(facultyIdToAddSubjects))
                {
                    facultyIdToAddSubjects = getUserInput(facultyIdToAddSubjects, "Faculty ID of the Faculty for whom you want to assign subjects");
                }
                Faculty facultyToAddSubjects = currentDepartment3.getFaculties(facultyIdToAddSubjects);
                Set<String> availableSubjects = currentDepartment3.getCourseSubjects();
                availableSubjects.removeAll(facultyToAddSubjects.getSubjectsHandled());
                Menu subjectMenu = new Menu.MenuBuilder().setHeader("Add Subject(s) Menu")
                        .addMultipleOptions(getStringArrayOfStringSet(availableSubjects))
                        .addOption("Stop adding Subjects")
                        .build();
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
