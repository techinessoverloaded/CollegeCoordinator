package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.TrainingHead;
import com.techiness.collegecoordinator.utils.Menu;

import static com.techiness.collegecoordinator.utils.IOUtils.println2;
import static com.techiness.collegecoordinator.utils.IOUtils.printlnWithAnim;

public final class TrainingHeadUI extends HoDUI
{
    private TrainingHead trainingHead;

    public TrainingHeadUI(TrainingHead trainingHead)
    {
        super();
        this.trainingHead = trainingHead;
        userMenu.removeOption(userMenu.indexOf("Logout"));
        userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Training Head Menu")
                .addOption("Add a Company visiting for Placement to the Department")
                .addOption("Remove a Company visiting for Placement to the Department")
                .addOption("Display all the Companies visiting for Placement to the Department")
                .addOption("Set if a Student is Placed/Unplaced")
                .addOption("Add an Offer for a Student")
                .addOption("Remove an Offer of a Student")
                .addOption("Display all the Offers obtained by a Student")
                .addOption("Logout")
                .build());
    }

    private void executeGeneralTrainingHeadActions(int choice)
    {
        switch (choice)
        {
            //Add a Company visiting for Placement to the Department
            case 34:
            break;
            //Remove a Company visiting for Placement to the Department
            case 35:
            break;
            //Display all the Companies visiting for Placement to the Department
            case 36:
            break;
            //Set if a Student is Placed/Unplaced
            case 37:
            break;
            //Add an Offer for a Student
            case 38:
            break;
            //Remove an Offer of a Student
            case 39:
            break;
            //Display all the Offers obtained by a Student
            case 40:
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
                executeGeneralUserActions(trainingHead, choice);
            else if(choice >= 8 && choice <= 25)
                executeGeneralFacultyActions(trainingHead, choice);
            else if(choice >= 26 && choice <= 33)
                executeGeneralHoDActions(trainingHead, choice);
            else if(choice >= 34 && choice <= userMenu.getOptions().size()-1)
                executeGeneralTrainingHeadActions(choice);
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
