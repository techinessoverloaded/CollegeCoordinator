package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.*;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class Main
{
    public static Department createDepartment()
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

    public static void executeGeneralUserActions(User user, int selection)
    {
        switch (selection)
        {
            case 1:
                String newName = "";
                while(!InputDataValidator.validateName(newName))
                {
                    println("Enter the new name:");
                    newName = readLine();
                    if(!InputDataValidator.validateName(newName))
                        println("Please enter the new name to proceed...");
                }
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

            case 2:
                int age = -1;
                while(!InputDataValidator.validateAge(age))
                {
                    println("Enter the new age:");
                    age = readInt();
                    readLine();
                    if(!InputDataValidator.validateAge(age))
                        println("Warning : Age should be between 18 and 100 ! Please enter the new age to proceed...");
                }
                println("Enter your password to confirm age change:");
                String password1 = readPassword();
                if(password1.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing age...");
                    user.setAge(age);
                    println("Account details after changing age\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Age Change Request failed!!!");
                }
                break;

            case 3:
                Gender newGender = null;
                int genderChoice = -1;
                Menu.MenuBuilder genderMenuBuilder = new Menu.MenuBuilder();
                Menu genderMenu = genderMenuBuilder.setHeader("Gender Selection")
                        .addOption("Male").addOption("Female").addOption("Other").build();
                while(newGender ==null)
                {
                    println("Enter the new new Gender:");
                    genderChoice = genderMenu.displayMenuAndGetChoice();
                    readLine();
                    switch (genderChoice)
                    {
                        case 1:
                            newGender = Gender.MALE;
                            break;
                        case 2:
                            newGender = Gender.FEMALE;
                            break;
                        case 3:
                            newGender = Gender.OTHER;
                            break;
                        default:
                            newGender = null;
                            println("Invalid Choice ! Enter the right choice...");
                            break;
                    }
                }
                println("Enter your password to confirm newGender change:");
                String password2 = readPassword();
                if(password2.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing name...");
                    user.setGender(newGender);
                    println("Account details after changing newGender\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Gender Change Request failed!!!");
                }
                break;
            case 4:
                String newPhone = "";
                while(!InputDataValidator.validatePhone(newPhone))
                {
                    println("Enter the new phone number:");
                    newPhone = readLine();
                    if(!InputDataValidator.validatePhone(newPhone))
                        println("Please enter a valid phone number to proceed...");
                }
                println("Enter your password to confirm phone number change:");
                String password3 = readPassword();
                if(password3.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing phone number...");
                    user.setPhone(newPhone);
                    println("Account details after changing phone\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Phone Number Change Request failed!!!");
                }
                break;
            case 5:
                String newEmail = "";
                while(!InputDataValidator.validateEmail(newEmail))
                {
                    println("Enter the new email ID:");
                    newEmail = readLine();
                    if(!InputDataValidator.validateEmail(newEmail))
                        println("Please enter a valid email ID to proceed...");
                }
                println("Enter your password to confirm email ID change:");
                String password4 = readPassword();
                if(password4.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing email ID...");
                    user.setEmail(newEmail);
                    println("Account details after changing email ID\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Email ID Change Request failed!!!");
                }
                break;
            case 6:
                String newPassword = "";
                while(!InputDataValidator.validatePassword(newPassword))
                {
                    println("Enter the new password:");
                    newPassword = readPassword();
                    if(!InputDataValidator.validatePassword(newPassword))
                        println("Please enter a valid password to proceed...");
                }
                println("Enter your CURRENT password to confirm password change:");
                String password5 = readPassword();
                if(password5.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing password...");
                    user.setPhone(newPassword);
                    println("Account details after changing password\n\n"+ AccountsManager.getInstance().getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Password Change Request failed!!!");
                }
                break;
        }
    }

    public static void adminOptions(Admin admin)
    {
        Menu.MenuBuilder adminMenuBuilder = new Menu.MenuBuilder(getGeneralUserMenu());
        Menu adminMenu = adminMenuBuilder.setHeader("Admin Menu")
                .addOption("Add a department")
                .addOption("Assign a HoD for a department")
                .addOption("Change HoD for a department")
                .addOption("Remove a department")
                .addOption("Get existing departments")
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
                    Department newDepartment = createDepartment();
                    if (newDepartment != null)
                    {
                        admin.addDepartment(newDepartment);
                        printDepartmentCreationSuccess(newDepartment);
                        if (newDepartment.getHod() == null)
                        {
                            println("You have to assign HoD to the newly created department now !");
                            println("Create an account for the HoD and note down the credentials for the HoD to use later !");
                            HoD hod = (HoD) new UserCreationHelper(UserType.HOD).getNewUser();
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
                    //Department department = admin.getDepartments().get(deptID);
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
                    UserCreationHelper userCreatorHelper = new UserCreationHelper(UserType.FACULTY);
                    Faculty newFaculty = (Faculty) userCreatorHelper.getNewUser();
                    AccountsManager.getInstance().getUsers().put(newFaculty.getId(), newFaculty);
                    printAccountCreationSuccess(newFaculty);
                    facultyOptions(newFaculty);
                    break;
                case 2:
                    UserCreationHelper userCreatorHelper2 = new UserCreationHelper(UserType.STUDENT);
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
        AccountsManager accountsManager = AccountsManager.getInstance();
        if(accountsManager.isFirstTime())
        {
            accountsManager.setFirstTime(false);
        }
        //println(accountsManager.getUsers());
        //println(accountsManager.isFirstTime());
        printTextWithinStarPattern("Welcome to CollegeCoordinator !");
        if (accountsManager.noAdminAvailable())
        {
            UserCreationHelper userCreatorHelper = new UserCreationHelper(UserType.ADMIN);
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
                println("\n1. Login as Admin \n2.. Exit");
                loginRegisterOptions = readInt();
                readLine();
                switch (loginRegisterOptions)
                {
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

