package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;
import javafx.util.Pair;
import java.util.HashMap;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;
import static com.techiness.collegecoordinator.consoleui.IOUtils.println2;

public final class AdminUI extends AbstractUserUI
{
    private final Admin admin;
    private final AccountsManager accountsManager;
    private final SessionManager sessionManager;
    public AdminUI(Admin admin)
    {
        this.admin = admin;
        this.accountsManager = AccountsManager.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Admin Menu")
                .addOption("Create a department and assign new HoD")
                .addOption("Create a department and assign an existing Faculty as its HoD")
                .addOption("Promote existing Faculty as HoD for same department and Demote current HoD as Faculty of same department")
                .addOption("Promote existing Faculty as HoD for same department and Demote current HoD as Faculty of different department")
                .addOption("Promote existing Faculty as HoD for same department and Relieve current HoD from job")
                .addOption("Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of same department")
                .addOption("Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of different department")
                .addOption("Promote Faculty from another department as HoD for a department and Relieve current HoD from job")
                .addOption("Remove a department")
                .addOption("Get existing departments")
                .addOption("Logout")
                .addOption("Factory Reset Application")
                .build());
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
            if(deptChoice == -1)
                continue;
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

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        AccountsManager accountsManager = AccountsManager.getInstance();
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if (choice == -1)
                continue;
            switch (choice) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    executeGeneralUserActions(admin, choice);
                    break;
                case 7:
                    //Create a department and assign new HoD
                    Department newDepartment = createDepartment();
                    if (newDepartment != null) {
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
                        admin.addDepartment(newDepartment);
                        println("HoD: " + hod.getName() + " assigned to Department: " + newDepartment.getId() + " successfully \n!");
                    } else {
                        println("Department creation cancelled!\n");
                    }
                    break;

                case 8:
                    //Create a department and assign an existing faculty as its HoD
                    Department newDepartment1 = createDepartment();
                    if (newDepartment1 != null) {
                        printDepartmentCreationSuccess(newDepartment1);
                        println();
                        println("You have to assign HoD to the newly created department now !");
                        Department existingDepartment = null;
                        String deptId = "";
                        while (existingDepartment == null) {
                            deptId = getUserInput(deptId, "Department ID of the Faculty\'s Department");
                            existingDepartment = accountsManager.getDepartments(deptId);
                            if (existingDepartment == null) {
                                println("Invalid Department ID ! Enter a valid Department ID...");
                            }
                        }
                        Faculty existingFaculty = null;
                        String facultyId = "";
                        while (existingFaculty == null) {
                            facultyId = getUserInput(facultyId, "Faculty ID of the Faculty to be promoted as HoD");
                            existingFaculty = existingDepartment.getFaculties(facultyId);
                            if (existingFaculty == null) {
                                println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                            }
                        }
                        HoD newHoD = admin.promoteFacultyToDifferentDeptHoD(facultyId, deptId, newDepartment1.getId(), false).getKey();
                        if (newHoD != null) {
                            admin.addDepartment(newDepartment1);
                            printAccountCreationSuccess(newHoD);
                            println2("HoD " + newHoD.getName() + " assigned to Department: " + newDepartment1.getId() + " successfully!");
                        } else {
                            println2("HoD assignment to new Department failed ! Department Creation failed !");
                        }
                    } else {
                        println2("Department creation cancelled!");
                    }
                    break;

                case 9:
                    //Promote existing Faculty as HoD for SAME department and Demote current HoD as Faculty of same department
                    Department existingDepartment9 = null;
                    String deptId9 = "";
                    while (existingDepartment9 == null) {
                        deptId9 = getUserInput(deptId9, "Department ID");
                        existingDepartment9 = accountsManager.getDepartments(deptId9);
                        if (existingDepartment9 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty9 = null;
                    String existingFacultyId9 = "";
                    while (existingFaculty9 == null) {
                        existingFacultyId9 = getUserInput(existingFacultyId9, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty9 = existingDepartment9.getFaculties(existingFacultyId9);
                        if (existingFaculty9 == null) {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    printlnWithAnim("Promoting Faculty: " + existingFacultyId9 + " as HoD and Demoting current HoD: " + existingDepartment9.getHod().getId() + " as Faculty...");
                    Pair<HoD, Faculty> result9 = admin.promoteFacultyToSameDeptHoD(existingFacultyId9, deptId9, true);
                    HoD newHoD9 = result9.getKey();
                    Faculty newFaculty9 = result9.getValue();
                    if (result9 != null) {
                        println2("Old Faculty: " + existingFacultyId9 + " promoted as Current HoD: " + newHoD9.getId() + " successfully !");
                        printAccountCreationSuccess(newHoD9);
                        println2("Old HoD: " + existingDepartment9.getHod().getId() + " promoted as Current Faculty: " + newFaculty9.getId() + " successfully !");
                        printAccountCreationSuccess(newFaculty9);
                    } else {
                        println2("Failed to promote Faculty as HoD !");
                    }
                    break;

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
                case 18:
                    println2("WARNING: Factory Resetting the Application will clear all the User Data and all Accounts including this Admin Account will be deleted !!!");
                    String resetChoice = "";
                    while (true) {
                        resetChoice = getUserInput(resetChoice, "Do you want to proceed ? Enter Yes/No");
                        if (!(resetChoice.equalsIgnoreCase("Yes") || resetChoice.equalsIgnoreCase("No"))) {
                            println2("Enter either Yes/No");
                            continue;
                        }
                        if (resetChoice.equalsIgnoreCase("Yes")) {
                            printlnWithAnim("Factory resetting the Application...");
                            try {
                                SerializationHelper.getInstance().clearStoredData("departments.txt", "isFirstTime.txt", "users.txt");
                                AccountsManager.getInstance().setDepartments(new HashMap<>());
                                AccountsManager.getInstance().setUsers(new HashMap<>());
                                SessionManager.getInstance().setFirstTime(true);
                                SessionManager.getInstance().logoutUser();
                                SerializationHelper.getInstance().clearStoredData("admin.txt");
                                AccountsManager.getInstance().setAdmin(null);
                                sessionManager.setFactoryResetDone(true);
                            } catch (Exception e) {
                                println("Error occurred during Factory Reset!");
                            }
                            printlnValLn("The Application will behave like opening for the First Time !");
                            return;
                        } else if (resetChoice.equalsIgnoreCase("No")) {
                            println2("Cancelled Factory Reset");
                            break;
                        }
                    }
            }
        }
    }
}
