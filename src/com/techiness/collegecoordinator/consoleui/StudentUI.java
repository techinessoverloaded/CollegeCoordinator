package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Menu;
import com.techiness.collegecoordinator.helpers.SessionManager;

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
                .addOption("Check my Grade")
                .addOption("Request Leave or OD")
                .addOption("Check if Leave or OD got Approved or not")
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
