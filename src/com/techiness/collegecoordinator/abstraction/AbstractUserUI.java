package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.managers.AccountsManager;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.utils.InputDataValidator;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.managers.SessionManager;
import static com.techiness.collegecoordinator.utils.IOUtils.*;
import static com.techiness.collegecoordinator.utils.IOUtils.println;

public abstract class AbstractUserUI implements UserInterface
{
    protected Menu userMenu;
    protected AccountsManager accountsManager;
    protected SessionManager sessionManager;

    public AbstractUserUI()
    {
       userMenu = new Menu.MenuBuilder().setHeader("User Menu")
                .addOption("Change name")
                .addOption("Change age")
                .addOption("Change gender")
                .addOption("Change phone number")
                .addOption("Change email ID")
                .addOption("Change password")
                .addOption("Display Account Details")
                .build();
        accountsManager = AccountsManager.getInstance();
        sessionManager = SessionManager.getInstance();
    }

    @Override
    public abstract void displayUIAndExecuteActions();

    protected final void executeGeneralUserActions(User user, int selection)
    {
        switch (selection)
        {
            case 1:
                String newName = "";
                while(!InputDataValidator.validateName(newName,user.getName()))
                {
                    newName = getUserInput(newName,"New Name");
                    if(!InputDataValidator.validateName(newName,user.getName()))
                    {
                        println("Please enter the new name to proceed...");
                    }
                }
                String password = getPasswordInput("Password to confirm Name Change");
                if(password.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing name...");
                    user.setName(newName);
                    println("Account details after changing name\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Name Change Request failed!!!");
                }
                break;

            case 2:
                int age = -1;
                while(!InputDataValidator.validateAge(age,user.getAge()))
                {
                    age = getUserInput(age,"New Age");
                    if(age == -1)
                        continue;
                    if(!InputDataValidator.validateAge(age,user.getAge()))
                    {
                        println("Warning : Age should be between 18 and 100 ! Please enter the new age to proceed...");
                        continue;
                    }
                }
                String password1 = getPasswordInput("Password to confirm Age Change");
                if(password1.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing age...");
                    user.setAge(age);
                    println("Account details after changing age\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Age Change Request failed!!!");
                }
                break;

            case 3:
                Gender newGender = null;
                int genderChoice = -1;
                Menu genderMenu = Menu.getGenderSelectionMenu();
                while(newGender == null)
                {
                    println("Enter the new new Gender:");
                    genderChoice = genderMenu.displayMenuAndGetChoice();
                    if(genderChoice == -1)
                        continue;
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
                if(newGender == user.getGender())
                {
                    println("You have entered the same gender again!");
                    break;
                }
                println("Enter your password to confirm Gender change:");
                String password2 = readPassword();
                if(password2.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing name...");
                    user.setGender(newGender);
                    println("Account details after changing Gender\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Gender Change Request failed!!!");
                }
                break;
            case 4:
                String newPhone = "";
                while(!InputDataValidator.validatePhone(newPhone,user.getPhone()))
                {
                    println("Enter the new phone number:");
                    newPhone = readLine();
                    if(!InputDataValidator.validatePhone(newPhone,user.getPhone()))
                        println("Please enter a valid phone number to proceed...");
                }
                println("Enter your password to confirm phone number change:");
                String password3 = readPassword();
                if(password3.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing phone number...");
                    user.setPhone(newPhone);
                    println("Account details after changing phone\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Phone Number Change Request failed!!!");
                }
                break;
            case 5:
                String newEmail = "";
                while(!InputDataValidator.validateEmail(newEmail,user.getEmail()))
                {
                    println("Enter the new email ID:");
                    newEmail = readLine();
                    if(!InputDataValidator.validateEmail(newEmail,user.getEmail()))
                        println("Please enter a valid email ID to proceed...");
                }
                println("Enter your password to confirm email ID change:");
                String password4 = readPassword();
                if(password4.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing email ID...");
                    user.setEmail(newEmail);
                    println("Account details after changing email ID\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Email ID Change Request failed!!!");
                }
                break;
            case 6:
                String newPassword = "";
                while(!InputDataValidator.validatePassword(newPassword,false,user.getPassword()))
                {
                    println("Enter the new password:");
                    newPassword = readPassword();
                    if(!InputDataValidator.validatePassword(newPassword,false,user.getPassword()))
                        println("Please enter a valid password to proceed...");
                }
                println("Enter your CURRENT password to confirm password change:");
                String password5 = readPassword();
                if(password5.equals(user.getPassword()))
                {
                    printlnWithAnim("Changing password...");
                    user.setPhone(newPassword);
                    println("Account details after changing password\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Password Change Request failed!!!");
                }
                break;
            case 7:
                printAccountDetails(user,false);
                break;
        }
    }
}
