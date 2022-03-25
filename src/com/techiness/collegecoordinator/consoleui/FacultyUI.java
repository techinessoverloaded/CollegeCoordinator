package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.concrete.Faculty;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.SessionManager;

public class FacultyUI extends AbstractUserUI
{
    private Faculty faculty;
    protected final AccountsManager accountsManager;
    protected final SessionManager sessionManager;

    public FacultyUI(Faculty faculty)
    {
        this.faculty = faculty;
        this.accountsManager = AccountsManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
    }

    public FacultyUI()
    {
        this.accountsManager = AccountsManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    public void displayUIAndExecuteActions()
    {

    }
}
