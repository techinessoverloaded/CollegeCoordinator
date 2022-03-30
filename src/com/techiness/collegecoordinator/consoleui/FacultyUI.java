package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.helpers.Menu;
import java.util.EnumSet;

import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class FacultyUI extends AbstractUserUI
{
    private Faculty faculty;

    public FacultyUI(Faculty faculty)
    {
        this.faculty = faculty;
        prepareMenu();
    }

    protected FacultyUI()
    {
        prepareMenu();
    }

    private void prepareMenu()
    {
        this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Faculty Menu")
                .addOption("Display the Subjects to be handled")
                .addOption("Add Qualification(s)")
                .addOption("Remove Qualification(s)")
                .addOption("Display my Qualifications")
                .addOption("Set my experience")
                .addOption("Display my experience")
                .addOption("Add a Student to the Department")
                .addOption("Remove a Student from the Department")
                .addOption("Display the Students under the Department")
                .addOption("Set Grade for a Student")
                .addOption("Set if a Student needs Training or not")
                .addOption("Request Leave to HoD")
                .addOption("Request On Duty to HoD")
                .addOption("Request Department Change to Admin")
                .addOption("Request Promotion to Admin")
                .addOption("Request Resignation to Admin")
                .addOption("Check if Request Letter got Approved or not")
                .addOption("Logout")
                .build());
    }

    protected void executeGeneralFacultyActions(Faculty faculty , int selection)
    {
        switch (selection)
        {
            //Display the Subjects to be handled
            case 8:
                println2("Subjects Handled by you:");
                println2(faculty.getSubjectsHandled());
                break;

            //Add Qualification(s)
            case 9:
                if(Qualification.getEnumSetDifference(faculty.getQualifications()) == 0)
                {
                    println2("You already have all the available Qualifications ! Cannot add more !");
                    break;
                }
                Menu qualificationMenu = new Menu.MenuBuilder().setHeader("Add Qualification(s) Menu")
                        .addMultipleOptions(Qualification.getStringArrayOfSetDifferenceValues(faculty.getQualifications()))
                        .addOption("Stop adding Qualifications")
                        .build();
                EnumSet<Qualification> newQualifications = EnumSet.noneOf(Qualification.class);
                int selectedQualificationChoice = -1;
                println2("Keep selecting Degrees one by one to add to the existing Qualifications...");
                while(selectedQualificationChoice < qualificationMenu.getOptions().size()+1)
                {
                    selectedQualificationChoice = qualificationMenu.displayMenuAndGetChoice();

                    if (selectedQualificationChoice == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if (selectedQualificationChoice >= 1 && selectedQualificationChoice <= qualificationMenu.getOptions().size() - 1)
                    {
                        newQualifications.add(Qualification.valueOf(qualificationMenu.getOptions(selectedQualificationChoice)));
                        qualificationMenu.removeOption(selectedQualificationChoice);
                    }
                    else if (selectedQualificationChoice == qualificationMenu.getOptions().size())
                    {
                        if (newQualifications.size() == 0)
                        {
                            println("Must add at least one Qualification !");
                        }
                        else
                            break;
                    }
                }
                printlnWithAnim("Adding new Qualification(s) to existing Qualifications...");
                faculty.getQualifications().addAll(newQualifications);
                println2("New Qualification(s) added to existing Qualifications successfully !");
                break;
            //Remove a Qualification
            case 10:
                Menu qualificationMenu2 = new Menu.MenuBuilder().setHeader("Remove Qualification(s) Menu")
                        .addMultipleOptions(Qualification.getStringArrayOfValues(faculty.getQualifications().toArray(new Qualification[0])))
                        .addOption("Stop removing Qualifications")
                        .build();
                if(qualificationMenu2.getOptions().size() == 2)
                {
                    println2("You need at least 1 Qualification ! Cannot remove Qualifications anymore !");
                    break;
                }
                EnumSet<Qualification> newQualifications2 = EnumSet.noneOf(Qualification.class);
                int selectedQualificationChoice2 = -1;
                println2("Keep selecting Degrees one by one to remove from the existing Qualifications...");
                while(selectedQualificationChoice2 < qualificationMenu2.getOptions().size()+1)
                {
                    if(qualificationMenu2.getOptions().size() == 2)
                    {
                        println2("You need at least 1 Qualification ! Cannot remove Qualifications anymore !");
                        break;
                    }

                    selectedQualificationChoice2 = qualificationMenu2.displayMenuAndGetChoice();

                    if (selectedQualificationChoice2 == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if (selectedQualificationChoice2 >= 1 && selectedQualificationChoice2 <= qualificationMenu2.getOptions().size() - 1)
                    {
                        newQualifications2.add(Qualification.valueOf(qualificationMenu2.getOptions(selectedQualificationChoice2)));
                        qualificationMenu2.removeOption(selectedQualificationChoice2);
                    }
                    else if (selectedQualificationChoice2 == qualificationMenu2.getOptions().size())
                    {
                        if (newQualifications2.size() == 0)
                        {
                            println("Must remove at least one Qualification !");
                        }
                        else
                            break;
                    }
                }
                printlnWithAnim("Removing the selected Qualification(s) from the existing Qualifications...");
                faculty.getQualifications().removeAll(newQualifications2);
                println2("Selected Qualification(s) removed from the existing Qualifications successfully !");
                break;
            //Display my Qualifications
            case 11:
                println2("Your Qualifications are :");
                println2(faculty.getQualifications());
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
            default:
                return;
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

            else if(choice >= 8 && choice <= userMenu.getOptions().size()-1)
            {
                executeGeneralFacultyActions(choice);
            }

            else if(choice == userMenu.getOptions().size())
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
