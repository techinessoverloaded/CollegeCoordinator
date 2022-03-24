package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public final class UserUI<T extends User> extends AbstractUserUI
{
    private T user;
    private UserType userType;
    private AccountsManager accountsManager;
    private SessionManager sessionManager;

    public UserUI(T user)
    {
        this.user = user;
        accountsManager = AccountsManager.getInstance();
        sessionManager = SessionManager.getInstance();
        inferUserTypeFromUser();
        prepareMenuForUser();
    }

    private void inferUserTypeFromUser()
    {
        if (user instanceof Admin)
            userType = UserType.ADMIN;
        else if (user instanceof TrainingHead)
            userType = UserType.TRAINING_HEAD;
        else if (user instanceof HoD)
            userType = UserType.HOD;
        else if (user instanceof Faculty)
            userType = UserType.FACULTY;
        else
            userType = UserType.STUDENT;
    }

    private void prepareMenuForUser()
    {
        switch (userType)
        {
            case ADMIN:
                this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Admin Menu")
                        .addOption("Create a department and assign new HoD")
                        .addOption("Create a department and assign an existing faculty as its HoD")
                        .addOption("Promote existing faculty as HoD for department and Demote current HoD as staff of same department")
                        .addOption("Promote existing faculty as HoD for department and Demote current HoD as staff of different department")
                        .addOption("Promote existing faculty as HoD for department and Relieve current HoD from job")
                        .addOption("Promote faculty from another department as HoD for a department and Demote current HoD as staff of same department")
                        .addOption("Promote faculty from another department as HoD for a department and Demote current HoD as staff of different department")
                        .addOption("Promote faculty from another department as HoD for a department and Relieve current HoD from job")
                        .addOption("Remove a department")
                        .addOption("Get existing departments")
                        .addOption("Logout")
                        .build());
                break;
        }
    }


    public void displayUIAndExecuteActions()
    {
        switch (userType)
        {
            case ADMIN:
                adminActions();
                break;
        }
    }

    private Department createDepartment()
    {
        int deptChoice = -1;
        Menu departmentMenu = new Menu.MenuBuilder().setHeader("Department Choice")
                .addOption("Course Department")
                .addOption("Placement Department")
                .addOption("Cancel Department creation")
                .build();
        while(true)
        {
            deptChoice = departmentMenu.displayMenuAndGetChoice();
            switch (deptChoice)
            {
                case 1:
                    Department newDepartment = new DepartmentCreationHelper(DepartmentType.COURSE).getNewDepartment();
                    return newDepartment == null ? null : (CourseDepartment) newDepartment;
                case 2:
                    Department newDepartment2 = new DepartmentCreationHelper(DepartmentType.PLACEMENT).getNewDepartment();
                    return newDepartment2 == null ? null : (PlacementDepartment) newDepartment2;
                case 3:
                    return null;
                default:
                    println("Invalid choice! Enter a valid choice...");
            }
        }
    }

    public void adminActions()
    {
        Admin admin = (Admin) user;
        int choice = -1;
        AccountsManager accountsManager = AccountsManager.getInstance();
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            switch (choice)
            {
                case 1: case 2: case 3: case 4: case 5: case 6:
                executeGeneralUserActions(admin,choice);
                break;
                case 7:
                    Department newDepartment = createDepartment();
                    if (newDepartment != null)
                    {
                        admin.addDepartment(newDepartment);
                        printDepartmentCreationSuccess(newDepartment);
                        println("You have to assign HoD to the newly created department now !");
                        println("Create an account for the HoD and note down the credentials for the HoD to use later !");
                        HoD hod = (HoD) new UserCreationHelper(UserType.HOD).getNewUser();
                        println();
                        printAccountCreationSuccess(hod);
                        println();
                        printlnWithAnim("Assigning HoD: " + hod.getName() + " to " + newDepartment.getName() + " with department ID: " + newDepartment.getId());
                        hod.setDeptId(newDepartment.getId());
                        newDepartment.setHod(hod);
                        accountsManager.getUsers().put(hod.getId(), hod);
                        println("HoD: " + hod.getName() + " assigned to Department: " + newDepartment.getId() + " successfully \n!");
                    }
                    else
                    {
                        println("Department creation cancelled!\n");
                    }
                    break;

                case 8:
                    Department newDepartment1 = createDepartment();
                    if (newDepartment1 != null)
                    {
                        admin.addDepartment(newDepartment1);
                        printDepartmentCreationSuccess(newDepartment1);
                        println();
                        println("You have to assign HoD to the newly created department now !");
                        Department existingDepartment = null;
                        String deptId = null;
                        while(existingDepartment == null)
                        {
                            println("Enter the department ID of the faculty\'s Department:");
                            deptId = readLine();
                            existingDepartment = accountsManager.getDepartments(deptId);
                            if (existingDepartment == null)
                            {
                                println("Invalid Department ID ! Enter a valid Department ID...");
                            }
                        }
                        Faculty existingFaculty = null;
                        String facultyId = null;
                        while(existingFaculty == null)
                        {
                            println("Enter the faculty ID of the faculty to be promoted as HoD");
                            facultyId = readLine();
                            existingFaculty = existingDepartment.getFaculties(facultyId);
                            if(existingFaculty == null)
                            {
                                println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                            }
                        }
                        HoD newHoD = admin.promoteFacultyToDifferentDeptHoD(facultyId, deptId, newDepartment1.getId(), false);
                        if(newHoD != null)
                        {
                            printAccountCreationSuccess(newHoD);
                            println();
                            println("HoD "+newHoD.getName()+" assigned to Department: "+newDepartment1.getId()+" successfully\n!");
                        }
                        else
                        {
                            println("HoD assignment to new Department failed !");
                        }
                    }
                    else
                    {
                        println("Department creation cancelled!\n");
                    }
                    break;

                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    printlnWithAnim("Logging out...");
                    sessionManager.logoutUser();
                    return;
            }
        }
    }
}