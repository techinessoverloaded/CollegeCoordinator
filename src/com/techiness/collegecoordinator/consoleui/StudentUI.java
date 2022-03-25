package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.SessionManager;

public final class StudentUI extends AbstractUserUI
{
    private Student student;
    private final AccountsManager accountsManager;
    private final SessionManager sessionManager;

    public StudentUI(Student student)
    {
        this.student = student;
        this.accountsManager = AccountsManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    public void displayUIAndExecuteActions()
    {

    }
}
