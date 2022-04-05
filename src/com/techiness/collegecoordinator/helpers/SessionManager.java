package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.consoleui.*;
import com.techiness.collegecoordinator.enums.UserType;

public final class SessionManager
{
    private static SessionManager instance = null;
    private UserType currentUserType;
    private User currentUser;
    private AccountsManager accountsManager;
    private boolean factoryResetDone = false;
    private boolean isFirstTime = true;

    private SessionManager()
    {
        currentUserType = null;
        currentUser = null;
        accountsManager = AccountsManager.getInstance();
    }

    public synchronized static SessionManager getInstance()
    {
        if(instance == null)
        {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean isFirstTime()
    {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime)
    {
        isFirstTime = firstTime;
    }

    public UserType getCurrentUserType()
    {
        return currentUserType;
    }

    public void setCurrentUserType(UserType currentUserType)
    {
        this.currentUserType = currentUserType;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public String registerAdmin(Admin admin)
    {
        if(accountsManager.getAdmin() != null)
            return null;
        accountsManager.setAdmin(admin);
        accountsManager.getUsers().put(admin.getId(),admin);
        return admin.getId();
    }

    public boolean loginAdmin(String adminId, String adminPassword)
    {
        Admin registeredAdmin = accountsManager.getAdmin();
        if(registeredAdmin == null || !registeredAdmin.getId().equals(adminId) || !registeredAdmin.getPassword().equals(adminPassword))
            return false;
        currentUser = registeredAdmin;
        currentUserType = UserType.ADMIN;
        return true;
    }

    public boolean loginUser(String userId, String password)
    {
        String deptId = userId.substring(userId.indexOf('@')+1,userId.indexOf('_'));
        UserType currentUserType = StringToEnumUtils.getUserTypeFromUserId(userId);
        Department department = accountsManager.getDepartments(deptId);
        User reference;
        if(department == null)
            return false;
        switch (currentUserType)
        {
            case STUDENT:
                if(!department.getStudents().containsKey(userId) || department.getStudents().get(userId)==null)
                    return false;
                reference = department.getStudents().get(userId);
                break;
            case FACULTY:
                if(!department.getFaculties().containsKey(userId) || department.getFaculties().get(userId)==null)
                    return false;
                reference = department.getFaculties().get(userId);
                break;
            case HOD:
                if(department.getHod() == null || !(department instanceof CourseDepartment))
                    return false;
                reference = department.getHod();
                break;
            case TRAINING_HEAD:
                if(!(department instanceof PlacementDepartment) || department.getHod() == null)
                    return false;
                reference = department.getHod();
                break;
            default:
                return false;
        }
        if(reference == null)
            return false;
        if(password.equals(reference.getPassword()))
        {
            currentUser = reference;
            currentUserType = UserType.valueOf(userId.substring(userId.indexOf("_")+1));
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean redirectToRespectiveUI()
    {
        if(currentUser == null)
            return false;

        switch (currentUserType)
        {
            case ADMIN:
                AdminUI adminUI = new AdminUI( (Admin) currentUser);
                currentUser.addObserver(adminUI);
                adminUI.displayUIAndExecuteActions();
                return true;

            case HOD:
                HoDUI hodUI = new HoDUI((HoD) currentUser);
                currentUser.addObserver(hodUI);
                hodUI.displayUIAndExecuteActions();
                return true;

            case TRAINING_HEAD:
                TrainingHeadUI trainingHeadUI = new TrainingHeadUI((TrainingHead) currentUser);
                currentUser.addObserver(trainingHeadUI);
                trainingHeadUI.displayUIAndExecuteActions();
                return true;

            case FACULTY:
                FacultyUI facultyUI = new FacultyUI((Faculty) currentUser);
                currentUser.addObserver(facultyUI);
                facultyUI.displayUIAndExecuteActions();
                return true;

            case STUDENT:
                StudentUI studentUI = new StudentUI((Student) currentUser);
                currentUser.addObserver(studentUI);
                studentUI.displayUIAndExecuteActions();
                return true;

            default:
                return false;
        }
    }

    public boolean logoutUser()
    {
        if(currentUser==null)
            return false;
        currentUser.deleteObservers();
        currentUser = null;
        currentUserType = null;
        return true;
    }

    public boolean isFactoryResetDone()
    {
        return factoryResetDone;
    }

    public void setFactoryResetDone(boolean factoryResetDone)
    {
        this.factoryResetDone = factoryResetDone;
    }
}
