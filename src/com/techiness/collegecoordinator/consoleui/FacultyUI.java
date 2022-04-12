package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.AbstractUserUI;
import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.concrete.*;
import com.techiness.collegecoordinator.enums.Grade;
import com.techiness.collegecoordinator.enums.RequestLetterType;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.factories.RequestLetterFactory;
import com.techiness.collegecoordinator.utils.InputDataValidator;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.factories.UserFactory;
import com.techiness.collegecoordinator.concrete.TCRequestLetter;
import javafx.util.Pair;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import static com.techiness.collegecoordinator.utils.IOUtils.*;

public class FacultyUI extends AbstractUserUI
{
    private Faculty faculty;

    public FacultyUI(Faculty faculty)
    {
        super();
        this.faculty = faculty;
        prepareMenu();
    }

    protected FacultyUI()
    {
        super();
        prepareMenu();
    }

    private void prepareMenu()
    {
        this.userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Faculty Menu")
                .addOption("Display the Subjects to be handled")
                .addOption("Add Qualification(s)")
                .addOption("Remove Qualification(s)")
                .addOption("Display my Qualifications")
                .addOption("Set my experience")
                .addOption("Display my experience")
                .addOption("Add a Student to the Department")
                .addOption("Remove a Student from the Department")
                .addOption("Display the Students under the Department")
                .addOption("Set/Change Grade for a Student")
                .addOption("Set/Change if a Student needs Placement Training or not")
                .addOption("Request Leave to HoD")
                .addOption("Request On Duty to HoD")
                .addOption("Request Promotion to Admin")
                .addOption("Request Resignation to Admin")
                .addOption("Check if Request Letter got Approved or not")
                .addOption("Logout")
                .build());
    }

    protected final void executeGeneralFacultyActions(Faculty faculty , int selection)
    {
        switch (selection)
        {
            //Display the Subjects to be handled
            case 8:
                println2("Subjects Handled by you:");
                println2(faculty.getSubjectsHandled());
                break;

            //Add Qualification(s)
            case 9:
                if (Qualification.getEnumSetDifference(faculty.getQualifications()) == 0) {
                    println2("You already have all the available Qualifications ! Cannot add more !");
                    break;
                }
                Menu qualificationMenu = new Menu.MenuBuilder().setHeader("Add Qualification(s) Menu")
                        .addMultipleOptions(Qualification.getStringArrayOfSetDifferenceValues(faculty.getQualifications()))
                        .addOption("Stop adding Qualifications")
                        .build();
                EnumSet<Qualification> newQualifications = EnumSet.noneOf(Qualification.class);
                int selectedQualificationChoice = -1;
                println2("Keep selecting Degrees one by one to add to the existing Qualifications...");
                while (selectedQualificationChoice < qualificationMenu.getOptions().size() + 1) {
                    selectedQualificationChoice = qualificationMenu.displayMenuAndGetChoice();

                    if (selectedQualificationChoice == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if (selectedQualificationChoice >= 1 && selectedQualificationChoice <= qualificationMenu.getOptions().size() - 1) {
                        newQualifications.add(Qualification.valueOf(qualificationMenu.getOptions(selectedQualificationChoice)));
                        qualificationMenu.removeOption(selectedQualificationChoice);
                    } else if (selectedQualificationChoice == qualificationMenu.getOptions().size()) {
                        if (newQualifications.size() == 0) {
                            println("Must add at least one Qualification !");
                        } else
                            break;
                    }
                }
                printlnWithAnim("Adding new Qualification(s) to existing Qualifications...");
                faculty.getQualifications().addAll(newQualifications);
                println2("New Qualification(s) added to existing Qualifications successfully !");
                break;
            //Remove a Qualification
            case 10:
                Menu qualificationMenu2 = new Menu.MenuBuilder().setHeader("Remove Qualification(s) Menu")
                        .addMultipleOptions(Qualification.getStringArrayOfValues(faculty.getQualifications().toArray(new Qualification[0])))
                        .addOption("Stop removing Qualifications")
                        .build();
                if (qualificationMenu2.getOptions().size() == 2) {
                    println2("You need at least 1 Qualification ! Cannot remove Qualifications anymore !");
                    break;
                }
                EnumSet<Qualification> newQualifications2 = EnumSet.noneOf(Qualification.class);
                int selectedQualificationChoice2 = -1;
                println2("Keep selecting Degrees one by one to remove from the existing Qualifications...");
                while (selectedQualificationChoice2 < qualificationMenu2.getOptions().size() + 1) {
                    if (qualificationMenu2.getOptions().size() == 2) {
                        println2("You need at least 1 Qualification ! Cannot remove Qualifications anymore !");
                        break;
                    }

                    selectedQualificationChoice2 = qualificationMenu2.displayMenuAndGetChoice();

                    if (selectedQualificationChoice2 == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if (selectedQualificationChoice2 >= 1 && selectedQualificationChoice2 <= qualificationMenu2.getOptions().size() - 1) {
                        newQualifications2.add(Qualification.valueOf(qualificationMenu2.getOptions(selectedQualificationChoice2)));
                        qualificationMenu2.removeOption(selectedQualificationChoice2);
                    } else if (selectedQualificationChoice2 == qualificationMenu2.getOptions().size()) {
                        if (newQualifications2.size() == 0) {
                            println("Must remove at least one Qualification !");
                        } else
                            break;
                    }
                }
                printlnWithAnim("Removing the selected Qualification(s) from the existing Qualifications...");
                faculty.getQualifications().removeAll(newQualifications2);
                println2("Selected Qualification(s) removed from the existing Qualifications successfully !");
                break;
            //Display my Qualifications
            case 11:
                println2("Your Qualifications are :");
                println2(faculty.getQualifications());
                break;
            //Set my experience
            case 12:
                int newExperience = -1;
                while (!InputDataValidator.validateExperience(newExperience, faculty.getAge(), faculty.getExperience()))
                {
                    newExperience = getUserInput(newExperience, "New Experience in years");
                    if (!InputDataValidator.validateExperience(newExperience, faculty.getAge(), faculty.getExperience()))
                        println("Enter a valid Experience in years !");
                }
                printlnWithAnim("Setting new Experience...");
                faculty.setExperience(newExperience);
                println2("New Experience set successfully !");
                break;
            //Display my experience
            case 13:
                println("Your experience (in years) is : " + faculty.getExperience() + " years");
                break;
            //Add a Student to the Department
            case 14:
                Student student = (Student) UserFactory.getInstance().getNewUser(UserType.STUDENT,null,null);
                printlnWithAnim("Creating new Student Account...");
                if (faculty.addStudent(student)) {
                    printAccountDetails(student, true);
                    println2("New Student with ID: " + student.getId() + " created and added to the Department successfully !");
                } else {
                    println2("Some error occurred ! Either the Student exists already or the New Student Account couldn't be created !");
                }
                break;
            //Remove a Student from the Department
            case 15:
                String studentId = "";
                Department currentDepartment = accountsManager.getDepartments(faculty.getDeptId());
                Admin admin7 = accountsManager.getAdmin();
                while (!currentDepartment.checkIfStudentIdValid(studentId))
                {
                    studentId = getUserInput(studentId, "Student ID of the Student to be removed from the Department");
                    if (!currentDepartment.checkIfStudentIdValid(studentId))
                        println("Invalid Student ID ! Enter a Valid Student ID !");
                }
                TCRequestLetter tcRequestLetter = (TCRequestLetter) RequestLetterFactory.getInstance().getLetter(studentId, admin7.getId(), faculty.getDeptId(), RequestLetterType.TC);
                printlnWithAnim("Submitting TC Request Letter for Student to Admin...");
                if(admin7.addLetter(tcRequestLetter))
                {
                    println2("Submitted TC Request Letter for Student to Admin successfully !");
                    println2("Letter Details :\n"+ tcRequestLetter);
                }
                else
                {
                    println2("Some error occurred ! Unable to request TC for Student to the Admin !");
                }
                break;
            //Display the Students under the Department
            case 16:
                println2("List of Students Under the Department");
                println2(getStringOfNameableMap(accountsManager.getDepartments(faculty.getDeptId()).getStudents()));
                break;
            //Set/Change Grade for a Student
            case 17:
                String studentId2 = "";
                Department currentDepartment2 = accountsManager.getDepartments(faculty.getDeptId());
                while (!currentDepartment2.checkIfStudentIdValid(studentId2))
                {
                    studentId2 = getUserInput(studentId2, "Student ID of the Student whose grades have to be set");
                    if (!currentDepartment2.checkIfStudentIdValid(studentId2))
                    {
                        println("Invalid Student ID ! Enter a Valid Student ID !");
                    }
                }
                Map<String, Grade> newGrades = new HashMap<>();
                println2("Existing Grades of the Student with Student ID : " + studentId2);
                println2(currentDepartment2.getStudents(studentId2).getGrades().toString());
                Menu subjectMenu = new Menu.MenuBuilder().setHeader("Subject Menu")
                        .addMultipleOptions(getStringArrayOfStringSet(((CourseDepartment) currentDepartment2).getCourseSubjects()))
                        .addOption("Stop selecting Subjects")
                        .build();
                Menu gradeMenu = new Menu.MenuBuilder().setHeader("Grade Menu")
                        .addMultipleOptions(Grade.getStringArrayOfValues())
                        .build();
                int selectedSubjectChoice = -1;
                Grade currentGrade = null;
                println2("Select the Subject to which you want to Set/Change the Grade...");
                while (selectedSubjectChoice < subjectMenu.getOptions().size() + 1) {
                    selectedSubjectChoice = subjectMenu.displayMenuAndGetChoice();

                    if (selectedSubjectChoice == -1)
                        println("Invalid Choice ! Enter a Valid Choice...");

                    else if (selectedSubjectChoice >= 1 && selectedSubjectChoice <= subjectMenu.getOptions().size()) {
                        while (currentGrade == null) {
                            currentGrade = Grade.valueOf(gradeMenu.getOptions(gradeMenu.displayMenuAndGetChoice()));
                            if (currentGrade == null)
                                println("Enter a valid choice !");
                        }
                        newGrades.put(subjectMenu.getOptions(selectedSubjectChoice), currentGrade);
                        subjectMenu.removeOption(selectedSubjectChoice);
                    }
                }
                if (newGrades.size() > 0) {
                    printlnWithAnim("Updating Grades for the Student...");
                    if (newGrades.size() == ((CourseDepartment) currentDepartment2).getCourseSubjects().size())
                        faculty.setGrade(studentId2, newGrades);
                    else {
                        String finalStudentId = studentId2;
                        newGrades.forEach((k, v) -> {
                            faculty.setGrade(finalStudentId, new Pair<>(k, v));
                        });
                    }
                    println2("Grades updated for Student successfully !");
                }
                break;
            //Set/Change if a Student needs Placement Training or not
            case 18:
                String studentId3 = "";
                Department currentDepartment3 = accountsManager.getDepartments(faculty.getDeptId());
                while (!currentDepartment3.checkIfStudentIdValid(studentId3)) {
                    studentId2 = getUserInput(studentId3, "Student ID of the Student Training necessity is to be set");
                    if (!currentDepartment3.checkIfStudentIdValid(studentId3)) {
                        println("Invalid Student ID ! Enter a Valid Student ID !");
                    }
                }
                Boolean needsTraining = null;
                Student currentStudent = currentDepartment3.getStudents(studentId3);
                if (currentStudent.isNeedsTraining()) {
                    int removeFromTrainingChoice = -1;
                    println2("Student is already there in Placement Training. Do you want to Remove the Student from the Training ?");
                    Menu removeFromTrainingMenu = Menu.getYesOrNoMenu();
                    while (removeFromTrainingChoice == -1) {
                        removeFromTrainingChoice = removeFromTrainingMenu.displayMenuAndGetChoice();
                        if (removeFromTrainingChoice == -1) {
                            println("Invalid Choice ! Enter a valid choice !");
                            continue;
                        }
                        needsTraining = removeFromTrainingChoice == 1 ? Boolean.FALSE : Boolean.TRUE;
                    }
                } else {
                    int addToTrainingChoice = -1;
                    println2("Student is not there in Placement Training. Do you want to Add the Student to the Training ?");
                    Menu addToTrainingMenu = Menu.getYesOrNoMenu();
                    while (addToTrainingChoice == -1) {
                        addToTrainingChoice = addToTrainingMenu.displayMenuAndGetChoice();
                        if (addToTrainingChoice == -1) {
                            println("Invalid Choice ! Enter a valid choice !");
                            continue;
                        }
                        needsTraining = addToTrainingChoice == 1 ? Boolean.TRUE : Boolean.FALSE;
                    }
                }
                printlnWithAnim("Updating Student's Training Necessity...");
                faculty.setNeedsTraining(studentId3, needsTraining);
                println2("Updated Student's Training Necessity successfully !");
                break;
            // Request Leave to HoD
            case 19:
                if(faculty instanceof HoD)
                {
                    Admin admin5 = accountsManager.getAdmin();
                    RequestLetter leaveRequestLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), admin5.getId(), faculty.getDeptId(), RequestLetterType.LEAVE);
                    printlnWithAnim("Submitting Leave Request Letter to  Admin...");
                    if(admin5.addLetter(leaveRequestLetter))
                    {
                        println2("Submitted Leave Request Letter to Admin. You can check the status of Approval after the Admin checks it.");
                        println2("Letter Details:\n"+leaveRequestLetter);
                    }
                    else
                    {
                        println2("An Error Occurred ! Failed to Submit Leave Request Letter to Admin !");
                    }
                }
                else
                {
                    Department currentDepartment4 = accountsManager.getDepartments(faculty.getDeptId());
                    HoD hod = currentDepartment4.getHod();
                    RequestLetter leaveRequestLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), hod.getId(), faculty.getDeptId(), RequestLetterType.LEAVE);
                    printlnWithAnim("Submitting Leave Request Letter to  HoD...");
                    if(hod.addLetter(leaveRequestLetter))
                    {
                        println2("Submitted Leave Request Letter to HoD. You can check the status of Approval after the HoD checks it.");
                        println2("Letter Details:\n" + leaveRequestLetter);
                    }
                    else
                    {
                        println2("An Error Occurred ! Failed to Submit Leave Request Letter to HoD !");
                    }
                }
                break;

            // Request On Duty to HoD
            case 20:
                if(faculty instanceof HoD)
                {
                    Admin admin6 = accountsManager.getAdmin();
                    RequestLetter odRequestLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), admin6.getId(), faculty.getDeptId(), RequestLetterType.ON_DUTY);
                    printlnWithAnim("Submitting On Duty Request Letter to  Admin...");
                    if(admin6.addLetter(odRequestLetter))
                    {
                        println2("Submitted On Duty Request Letter to Admin. You can check the status of Approval after the Admin checks it.");
                        println2("Letter Details:\n"+odRequestLetter);
                    }
                    else
                        println2("An Error Occurred ! Failed to submit On Duty Request Letter to Admin");
                }
                else
                {
                    Department currentDepartment5 = accountsManager.getDepartments(faculty.getDeptId());
                    HoD hod2 = currentDepartment5.getHod();
                    RequestLetter odRequestLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), hod2.getId(), faculty.getDeptId(), RequestLetterType.ON_DUTY);
                    printlnWithAnim("Submitting On Duty Request Letter to  HoD...");
                    if(hod2.addLetter(odRequestLetter))
                    {
                        println2("Submitted On Duty RequestLetter to HoD. You can check the status of Approval after the HoD checks it.");
                        println2("Letter Details:\n" + odRequestLetter);
                    }
                    else
                        println2("An Error Occurred ! Failed to Submit On Duty Request Letter to HoD !");
                }
                break;
            // Request Promotion to Admin
            case 21:
                Admin admin1 = accountsManager.getAdmin();
                RequestLetter promotionLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), admin1.getId(), faculty.getDeptId(), RequestLetterType.PROMOTION);
                printlnWithAnim("Submitting Promotion Request Letter to Admin...");
                if(admin1.addLetter(promotionLetter))
                {
                    println2("Submitted Promotion Request Letter to Admin. You can check the status of Approval after the Admin checks it.");
                    println2("Letter Details:\n" + promotionLetter);
                }
                else
                {
                    println2("An Error Occurred ! Failed to Submit Promotion Request Letter to Admin !");
                }
                break;
            //Request Resignation to Admin
            case 22:
                Admin admin3 = accountsManager.getAdmin();
                RequestLetter resignationLetter = RequestLetterFactory.getInstance().getLetter(faculty.getId(), admin3.getId(), faculty.getDeptId(), RequestLetterType.RESIGNATION);
                printlnWithAnim("Submitting Resignation Request Letter to Admin...");
                if(admin3.addLetter(resignationLetter))
                {
                    println("Submitted Resignation Request Letter to Admin. You can check the status of Approval after the Admin checks it.");
                    println2("Letter Details:\n" + resignationLetter);
                }
                else
                {
                    println2("An Error Occurred ! Failed to Submit Resignation Request Letter to Admin !");
                }
                break;
                // Check if Request RequestLetter got Approved or not
            case 23:
                Menu checkLetterTypeMenu = new Menu.MenuBuilder().setHeader("Type of Letter to be checked")
                        .addMultipleOptions(RequestLetterType.getStringArrayOfValues())
                        .build();
                int checkLetterChoice = -1;
                RequestLetterType enteredRequestLetterType;
                while(checkLetterChoice == -1)
                {
                    checkLetterChoice = checkLetterTypeMenu.displayMenuAndGetChoice();
                    if(checkLetterChoice == -1)
                        println2("Invalid choice ! Enter a valid choice...");
                }
                enteredRequestLetterType = RequestLetterType.valueOf(checkLetterTypeMenu.getOptions(checkLetterChoice));
                Department currentDepartment6 = accountsManager.getDepartments(faculty.getDeptId());
                HoD hod3 = currentDepartment6.getHod();
                Admin admin4 = accountsManager.getAdmin();
                String letterId = "";
                while(letterId.equals(""))
                {
                    letterId = getUserInput(letterId, "Letter ID of the Letter");
                    if(letterId.equals(""))
                    {
                        println("Enter a valid letter ID...");
                    }
                }
                switch (enteredRequestLetterType)
                {
                    case LEAVE:
                        if(hod3.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(hod3.checkIfLetterApproved(letterId))
                            println2("Your Leave Request Letter got approved by the HoD !");
                        else
                            println2("Your Leave Request Letter was not approved by the HoD ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;

                    case ON_DUTY:
                        if(hod3.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(hod3.checkIfLetterApproved(letterId))
                            println2("Your OD Request Letter got approved by the HoD !");
                        else
                            println2("Your OD Request Letter was not approved by the HoD ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;

                    case PROMOTION:
                        if(admin4.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(admin4.checkIfLetterApproved(letterId))
                            println2("Your Promotion Request Letter got approved by the Admin ! Your Request will be taken care of soon !");
                        else
                            println2("Your Promotion Request Letter was not approved by the Admin ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;

                    case RESIGNATION:
                        if(admin4.getLetters(letterId)==null)
                        {
                            println2("No such Letter ID exists !");
                            break;
                        }
                        if(admin4.checkIfLetterApproved(letterId))
                            println2("Your Resignation Request Letter got approved by the Admin ! Your Request will be taken care of soon !");
                        else
                            println2("Your Resignation Letter was not approved by the Admin ! You can try applying again ! The current letter will be deleted after the Application closes !");
                        break;
                }
                break;
        }
    }

    private void executeGeneralFacultyActions(int selection)
    {
        executeGeneralFacultyActions(faculty, selection);
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if(choice >= 1 && choice <= 7)
            {
                executeGeneralUserActions(faculty, choice);
            }

            else if(choice >= 8 && choice <= userMenu.getOptions().size()-1)
            {
                executeGeneralFacultyActions(choice);
            }

            else if(choice == userMenu.getOptions().size())
            {
                printlnWithAnim("Logging out...");
                sessionManager.logoutUser();
                return;
            }
            else
            {
                println("Invalid Choice ! Enter a valid choice...");
            }
        }
    }
}
