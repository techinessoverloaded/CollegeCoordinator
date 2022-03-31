package com.techiness.collegecoordinator.consoleui;
import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.helpers.Menu;

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
                .addOption("Remove a Faculty from the Department and relieve the Faculty the from Job")
                .addOption("Remove a Faculty from the Department and transfer the Faculty to another Department")
                .addOption("Display all the faculties under the Department")
                .addOption("Add Subject(s) handled by a Faculty")
                .addOption("Remove Subject(s) handled by a Faculty")
                .addOption("Display all the Request Letters")
                .addOption("View and Approve/Disapprove a Request RequestLetter")
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
