package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static com.techiness.collegecoordinator.driver.Main.println;
import static com.techiness.collegecoordinator.driver.Main.readLine;
import static com.techiness.collegecoordinator.driver.Main.readInt;
import static com.techiness.collegecoordinator.driver.Main.readPassword;

public class UserCreatorHelper
{
    private UserType userType;
    private String name = "", email = "", password = "", phone = "", qualString = "", subjectString = "", companyString = "";
    private Gender gender = null;
    private int age = -1, genderChoice = -1, experience = -1;
    private List<String> qualifications = null, subjectsHandled = null, companies = null;

    public UserCreatorHelper(UserType userType)
    {
        this.userType = userType;
    }

    public UserType getUserType()
    {
        return userType;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    private void resetVariables()
    {
        name = email = phone = password = qualString = subjectString = companyString = "";
        gender = null;
        qualifications = subjectsHandled = companies = null;
        age = genderChoice =  experience = -1;
    }

    private void getBasicDataOfUser()
    {
        resetVariables();
        while(!InputDataValidation.validateName(name))
        {
            println("Enter your name:");
            name = readLine();
            if(!InputDataValidation.validateName(name))
                println("Please enter your name to proceed...");
        }
        println();
        while(!InputDataValidation.validateAge(age))
        {
            println("Enter your age:");
            age = readInt();
            if(!InputDataValidation.validateAge(age))
                println("Invalid age ! Enter the actual age !");
        }
        println();
        while(gender==null)
        {
            println("Select your Gender (1-3): \n 1. MALE \n 2. FEMALE \n 3. OTHER");
            genderChoice = readInt();
            switch (genderChoice)
            {
                case 1:
                    gender = Gender.MALE;
                    break;
                case 2:
                    gender = Gender.FEMALE;
                    break;
                case 3:
                    gender = Gender.OTHER;
                    break;
                default:
                    gender = null;
                    println("Invalid Choice ! Enter the right choice...");
                    break;
            }
        }
        readLine();
        println();
        while(!InputDataValidation.validatePhone(phone))
        {
            println("Enter your phone number:");
            phone = readLine();
            if(!InputDataValidation.validatePhone(phone))
                println("Invalid Phone Number ! Enter phone number properly !");
        }
        println();
        while(!InputDataValidation.validateEmail(email))
        {
            println("Enter your email:");
            email = readLine();
            if(!InputDataValidation.validateEmail(email))
                println("Invalid Email ID ! Enter Email ID properly !");
        }
        println();
        while(!InputDataValidation.validatePassword(password))
        {
            println("Criteria for password : ");
            println("Password must contain at least one digit [0-9].\n" +
                    "Password must contain at least one lowercase character [a-z].\n" +
                    "Password must contain at least one uppercase character [A-Z].\n" +
                    "Password must contain at least one special character like ! @ # & ( ).\n" +
                    "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            println();
            println("Enter your desired password:");
            password = readPassword();
            if(!InputDataValidation.validatePassword(password))
                println("Invalid Password ! Try entering a different password matching the given criteria !");
        }
    }

    private void getFacultyDetails()
    {
        getBasicDataOfUser();
        println("Enter your qualifications separated by commas on a single line:");
        qualString = readLine();
        qualifications = new ArrayList<>(Arrays.asList(qualString.split(",")));
        println("Enter your experience in years:");
        experience = readInt();
        readLine();
    }

    private void getHoDDetails()
    {
        getFacultyDetails();
        println("Enter the subjects handled by you, separated by commas on a single line:");
        subjectString = readLine();
        subjectsHandled = new ArrayList<>(Arrays.asList(subjectString.split(",")));
    }

    private void getTrainingHeadDetails()
    {
        getHoDDetails();
        println("Enter the names of companies that are visiting for placement, separated by commas:");
        companyString = readLine();
        companies = new ArrayList<>(Arrays.asList(companyString.split(",")));
    }

    public User getNewUser()
    {
        switch (userType)
        {
            case ADMIN:
                getBasicDataOfUser();
                return new Admin(name, age, gender, phone, email, password, new HashMap<>());
            case HOD:
                getHoDDetails();
                return new HoD(name, age, gender, phone, email, password, subjectsHandled, qualifications,
                        experience,new HashMap<>(),"");
            case FACULTY:
                getFacultyDetails();
                return new Faculty(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,"");
            case TRAINING_HEAD:
                getTrainingHeadDetails();
                return new TrainingHead(name, age, gender, phone, email, password, subjectsHandled, qualifications,
                        experience,new HashMap<>(),companies,"");
            case STUDENT:
            getBasicDataOfUser();
                return new Student(name, age, gender, phone, email, password, new ArrayList<>(),"");
            default:
                return null;
        }
    }
}
