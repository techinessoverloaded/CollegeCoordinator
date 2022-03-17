package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main
{
    private static Scanner scanner = null;
    private static Console console = null;

    public static void println(Object val) {
        System.out.println(val);
    }

    public static void println() {
        System.out.println();
    }

    public static void print(Object val) {
        System.out.print(val);
    }

    public static String readLine() {
        return scanner.nextLine().trim();
    }

    public static int readInt() {
        return scanner.nextInt();
    }

    public static double readDouble() {
        return scanner.nextDouble();
    }

    public static void printSymbols(char symbol, int count) {
        /*
         while(count--!=0)
            print(symbol);
         */
        Stream.generate(() -> symbol).limit(count).forEach(System.out::print);
    }

    public static void printTextWithinStarPattern(String text)
    {
        printSymbols('*', 14);
        print(" " + text + " ");
        printSymbols('*', 14);
        println();
        printSymbols('*', 30 + text.length());
        println();
        println();
    }

    public static void printlnWithAnim(String text)
    {
        for (char c : text.toCharArray())
        {
            print(c);
            try
            {
                Thread.sleep(230);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        println();
    }

    public static void printAccountCreationSuccess(User user)
    {
        println(user.getId().substring(user.getId().indexOf('_') + 1) + " Account created successfully with User ID: " + user.getId());
        println("\nAccount Details:\n\n" + user);
        println("NOTE: You would need your User ID and Password for logging in next time!\n");
        printlnWithAnim("Logging in now...");
        AccountsManager.getInstance().loginUser(user.getId(), user.getPassword());
        println();
    }

    public static void printDepartmentCreationSuccess(Department department)
    {
        println(department.getId().substring(department.getId().indexOf('@')+1) + " created successfully with Department ID: " + department.getId());
        println("\nDepartment Details:\n\n" + department);
        println();
    }

    public static String readPassword()
    {
        return console != null ? String.valueOf(console.readPassword()) : readLine();
    }

    public static Department createDepartment(Admin admin)
    {
        int deptChoice = -1;
        println("Choose the type of department (1-3):\n1. Circuit Branch Department\n2. Non-Circuit Branch Department\n3. Training and Placement Department\n4. Cancel Department Creation");
        switch (deptChoice)
        {
            case 1:
                CircuitBranchDepartment newCktDept = (CircuitBranchDepartment) new DepartmentCreationHelper(DepartmentType.CIRCUIT).getNewDepartment();
                admin.addDepartment(newCktDept);
                return newCktDept;

            case 2:
                NonCircuitBranchDepartment newNonCktDept = (NonCircuitBranchDepartment) new DepartmentCreationHelper(DepartmentType.NON_CIRCUIT).getNewDepartment();
                admin.addDepartment(newNonCktDept);
                return newNonCktDept;

            case 3:
                for (Department dept : admin.getDepartments().values()) {
                    if (dept instanceof PlacementDepartment)
                    {
                        println("Placement Department exists already ! Cannot create another one !");
                        return null;
                    }
                }
                PlacementDepartment placementDepartment = (PlacementDepartment) new DepartmentCreationHelper(DepartmentType.PLACEMENT).getNewDepartment();
                admin.addDepartment(placementDepartment);
                return placementDepartment;

            default:
                return null;
        }
    }

    public static Menu getGeneralUserMenu()
    {
        return new Menu.MenuBuilder()
                .addOption("Change name")
                .addOption("Change age")
                .addOption("Change gender")
                .addOption("Change phone number")
                .addOption("Change email ID")
                .addOption("Change password")
                .build();
    }

    public static void executeGeneralUserActions(User user,int selection)
    {
        switch (selection)
        {
            case 1:
                println("Enter the new name:");
                String newName = readLine();
                println("Enter your password to confirm name change:");
                String password = readPassword();
                if(password.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing name...");
                    user.setName(newName);
                    println("Account details after changing name\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Name Change Request failed!!!");
                }
                break;
        }
    }

    public static void adminOptions(Admin admin)
    {
        Menu.MenuBuilder adminMenuBuilder = new Menu.MenuBuilder(getGeneralUserMenu());
        Menu adminMenu = adminMenuBuilder.setHeader("Admin Menu")
                .addOption("Add a department")
                .addOption("Remove a department")
                .addOption("Get existing departments")
                .addOption("Assign/Change HoD of a Department")
                .addOption("Logout")
                .addOption("Logout and Exit Application")
                .build();
        printTextWithinStarPattern("CollegeCoordinator Admin Portal");
        int choice;
        while((choice = adminMenu.displayMenuAndGetChoice())<13)
        {
            switch (choice)
            {
                case 1: case 2: case 3: case 4: case 5: case 6:
                    executeGeneralUserActions(admin,choice);
                    break;
                case 7:
                    Department newDepartment = createDepartment(admin);
                    printDepartmentCreationSuccess(newDepartment);
                    if (newDepartment != null)
                    {
                        if (newDepartment.getHod() == null)
                        {
                            println("You have to assign HoD to the newly created department now !");
                            println("Create an account for the HoD and note down the credentials for the HoD to use later !");
                            HoD hod = (HoD) new UserCreatorHelper(UserType.HOD).getNewUser();
                            printAccountCreationSuccess(hod);
                            printlnWithAnim("Assigning HoD: " + hod.getName() + "to " + newDepartment.getName() + " with department ID: " + newDepartment.getId());
                            hod.setDeptId(newDepartment.getId());
                            newDepartment.setHod(hod);
                            println("HoD: " + hod.getName() + " assigned to Department: " + newDepartment.getId() + " successfully !");
                        }
                        else
                        {
                            println("Department has a HoD already!\n");
                        }
                    }
                    else
                    {
                        println("Failed to create new Department!\n");
                    }
                    break;
                case 8:
                    println("Enter the department ID of the Department which you want to remove:");
                    String deptID = readLine();
                    Department department = admin.getDepartments().get(deptID);
                    if(department!=null)
                    {

                    }
                    break;

                case 12:
                    printlnWithAnim("Logging out and exiting now...");
                    AccountsManager.getInstance().logoutUser();
                    return;
            }
        }
    }

    public static void facultyOptions(Faculty faculty) {

    }

    public static void studentOptions(Student student) {

    }

    public static void registerPrompt() {
        printTextWithinStarPattern("New User Registration");
        int userChoice = -1;
        do {
            println("Which type of user (1-2) are you ?\n");
            println("1. Faculty\n2. Student\n3. Exit\n");
            userChoice = readInt();
            readLine();
            switch (userChoice)
            {
                case 1:
                    UserCreatorHelper userCreatorHelper = new UserCreatorHelper(UserType.FACULTY);
                    Faculty newFaculty = (Faculty) userCreatorHelper.getNewUser();
                    AccountsManager.getInstance().getUsers().put(newFaculty.getId(), newFaculty);
                    printAccountCreationSuccess(newFaculty);
                    facultyOptions(newFaculty);
                    break;
                case 2:
                    UserCreatorHelper userCreatorHelper2 = new UserCreatorHelper(UserType.STUDENT);
                    Student newStudent = (Student) userCreatorHelper2.getNewUser();
                    AccountsManager.getInstance().getUsers().put(newStudent.getId(), newStudent);
                    printAccountCreationSuccess(newStudent);
                    studentOptions(newStudent);
                    break;
                case 3:
                    printlnWithAnim("Exiting now...");
                    userChoice = 4;
                    break;
                default:
                    println("Invalid Choice! Enter a valid choice...");
                    break;
            }
        } while (userChoice < 4);
    }

    public static void main(String args[])
    {
        scanner = new Scanner(System.in);
        console = System.console();
        AccountsManager accountsManager = AccountsManager.getInstance();
        if(accountsManager.isFirstTime())
        {
            accountsManager.setFirstTime(false);
        }
        //println(accountsManager.getUsers());
        //println(accountsManager.isFirstTime());
        printTextWithinStarPattern("Welcome to CollegeCoordinator !");
        if (accountsManager.getUsers().isEmpty() || accountsManager.noAdminAvailable())
        {
            UserCreatorHelper userCreatorHelper = new UserCreatorHelper(UserType.ADMIN);
            println("You have to create an Admin account to proceed further....");
            println();
            Admin admin = (Admin) userCreatorHelper.getNewUser();
            accountsManager.getUsers().put(admin.getId(), admin);
            printAccountCreationSuccess(admin);
            accountsManager.loginUser(admin.getId(), admin.getPassword());
            adminOptions(admin);
        }
        else
        {
            println("Welcome user, choose an option (1-3) to proceed...");
            int loginRegisterOptions = -1;
            do {
                println("1. Register as new User\n2. Login now\n3. Exit");
                loginRegisterOptions = readInt();
                readLine();
                switch (loginRegisterOptions) {
                    case 1:
                        registerPrompt();
                        loginRegisterOptions = 4;
                    case 2:
                        loginRegisterOptions = 4;
                    case 3:
                        printlnWithAnim("Exiting now...");
                        break;
                    case 4:
                        break;
                    default:
                        println("Invalid choice ! Enter a valid choice...");
                        break;
                }

            } while (loginRegisterOptions < 3);
        }

        //Persisting Data
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try
            {
                accountsManager.persistData();
                printlnWithAnim("Saving data before exiting...");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                println("Unable to store user data!!! Application might behave differently next time!");
            }
        }));
    }
}

