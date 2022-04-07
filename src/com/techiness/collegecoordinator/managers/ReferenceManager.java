package com.techiness.collegecoordinator.managers;

import com.techiness.collegecoordinator.concrete.*;
import java.util.Observable;
import java.util.Observer;

public final class ReferenceManager implements Observer
{
    private static ReferenceManager instance = null;
    private final AccountsManager accountsManager;

    private ReferenceManager()
    {
        accountsManager = AccountsManager.getInstance();
    }

    public synchronized static ReferenceManager getInstance()
    {
        if(instance == null)
            instance = new ReferenceManager();
        return instance;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof Admin)
        {
            Admin admin = (Admin) o;
            accountsManager.getUsers().replace(admin.getId(),admin);
            accountsManager.setAdmin(admin);
        }
        else if(o instanceof TrainingHead)
        {
            TrainingHead trainingHead = (TrainingHead) o;
            accountsManager.getUsers().replace(trainingHead.getId(), trainingHead);
            accountsManager.getDepartments(trainingHead.getDeptId()).setHod(trainingHead);
        }
        else if(o instanceof HoD)
        {
            HoD hoD = (HoD) o;
            accountsManager.getUsers().replace(hoD.getId(), hoD);
            accountsManager.getDepartments(hoD.getDeptId()).setHod(hoD);
        }
        else if(o instanceof Faculty)
        {
            Faculty faculty = (Faculty) o;
            accountsManager.getUsers().replace(faculty.getId(), faculty);
            accountsManager.getDepartments(faculty.getDeptId()).getFaculties().replace(faculty.getId(),faculty);
        }
        else if(o instanceof Student)
        {
            Student student = (Student) o;
            accountsManager.getUsers().replace(student.getId(), student);
            accountsManager.getDepartments(student.getDeptId()).getStudents().replace(student.getId(), student);
        }
    }
}
