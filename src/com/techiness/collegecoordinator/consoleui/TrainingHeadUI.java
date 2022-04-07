package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.TrainingHead;
import com.techiness.collegecoordinator.utils.Menu;

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

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if (choice == -1)
                continue;
        }
    }
}
