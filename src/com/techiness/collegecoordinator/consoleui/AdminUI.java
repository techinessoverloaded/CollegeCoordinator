package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.DepartmentCreationHelper;
import com.techiness.collegecoordinator.helpers.Menu;
import com.techiness.collegecoordinator.helpers.UserCreationHelper;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;
import static com.techiness.collegecoordinator.consoleui.IOUtils.printlnWithAnim;

public final class AdminUI extends GeneralUserUI
{
    public AdminUI()
    {
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
                .addOption("Logout and Exit Application")
                .build());
    }

    private Department createDepartment()
    {
        int deptChoice = -1;
        Menu departmentMenu = new Menu.MenuBuilder().setHeader("Department Choice")
                .addOption("Course Department")
                .addOption("Placement Department")
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
                default:
                    return null;
            }
        }
    }

    public void displayUIAndExecuteActions(Admin admin)
    {
        int choice;
        AccountsManager accountsManager = AccountsManager.getInstance();
        while((choice = userMenu.displayMenuAndGetChoice()) < 19)
        {
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
                        println();
                        println("You have to assign HoD to the newly created department now !");
                        println("Create an account for the HoD and note down the credentials for the HoD to use later !");
                        HoD hod = (HoD) new UserCreationHelper(UserType.HOD).getNewUser();
                        println();
                        printAccountCreationSuccess(hod);
                        println();
                        printlnWithAnim("Assigning HoD: " + hod.getName() + " to " + newDepartment.getName() + " with department ID: " + newDepartment.getId());
                        hod.setDeptId(newDepartment.getId());
                        newDepartment.setHod(hod);
                        println("HoD: " + hod.getName() + " assigned to Department: " + newDepartment.getId() + " successfully !");
                    }
                    else
                    {
                        println("Failed to create new Department!\n");
                    }
                    break;

                case 8:
                    println("Enter the department ID of the Department which you want to remove:");
                    String deptID = readLine();
                    //Department department = admin.getDepartments().get(deptID);
                    break;

                case 18:
                    printlnWithAnim("Logging out and exiting now...");
                    accountsManager.logoutUser();
                    return;
            }
        }
    }
}
