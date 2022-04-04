package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.InputDataValidator;
import com.techiness.collegecoordinator.helpers.Menu;

import java.util.*;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

// Factory Pattern
public class UserFactory
{
    private String name = "", email = "", password = "", phone = "", deptId = "";
    private Gender gender = null;
    private int age = -1, genderChoice = -1, experience = -1;
    private EnumSet<Qualification> qualifications = null;
    private Set<String> subjectsHandled = null;
    private static UserFactory instance = null;

    private UserFactory()
    {

    }

    public synchronized static UserFactory getInstance()
    {
        if(instance == null)
            instance = new UserFactory();
        return instance;
    }

    private void resetVariables()
    {
        name = email = phone = password = deptId = "";
        gender = null;
        subjectsHandled = null;
        qualifications = null;
        age = genderChoice =  experience = -1;
    }

    private void getBasicDataOfUser(UserType userType)
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

    private void getFacultyDetails(UserType userType)
    {
        getBasicDataOfUser(userType);
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
        while(!AccountsManager.getInstance().checkIfDeptIdExists(deptId))
        {
            deptId = getUserInput(deptId, "Department ID of the Department where the faculty has to be added");
            if(!AccountsManager.getInstance().checkIfDeptIdExists(deptId))
                println("No such department exists ! Enter a valid department ID !");
        }
        CourseDepartment courseDepartment = (CourseDepartment) AccountsManager.getInstance().getDepartments(deptId);
        Menu subjectMenu = new Menu.MenuBuilder().setHeader("Add Subject(s) Menu")
                .addMultipleOptions(getStringArrayOfStringSet(courseDepartment.getCourseSubjects()))
                .addOption("Stop adding Subjects")
                .build();
        subjectsHandled = new HashSet<>();
        int selectedSubjectChoice = -1;
        println2("Keep selecting Subjects one by one to add to the "+ userType +" 's subjects...");
        while(selectedSubjectChoice < subjectMenu.getOptions().size()+1)
        {
            selectedSubjectChoice = subjectMenu.displayMenuAndGetChoice();

            if(selectedSubjectChoice == -1)
                println("Invalid Choice ! Enter a Valid Choice...");

            else if(selectedSubjectChoice >= 1 && selectedSubjectChoice <= subjectMenu.getOptions().size()-1)
            {
                subjectsHandled.add(subjectMenu.getOptions(selectedSubjectChoice));
                subjectMenu.removeOption(selectedSubjectChoice);
            }

            else if(selectedSubjectChoice == subjectMenu.getOptions().size())
            {
                if(subjectsHandled.size() == 0)
                {
                    println("Must add at least one Subject to be handled !");
                }
                else
                    break;
            }
        }
    }

    private void getHoDDetails(UserType userType)
    {
        getFacultyDetails(userType);
    }

    private void getTrainingHeadDetails(UserType userType)
    {
        getHoDDetails(userType);
    }

    public synchronized User getNewUser(UserType userType)
    {
        switch (userType)
        {
            case ADMIN:
                getBasicDataOfUser(userType);
                return new Admin(name, age, gender, phone, email, password, new HashMap<>());
            case HOD:
                getHoDDetails(userType);
                return new HoD(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,new HashMap<>(),deptId);
            case FACULTY:
                getFacultyDetails(userType);
                return new Faculty(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,deptId);
            case TRAINING_HEAD:
                getTrainingHeadDetails(userType);
                return new TrainingHead(name, age, gender, phone, email, password, new ArrayList<>(), qualifications,
                        experience,new HashMap<>(),deptId);
            case STUDENT:
                getBasicDataOfUser(userType);
                return new Student(name, age, gender, phone, email, password,new HashMap<>() ,new HashMap<>(),deptId);
            default:
                return null;
        }
    }
}
