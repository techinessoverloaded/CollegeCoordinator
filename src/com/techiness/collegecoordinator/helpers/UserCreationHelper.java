package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.enums.UserType;

import java.util.*;

import static com.techiness.collegecoordinator.helpers.IOUtils.*;

public class UserCreationHelper
{
    private UserType userType;
    private String name = "", email = "", password = "", phone = "", qualString = "", subjectString = "";
    private Gender gender = null;
    private int age = -1, genderChoice = -1, experience = -1;
    private EnumSet<Qualification> qualifications = null, subjectsHandled = null;

    public UserCreationHelper(UserType userType)
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
        name = email = phone = password = qualString = subjectString = "";
        gender = null;
        qualifications = subjectsHandled = null;
        age = genderChoice =  experience = -1;
    }

    private void getBasicDataOfUser()
    {
        resetVariables();
        while(!InputDataValidator.validateName(name))
        {
            name = getUserInput(name,"Name of the "+userType);
            if(!InputDataValidator.validateName(name))
                println("Please enter the Name of the "+userType+" to proceed...");
        }
        println();
        while(!InputDataValidator.validateAge(age))
        {
            age = getUserInput(age,"Age of the "+userType);
            if(age == -1)
                continue;
            if(!InputDataValidator.validateAge(age))
                println("Warning : Age should be between 18 and 100 ! Enter the actual age of "+userType+" to proceed...");
        }
        println();
        Menu.MenuBuilder genderMenuBuilder = new Menu.MenuBuilder();
        Menu genderMenu = genderMenuBuilder.setHeader("Gender Selection")
                .addOption("Male")
                .addOption("Female")
                .addOption("Other")
                .build();
        while(gender==null)
        {
            genderChoice = genderMenu.displayMenuAndGetChoice();
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
        println();
        while(!InputDataValidator.validatePhone(phone))
        {
            phone = getUserInput(phone,"Phone Number of the "+userType);
            if(!InputDataValidator.validatePhone(phone))
                println("Invalid Phone Number ! Enter valid phone number of "+userType+" to proceed...");
        }
        println();
        while(!InputDataValidator.validateEmail(email))
        {
            email = getUserInput(email, "Email ID of the "+userType);
            if(!InputDataValidator.validateEmail(email))
                println("Invalid Email ID ! Enter valid Email ID of "+userType+" to proceed...");
        }
        println();
        while(!InputDataValidator.validatePassword(password,false))
        {
            println("Criteria for password : ");
            println2("Password must contain at least one digit [0-9].\n" +
                    "Password must contain at least one lowercase character [a-z].\n" +
                    "Password must contain at least one uppercase character [A-Z].\n" +
                    "Password must contain at least one special character like ! @ # & ( ).\n" +
                    "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            password = getPasswordInput("desired Password for the "+userType);
            if(!InputDataValidator.validatePassword(password,false))
                println("Invalid Password ! Try entering a different password matching the given criteria for "+userType+"to proceed...");
        }
        println();
    }

    private void getFacultyDetails()
    {
        getBasicDataOfUser();
        Menu qualificationMenu = new Menu.MenuBuilder().setHeader("Add Qualification(s) Menu")
                .addMultipleOptions(Qualification.getStringArrayOfValues())
                .addOption("Stop adding Qualifications")
                .build();
        qualifications = EnumSet.noneOf(Qualification.class);
        int selectedQualificationChoice = -1;
        println2("Keep selecting Degrees one by one to add to the "+ userType +" 's qualifications...");
        while(selectedQualificationChoice < qualificationMenu.getOptions().size()+1)
        {
            selectedQualificationChoice = qualificationMenu.displayMenuAndGetChoice();

            if(selectedQualificationChoice == -1)
                println("Invalid Choice ! Enter a Valid Choice...");

            else if(selectedQualificationChoice >= 1 && selectedQualificationChoice <= qualificationMenu.getOptions().size()-1)
            {
                qualifications.add(Qualification.valueOf(qualificationMenu.getOptions(selectedQualificationChoice)));
                qualificationMenu.removeOption(selectedQualificationChoice);
            }

            else if(selectedQualificationChoice == qualificationMenu.getOptions().size())
            {
                if(qualifications.size() == 0)
                {
                    println("Must add at least one Qualification !");
                }
                else
                    break;
            }
        }
        experience = getUserInput(experience,"Experience of the "+ userType +" in years");
    }

    private void getHoDDetails()
    {
        getFacultyDetails();
        println("Enter the Subjects handled by the "+userType+", separated by commas on a single line :");
        subjectString = readLine();
        subjectsHandled = EnumSet.allOf(Qualification.class);
    }

    private void getTrainingHeadDetails()
    {
        getHoDDetails();
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
                return new HoD(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,new HashMap<>(),"");
            case FACULTY:
                getFacultyDetails();
                return new Faculty(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,"");
            case TRAINING_HEAD:
                getTrainingHeadDetails();
                return new TrainingHead(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,new HashMap<>(),"");
            case STUDENT:
            getBasicDataOfUser();
                return new Student(name, age, gender, phone, email, password,new HashMap<>() ,new HashMap<>(),"");
            default:
                return null;
        }
    }
}
