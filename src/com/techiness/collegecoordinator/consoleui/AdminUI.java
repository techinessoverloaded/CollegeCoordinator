package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.factories.DepartmentFactory;
import com.techiness.collegecoordinator.factories.UserFactory;
import com.techiness.collegecoordinator.utils.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.techiness.collegecoordinator.utils.IOUtils.*;
import static com.techiness.collegecoordinator.utils.IOUtils.println2;

public final class AdminUI extends AbstractUserUI
{
    private final Admin admin;
    public AdminUI(Admin admin)
    {
        super();
        this.admin = admin;
        this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Admin Menu ")
                .addOption("Create a department and assign new HoD")
                .addOption("Create a department and assign an existing Faculty as its HoD")
                .addOption("Promote existing Faculty as HoD for same department and Demote current HoD as Faculty of same department")
                .addOption("Promote existing Faculty as HoD for same department and Demote current HoD as Faculty of different department")
                .addOption("Promote existing Faculty as HoD for same department and Relieve current HoD from job")
                .addOption("Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of same department")
                .addOption("Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of different department")
                .addOption("Promote Faculty from another department as HoD for a department and Relieve current HoD from job")
                .addOption("Remove a department")
                .addOption("View existing departments")
                .addOption("Display Department Details")
                .addOption("Display all the Request Letters")
                .addOption("View and Approve/Disapprove a Request Letter")
                .addOption("Logout")
                .addOption("Factory Reset Application")
                .build());
    }

    private boolean ensureDepartments(boolean moreThanOne)
    {
        if(moreThanOne)
            return accountsManager.getDepartments().size() > 1;
        return accountsManager.getDepartments().size() > 0;
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            List<Department> departmentsWithoutHoD = accountsManager.getDepartments().values().stream().filter(department -> department.getHod() == null).collect(Collectors.toList());
            if(departmentsWithoutHoD.size()>1)
            {
                println2("The Following Departments Don't Have a HoD !!! It is recommended to Assign an HoD for these departments !!!");
                departmentsWithoutHoD.forEach(IOUtils::println);
            }
            choice = userMenu.displayMenuAndGetChoice();
            if (choice == -1)
                continue;
            if(choice >= 1 && choice <= 7)
            {
                executeGeneralUserActions(admin, choice);
                continue;
            }
            switch (choice)
            {
                case 8:
                    //Create a department and assign new HoD
                    Department newDepartment = null;
                    int deptChoice = -1;
                    Menu departmentMenu = new Menu.MenuBuilder().setHeader("Department Choice")
                            .addOption("Course Department")
                            .addOption("Placement Department")
                            .build();
                    while (deptChoice == -1)
                    {
                        deptChoice = departmentMenu.displayMenuAndGetChoice();
                        switch (deptChoice)
                        {
                            case 1:
                                newDepartment = DepartmentFactory.getInstance().getNewDepartment(DepartmentType.COURSE);
                                break;
                            case 2:
                                newDepartment = DepartmentFactory.getInstance().getNewDepartment(DepartmentType.PLACEMENT);
                                break;
                            default:
                                println("Invalid choice! Enter a valid choice...");
                        }
                    }
                    if (newDepartment != null)
                    {
                        printDepartmentDetails(newDepartment,true);
                        println("You have to assign HoD to the newly created department now !");
                        println("Create an account for the HoD and note down the credentials for the HoD to use later !");
                        if(newDepartment instanceof PlacementDepartment)
                        {
                            PlacementDepartment placementDepartment = (PlacementDepartment) newDepartment;
                            admin.addDepartment(placementDepartment);
                            TrainingHead trainingHead = (TrainingHead) UserFactory.getInstance().getNewUser(UserType.TRAINING_HEAD, Boolean.TRUE, newDepartment.getId());
                            printAccountDetails(trainingHead,true);
                            println();
                            printlnWithAnim("Assigning TrainingHead: " + trainingHead.getName() + " to " + newDepartment.getName() + " with department ID: " + newDepartment.getId());
                            trainingHead.setDeptId(placementDepartment.getId());
                            accountsManager.getPlacementDepartment().setTrainingHead(trainingHead);
                            accountsManager.getUsers().put(trainingHead.getId(), trainingHead);
                            println2("TrainingHead: " + trainingHead.getName() + " assigned to Department: " + newDepartment.getId() + " successfully !");
                        }
                        else
                        {
                            CourseDepartment courseDepartment = (CourseDepartment) newDepartment;
                            if(admin.addDepartment(courseDepartment))
                                println("Success");
                            HoD hod = (HoD) UserFactory.getInstance().getNewUser(UserType.HOD, Boolean.FALSE, newDepartment.getId());
                            printAccountDetails(hod, true);
                            println();
                            printlnWithAnim("Assigning HoD: " + hod.getName() + " to " + newDepartment.getName() + " with department ID: " + newDepartment.getId());
                            hod.setDeptId(courseDepartment.getId());
                            accountsManager.getDepartments(courseDepartment.getId()).setHod(hod);
                            accountsManager.getUsers().put(hod.getId(), hod);
                            println2("HoD: " + hod.getName() + " assigned to Department: " + newDepartment.getId() + " successfully !");
                        }
                    }
                    else
                    {
                        println("Department creation cancelled!\n");
                    }
                    break;

                case 9:
                    //Create a department and assign an existing faculty as its HoD
                    if(!ensureDepartments(false))
                    {
                        println2("No Departments are present !");
                        break;
                    }
                    Department newDepartment1 = null;
                    int deptChoice2 = -1;
                    Menu departmentMenu2 = new Menu.MenuBuilder().setHeader("Department Choice")
                            .addOption("Course Department")
                            .addOption("Placement Department")
                            .addOption("Cancel Department creation")
                            .build();
                    while (newDepartment1 == null)
                    {
                        deptChoice2 = departmentMenu2.displayMenuAndGetChoice();
                        switch (deptChoice2)
                        {
                            case 1:
                                newDepartment1 = DepartmentFactory.getInstance().getNewDepartment(DepartmentType.COURSE);
                                break;
                            case 2:
                                newDepartment1 = DepartmentFactory.getInstance().getNewDepartment(DepartmentType.PLACEMENT);
                                break;
                            case 3:
                                println2("Cancelling Department Creation...");
                                break;
                            default:
                                println("Invalid choice! Enter a valid choice...");
                        }
                    }
                    if (newDepartment1 != null)
                    {
                        printDepartmentDetails(newDepartment1,true);
                        println();
                        println("You have to assign HoD to the newly created department now !");
                        Department existingDepartment = null;
                        String deptId = "";
                        while (existingDepartment == null) {
                            deptId = getUserInput(deptId, "Department ID of the Faculty's Department");
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
                            printAccountDetails(newHoD,true);
                            println2("HoD " + newHoD.getName() + " assigned to Department: " + newDepartment1.getId() + " successfully!");
                        } else {
                            println2("HoD assignment to new Department failed ! Department Creation failed !");
                        }
                    }
                    else
                    {
                        println2("Department creation cancelled!");
                    }
                    break;

                case 10:
                    //Promote existing Faculty as HoD for SAME department and Demote current HoD as Faculty of same department
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
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
                    HoD oldHoD = existingDepartment9.getHod();
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
                    println2("Old Faculty: " + existingFacultyId9 + " promoted as Current HoD: " + newHoD9.getId() + " successfully !");
                    printAccountDetails(newHoD9,true);
                    println2("Old HoD: " + oldHoD.getId() + " demoted as Current Faculty: " + newFaculty9.getId() + " successfully !");
                    printAccountDetails(newFaculty9,true);
                    break;

                //Promote existing Faculty as HoD for same department and Demote current HoD as Faculty of different department
                case 11:
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
                    Department existingDepartment10 = null;
                    String deptId10 = "";
                    while (existingDepartment10 == null)
                    {
                        deptId10 = getUserInput(deptId10, "Department ID");
                        existingDepartment10 = accountsManager.getDepartments(deptId10);
                        if (existingDepartment10 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty10 = null;
                    String existingFacultyId10 = "";
                    HoD oldHod2 = existingDepartment10.getHod();
                    while (existingFaculty10 == null)
                    {
                        existingFacultyId10 = getUserInput(existingFacultyId10, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty10 = existingDepartment10.getFaculties(existingFacultyId10);
                        if (existingFaculty10 == null)
                        {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    String destinationDeptId = "";
                    while(!accountsManager.checkIfDeptIdExists(destinationDeptId))
                    {
                        destinationDeptId = getUserInput(destinationDeptId,"Destination Department ID");
                        if(!accountsManager.checkIfDeptIdExists(destinationDeptId))
                        {
                            println("Invalid Department ID ! Enter a Valid Department ID !");
                        }
                    }
                    printlnWithAnim("Promoting Existing Faculty as HoD for same Department and Demoting current HoD as Faculty of another Department");
                    Pair<HoD, Faculty> result2 = admin.promoteFacultyToSameDeptHoD(existingFacultyId10,deptId10,true,destinationDeptId);
                    HoD newHoD10 = result2.getKey();
                    Faculty newFaculty10 = result2.getValue();
                    println2("Old Faculty: " + existingFacultyId10 + " promoted as Current HoD: " + newHoD10.getId() + " successfully !");
                    printAccountDetails(newHoD10,true);
                    println2("Old HoD: " + oldHod2.getId() + " demoted as Faculty: " + newFaculty10.getId() + " of Department: "+ destinationDeptId+ " successfully !");
                    printAccountDetails(newFaculty10,true);
                    break;

                //Promote existing Faculty as HoD for same department and Relieve current HoD from job
                case 12:
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
                    Department existingDepartment11 = null;
                    String deptId11 = "";
                    while (existingDepartment11 == null) {
                        deptId11 = getUserInput(deptId11, "Department ID");
                        existingDepartment11 = accountsManager.getDepartments(deptId11);
                        if (existingDepartment11 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty11 = null;
                    String existingFacultyId11 = "";
                    HoD oldHod3 = existingDepartment11.getHod();
                    while (existingFaculty11 == null)
                    {
                        existingFacultyId11 = getUserInput(existingFacultyId11, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty11 = existingDepartment11.getFaculties(existingFacultyId11);
                        if (existingFaculty11 == null)
                        {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    printlnWithAnim("Promoting Faculty: " + existingFacultyId11 + " as HoD and Relieving current HoD: " + oldHod3.getId() + " from Job...");
                    Pair<HoD, Faculty> result11 = admin.promoteFacultyToSameDeptHoD(existingFacultyId11, deptId11, false);
                    HoD newHoD11 = result11.getKey();
                    println2("Old Faculty: " + existingFacultyId11 + " promoted as Current HoD: " + newHoD11.getId() + " successfully !");
                    printAccountDetails(newHoD11,true);
                    println2("Old HoD: " + oldHod3.getId() + " Relieved from Job successfully !");
                    break;

                //Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of same department
                case 13:
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
                    Department existingDepartment12 = null;
                    String deptId12 = "";
                    while (existingDepartment12 == null)
                    {
                        deptId12 = getUserInput(deptId12, "Department ID of the Faculty to be Promoted");
                        existingDepartment12 = accountsManager.getDepartments(deptId12);
                        if (existingDepartment12 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty12 = null;
                    String existingFacultyId12 = "";
                    while (existingFaculty12 == null)
                    {
                        existingFacultyId12 = getUserInput(existingFacultyId12, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty12 = existingDepartment12.getFaculties(existingFacultyId12);
                        if (existingFaculty12 == null)
                        {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    Department destinationDepartment = null;
                    String destinationDepartmentId = "";
                    while (destinationDepartment == null)
                    {
                        destinationDepartmentId = getUserInput(destinationDepartmentId, "Department ID of the Department where the Faculty needs to be assigned as HoD");
                        destinationDepartment = accountsManager.getDepartments(destinationDepartmentId);
                        if (destinationDepartment == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    HoD oldHoD4 = destinationDepartment.getHod();
                    printlnWithAnim("Promoting Faculty: " + existingFacultyId12 + " as HoD of "+destinationDepartmentId+" and Demoting current HoD: " + oldHoD4.getId() + " as Faculty...");
                    Pair<HoD, Faculty> result12 = admin.promoteFacultyToDifferentDeptHoD(existingFacultyId12, existingFacultyId12, destinationDepartmentId, true);
                    HoD newHoD12 = result12.getKey();
                    Faculty newFaculty12 = result12.getValue();
                    println2("Old Faculty: " + existingFacultyId12 + " promoted as Current HoD: " + newHoD12.getId() + " of Department: "+destinationDepartmentId+" successfully !");
                    printAccountDetails(newHoD12,true);
                    println2("Old HoD: " + oldHoD4.getId() + " demoted as Current Faculty: " + newFaculty12.getId() + " successfully !");
                    printAccountDetails(newFaculty12,true);
                    break;
                //Promote Faculty from another department as HoD for a department and Demote current HoD as Faculty of different department
                case 14:
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
                    Department existingDepartment13 = null;
                    String deptId13 = "";
                    while (existingDepartment13 == null)
                    {
                        deptId13 = getUserInput(deptId13, "Department ID of the Faculty to be Promoted");
                        existingDepartment13 = accountsManager.getDepartments(deptId13);
                        if (existingDepartment13 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty13 = null;
                    String existingFacultyId13 = "";
                    while (existingFaculty13 == null)
                    {
                        existingFacultyId13 = getUserInput(existingFacultyId13, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty13 = existingDepartment13.getFaculties(existingFacultyId13);
                        if (existingFaculty13 == null)
                        {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    Department destinationDepartmentOfNewHod = null;
                    String destinationDepartmentOfNewHodId = "";
                    while (destinationDepartmentOfNewHod == null)
                    {
                        destinationDepartmentOfNewHodId = getUserInput(destinationDepartmentOfNewHodId, "Department ID of the Department where the Faculty needs to be assigned as HoD");
                        destinationDepartmentOfNewHod = accountsManager.getDepartments(destinationDepartmentOfNewHodId);
                        if (destinationDepartmentOfNewHod == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    HoD oldHoD5 = destinationDepartmentOfNewHod.getHod();
                    Department destinationDepartmentOfNewFaculty = null;
                    String destinationDepartmentOfNewFacultyId = "";
                    while (destinationDepartmentOfNewFaculty == null)
                    {
                        destinationDepartmentOfNewFacultyId = getUserInput(destinationDepartmentOfNewFacultyId, "Department ID of the Department where the Faculty needs to be assigned as HoD");
                        destinationDepartmentOfNewFaculty = accountsManager.getDepartments(destinationDepartmentOfNewFacultyId);
                            if (destinationDepartmentOfNewFaculty == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    printlnWithAnim("Promoting Faculty: " + existingFacultyId13 + " as HoD of "+destinationDepartmentOfNewHodId+" and Demoting current HoD: " + oldHoD5.getId() + " as Faculty of "+destinationDepartmentOfNewFacultyId+"...");
                    Pair<HoD, Faculty> result13 = admin.promoteFacultyToDifferentDeptHoD(existingFacultyId13, existingFacultyId13, destinationDepartmentOfNewHodId, true, destinationDepartmentOfNewFacultyId);
                    HoD newHoD13 = result13.getKey();
                    Faculty newFaculty13 = result13.getValue();
                    println2("Old Faculty: " + existingFacultyId13 + " promoted as Current HoD: " + newHoD13.getId() + " of Department: "+destinationDepartmentOfNewHodId+" successfully !");
                    printAccountDetails(newHoD13,true);
                    println2("Old HoD: " + oldHoD5.getId() + " demoted as Current Faculty: " + newFaculty13.getId() + " of Department: "+destinationDepartmentOfNewFacultyId+" successfully !");
                    printAccountDetails(newFaculty13,true);
                    break;
                //Promote Faculty from another department as HoD for a department and Relieve current HoD from job
                case 15:
                    if(!ensureDepartments(true))
                    {
                        println2("Only one department is Present !");
                        break;
                    }
                    Department existingDepartment14 = null;
                    String deptId14 = "";
                    while (existingDepartment14 == null)
                    {
                        deptId14 = getUserInput(deptId14, "Department ID of the Faculty to be Promoted");
                        existingDepartment14 = accountsManager.getDepartments(deptId14);
                        if (existingDepartment14 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    Faculty existingFaculty14 = null;
                    String existingFacultyId14 = "";
                    while (existingFaculty14 == null)
                    {
                        existingFacultyId14 = getUserInput(existingFacultyId14, "Faculty ID of the Faculty to be Promoted as HoD");
                        existingFaculty14 = existingDepartment14.getFaculties(existingFacultyId14);
                        if (existingFaculty14 == null)
                        {
                            println("Invalid Faculty ID ! Enter a valid Faculty ID...");
                        }
                    }
                    Department destinationDepartment2 = null;
                    String destinationDepartmentId2 = "";
                    while (destinationDepartment2 == null)
                    {
                        destinationDepartmentId2 = getUserInput(destinationDepartmentId2, "Department ID of the Department where the Faculty needs to be assigned as HoD");
                        destinationDepartment2 = accountsManager.getDepartments(destinationDepartmentId2);
                        if (destinationDepartment2 == null)
                            println2("Invalid Department ID ! Enter a valid Department ID...");
                    }
                    HoD oldHoD6 = destinationDepartment2.getHod();
                    printlnWithAnim("Promoting Faculty: " + existingFacultyId14 + " as HoD of "+destinationDepartmentId2+" and Relieving current HoD: " + oldHoD6.getId() + " from Job...");
                    Pair<HoD, Faculty> result14 = admin.promoteFacultyToDifferentDeptHoD(existingFacultyId14, existingFacultyId14, destinationDepartmentId2, false);
                    HoD newHoD14 = result14.getKey();
                    println2("Old Faculty: " + existingFacultyId14 + " promoted as Current HoD: " + newHoD14.getId() + " of Department: "+destinationDepartmentId2+" successfully !");
                    printAccountDetails(newHoD14,true);
                    println2("Old HoD: " + oldHoD6.getId() + " Relieved from Job successfully !");
                    break;
                //Remove a department
                case 16:
                    if(!ensureDepartments(false))
                    {
                        println2("No Departments Exist !");
                        break;
                    }
                    String deptIdToBeRemoved = "";
                    while(!accountsManager.checkIfDeptIdExists(deptIdToBeRemoved))
                    {
                        deptIdToBeRemoved = getUserInput(deptIdToBeRemoved, "Department ID of the Department to be removed");
                        if(!accountsManager.checkIfDeptIdExists(deptIdToBeRemoved))
                        {
                            println("Invalid Department ID ! Enter a Valid Department ID !");
                        }
                    }
                    Menu yesOrNoMenu = Menu.getYesOrNoMenu();
                    int removeDepartmentChoice = -1;
                    println2("Are you sure that you want to remove the Entire Department along with its Faculties and Students from this System ?");
                    while(removeDepartmentChoice == -1)
                    {
                        removeDepartmentChoice = yesOrNoMenu.displayMenuAndGetChoice();
                        if(removeDepartmentChoice == -1)
                        {
                            println("Invalid Choice ! Enter a Valid Choice !");
                        }
                    }
                    if(removeDepartmentChoice == 1)
                    {
                        String confirmationPwd = "";
                        confirmationPwd = getUserInput(confirmationPwd, "Password to confirm Department Deletion");
                        if(confirmationPwd.equals(admin.getPassword()))
                        {
                            printlnWithAnim("Removing the Department from this System...");
                            admin.removeDepartment(deptIdToBeRemoved);
                            println2("Department with ID : "+deptIdToBeRemoved+" removed successfully from the system !");
                        }
                        else
                        {
                            printlnWithAnim("Incorrect Password ! Aborting the Remove Department Operation !");
                            break;
                        }
                    }
                    else
                    {
                        printlnWithAnim("Aborting the Remove Department Operation !");
                        break;
                    }
                    break;
                //View existing departments
                case 17:
                    if(!ensureDepartments(false))
                    {
                        println2("No Departments Exist !");
                        break;
                    }
                    println2("List of All Departments :");
                    println2(getStringOfNameableMap(accountsManager.getDepartments()));
                    break;
                //Display Department Details
                case 18:
                    String departmentId = "";
                    while (!accountsManager.checkIfDeptIdExists(departmentId))
                    {
                        departmentId = getUserInput(departmentId, "Department ID");
                        if(!accountsManager.checkIfDeptIdExists(departmentId))
                        {
                            println("Invalid Department ID ! Enter a Valid Department ID !");
                        }
                    }
                    Department department = accountsManager.getDepartments(departmentId);
                    println("Department Details :");
                    println2(department);
                    break;
                //Display all the Request Letters
                case 19:
                    Map<String, RequestLetter> requestLetterMap = admin.getLetters();
                    for(RequestLetter requestLetter: requestLetterMap.values())
                    {
                        printRequestLetter(requestLetter);
                    }
                    break;
                //View and Approve/Disapprove a Request Letter
                case 20:
                    String letterIdToApprove = "";
                    RequestLetter requestLetterToApprove = null;
                    while(requestLetterToApprove == null)
                    {
                        letterIdToApprove = getUserInput(letterIdToApprove,"Letter ID of the letter to be approved/disapproved");
                        requestLetterToApprove = admin.getLetters(letterIdToApprove);
                        if(requestLetterToApprove == null)
                            println2("Invalid Letter ID ! Enter a Valid Letter ID !");
                    }
                    println("Request Letter Details:");
                    println2(requestLetterToApprove);
                    Menu yesOrNoMenu2 = Menu.getYesOrNoMenu();
                    int yesOrNoChoice = -1;
                    println2("Do you want to Approve the Letter ?");
                    while(yesOrNoChoice == -1)
                    {
                        yesOrNoChoice = yesOrNoMenu2.displayMenuAndGetChoice();
                        if(yesOrNoChoice == -1)
                        {
                            println("Invalid Choice ! Enter a Valid Choice !");
                        }
                    }
                    if(yesOrNoChoice == 1)
                    {
                        printlnWithAnim("Approving Request Letter...");
                        println2("Request Letter has been approved successfully !");
                        requestLetterToApprove.setIsApproved(true);
                    }
                    else
                    {
                        printlnWithAnim("Disapproving Request Letter...");
                        println2("Request Letter has been disapproved successfully !");
                        requestLetterToApprove.setIsApproved(false);
                    }
                    break;
                //Logout
                case 21:
                    printlnWithAnim("Performing necessary Actions for Approved Request Letters before Logging out...");
                    List<RequestLetter> letters = admin.getLetters().values().stream().filter(requestLetter -> (requestLetter instanceof TCRequestLetter || requestLetter instanceof ResignationRequestLetter || requestLetter instanceof PromotionRequestLetter)).filter(RequestLetter::getIsApproved).collect(Collectors.toList());
                    for(RequestLetter letter: letters)
                    {
                        if(letter instanceof TCRequestLetter)
                        {
                            TCRequestLetter tcLetter = (TCRequestLetter) letter;
                            accountsManager.getDepartments(tcLetter.getRequesterDeptId()).getStudents().remove(tcLetter.getRequesterId());
                            accountsManager.getUsers().remove(tcLetter.getRequesterId());
                        }
                        else if(letter instanceof ResignationRequestLetter)
                        {
                            ResignationRequestLetter resignationRequestLetter = (ResignationRequestLetter) letter;
                            User hoDorFaculty = accountsManager.getUsers(resignationRequestLetter.getRequesterId());
                            if(hoDorFaculty instanceof HoD)
                            {
                                accountsManager.getDepartments(resignationRequestLetter.getRequesterDeptId()).setHod(null);
                                accountsManager.getUsers().remove(resignationRequestLetter.getRequesterId());
                            }
                            else
                            {
                                accountsManager.getDepartments(resignationRequestLetter.getRequesterDeptId()).getFaculties().remove(resignationRequestLetter.getRequesterId());
                                accountsManager.getUsers().remove(resignationRequestLetter.getRequesterId());
                            }
                        }
                        else if(letter instanceof PromotionRequestLetter)
                        {
                            PromotionRequestLetter promotionRequestLetter = (PromotionRequestLetter) letter;
                            if(promotionRequestLetter.getRequesterDeptId().equals(promotionRequestLetter.getDestinationDeptId()))
                                admin.promoteFacultyToSameDeptHoD(promotionRequestLetter.getRequesterId(), promotionRequestLetter.getRequesterDeptId(), true);
                            else
                                admin.promoteFacultyToDifferentDeptHoD(promotionRequestLetter.getRequesterId(), promotionRequestLetter.getRequesterDeptId(), promotionRequestLetter.getDestinationDeptId(), true);
                        }
                    }
                    printlnWithAnim("Logging out...");
                    sessionManager.logoutUser();
                    return;
                //Factory Reset
                case 22:
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
                                accountsManager.setDepartments(new HashMap<>());
                                accountsManager.setUsers(new HashMap<>());
                                sessionManager.setFirstTime(true);
                                sessionManager.logoutUser();
                                SerializationHelper.getInstance().clearStoredData("admin.txt");
                                accountsManager.setAdmin(null);
                                sessionManager.setFactoryResetDone(true);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                                println("Error occurred during Factory Reset!");
                            }
                            printlnValLn("The Application may behave like opening for the First Time...");
                            return;
                        }
                        else if (resetChoice.equalsIgnoreCase("No"))
                        {
                            println2("Cancelled Factory Reset");
                            break;
                        }
                    }
            }
        }
    }
}
