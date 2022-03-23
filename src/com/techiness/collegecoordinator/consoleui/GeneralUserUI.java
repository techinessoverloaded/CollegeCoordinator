package com.techiness.collegecoordinator.consoleui;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.AccountsManager;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.helpers.InputDataValidator;
import com.techiness.collegecoordinator.helpers.Menu;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;
import static com.techiness.collegecoordinator.consoleui.IOUtils.println;

public class GeneralUserUI
{
    protected Menu userMenu;

    public GeneralUserUI()
    {
        this.userMenu = new Menu.MenuBuilder()
                .addOption("Change name")
                .addOption("Change age")
                .addOption("Change gender")
                .addOption("Change phone number")
                .addOption("Change email ID")
                .addOption("Change password")
                .build();
    }

    public Menu getUserMenu()
    {
        return userMenu;
    }

    protected void executeGeneralUserActions(User user, int selection)
    {
        AccountsManager accountsManager = AccountsManager.getInstance();
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
                    println("Account details after changing name\n\n"+ accountsManager.getUsers().get(user.getId()));
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
                Menu.MenuBuilder genderMenuBuilder = new Menu.MenuBuilder();
                Menu genderMenu = genderMenuBuilder.setHeader("Gender Selection")
                        .addOption("Male").addOption("Female").addOption("Other").build();
                while(newGender == null)
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
                    println("Account details after changing newGender\n\n"+ accountsManager.getUsers().get(user.getId()));
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
                    println("Account details after changing phone\n\n"+ accountsManager.getUsers().get(user.getId()));
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
                    println("Account details after changing email ID\n\n"+ accountsManager.getUsers().get(user.getId()));
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
                    println("Account details after changing password\n\n"+ accountsManager.getUsers().get(user.getId()));
                }
                else
                {
                    println("Wrong Password ! Password Change Request failed!!!");
                }
                break;
        }
    }
}
