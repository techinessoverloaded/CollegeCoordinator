package com.techiness.collegecoordinator.consoleui;

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
                RequestLetter facultyResignationLetter = RequestLetterFactory.getInstance().getLetter(facultyIdToBeRemoved, admin.getId(), hod.getDeptId(), RequestLetterType.RESIGNATION);
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
                RequestLetter facultyTransferLetter = RequestLetterFactory.getInstance().getLetter(facultyIdToBeTransferred, admin2.getId(), hod.getDeptId(), RequestLetterType.DEPT_CHANGE);
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
                Set<String> subjectsToBeAdded = new HashSet<>();
                Menu subjectMenu = new Menu.MenuBuilder().setHeader("Add Subject(s) Menu")
                        .addMultipleOptions(getStringArrayOfStringSet(availableSubjects))
                        .addOption("Stop adding Subjects")
                        .build();
                int selectedSubjectChoice = -1;
                println2("Keep selecting Subjects one by one to add to the Faculty 's subjects...");
                while(selectedSubjectChoice < subjectMenu.getOptions().size()+1)
                {
                    selectedSubjectChoice = subjectMenu.displayMenuAndGetChoice();

                    if(selectedSubjectChoice == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if(selectedSubjectChoice >= 1 && selectedSubjectChoice <= subjectMenu.getOptions().size()-1)
                    {
                        subjectsToBeAdded.add(subjectMenu.getOptions(selectedSubjectChoice));
                        subjectMenu.removeOption(selectedSubjectChoice);
                    }

                    else if(selectedSubjectChoice == subjectMenu.getOptions().size())
                    {
                        if(subjectsToBeAdded.size() == 0)
                        {
                            println("Must add at least one Subject to be handled !");
                        }
                        else
                            break;
                    }
                }
                printlnWithAnim("Adding the chosen subjects to faculty's responsibilities...");
                if(hod.addSubjectHandled(facultyIdToAddSubjects, subjectsToBeAdded))
                {
                    println2("Added the chosen subjects to faculty's responsibilities successfully !");
                }
                else
                {
                    println2("Failed to add the chosen subjects to faculty's responsibilities !");
                }
                break;
            //Remove Subject(s) handled by a Faculty
            case 32:
                String facultyIdToRemoveSubjects = "";
                CourseDepartment currentDepartment4 = (CourseDepartment) accountsManager.getDepartments(hod.getDeptId());
                while(!currentDepartment4.checkIfFacultyIdValid(facultyIdToRemoveSubjects))
                {
                    facultyIdToAddSubjects = getUserInput(facultyIdToRemoveSubjects, "Faculty ID of the Faculty for whom you want to remove the assigned subjects");
                }
                Faculty facultyToRemoveSubjects = currentDepartment4.getFaculties(facultyIdToRemoveSubjects);
                Set<String> alreadyAssignedSubjects = facultyToRemoveSubjects.getSubjectsHandled();
                Set<String> subjectsToBeRemoved = new HashSet<>();
                    Menu subjectMenu2 = new Menu.MenuBuilder().setHeader("Remove Subject(s) Menu")
                        .addMultipleOptions(getStringArrayOfStringSet(alreadyAssignedSubjects))
                        .addOption("Stop removing Subjects")
                        .build();
                int selectedSubjectChoice2 = -1;
                println2("Keep selecting Subjects one by one to remove from the Faculty 's subjects...");
                while(selectedSubjectChoice2 < subjectMenu2.getOptions().size()+1)
                {
                    selectedSubjectChoice2 = subjectMenu2.displayMenuAndGetChoice();

                    if(selectedSubjectChoice2 == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if(selectedSubjectChoice2 >= 1 && selectedSubjectChoice2 <= subjectMenu2.getOptions().size()-1)
                    {
                        subjectsToBeRemoved.add(subjectMenu2.getOptions(selectedSubjectChoice2));
                        subjectMenu2.removeOption(selectedSubjectChoice2);
                    }

                    else if(selectedSubjectChoice2 == subjectMenu2.getOptions().size())
                    {
                        if(subjectsToBeRemoved.size() == 0)
                        {
                            println("Must add at least one Subject to be handled !");
                        }
                        else
                            break;
                    }
                }
                printlnWithAnim("Removing the chosen subjects from the faculty's responsibilities...");
                if(hod.addSubjectHandled(facultyIdToRemoveSubjects, subjectsToBeRemoved))
                {
                    println2("Removed the chosen subjects from the faculty's responsibilities successfully !");
                }
                else
                {
                    println2("Failed to remove the chosen subjects to faculty's responsibilities !");
                }
                break;
            //Display all the Request Letters
            case 33:

                break;
            //View and Approve/Disapprove a Request RequestLetter
            case 34:
                break;
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
