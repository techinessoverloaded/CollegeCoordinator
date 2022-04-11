package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.concrete.TrainingHead;
import com.techiness.collegecoordinator.factories.CompanyCreator;
import com.techiness.collegecoordinator.factories.OfferCreator;
import com.techiness.collegecoordinator.concrete.Company;
import com.techiness.collegecoordinator.utils.Menu;
import com.techiness.collegecoordinator.concrete.Offer;

import static com.techiness.collegecoordinator.utils.IOUtils.*;

public final class TrainingHeadUI extends HoDUI
{
    private TrainingHead trainingHead;
    private PlacementDepartment placementDepartment;
    public TrainingHeadUI(TrainingHead trainingHead)
    {
        super();
        this.trainingHead = trainingHead;
        this.placementDepartment = accountsManager.getPlacementDepartment();
        userMenu.removeOption(userMenu.indexOf("Logout"));
        userMenu.extendMenu(new Menu.MenuBuilder().setHeader("Training Head Menu")
                .addOption("Add a Company visiting the College for Placement")
                .addOption("Remove a Company visiting the College for Placement")
                .addOption("Display all the Companies visiting the College for Placement")
                .addOption("Add an Offer for a Student")
                .addOption("Remove an Offer of a Student")
                .addOption("Display all the Offers obtained by a Student")
                .addOption("Logout")
                .build());
    }

    private void executeGeneralTrainingHeadActions(int choice)
    {
        switch (choice)
        {
            //Add a Company visiting the College for Placement
            case 34:
                Company newCompany = CompanyCreator.getInstance().getCompany();
                printlnWithAnim("Adding new Company to Placement Department...");
                if(trainingHead.addCompany(newCompany))
                {
                    println("New company added to the Placement Department successfully !");
                    println2("Company Details:\n"+newCompany);
                }
                else
                {
                    println2("An error occurred ! New company could not be added to the Placement Department !");
                }
            break;
            //Remove a Company visiting the College for Placement
            case 35:
                String companyId = "";
                Menu companyIdMenu = new Menu.MenuBuilder().setHeader("Company ID Selection Menu")
                        .addMultipleOptions(getStringOfNameableMap(placementDepartment.getCompanies())
                                .replaceAll("\n",",").split(","))
                        .build();
                int selectedCompanyChoice = -1;
                while(selectedCompanyChoice == -1)
                {
                    selectedCompanyChoice = companyIdMenu.displayMenuAndGetChoice();
                    if(selectedCompanyChoice == -1)
                        println("Invalid Choice ! Enter a Valid Choice !");
                }
                String selectedOption =  companyIdMenu.getOptions(selectedCompanyChoice);
                companyId = selectedOption.substring(0, selectedOption.indexOf(" : "));
                printlnWithAnim("Removing the Company from Placement Department...");
                if(trainingHead.removeCompany(companyId))
                {
                    println("Company removed from the Placement Department successfully !");
                }
                else
                {
                    println2("An error occurred ! Company could not be removed from the Placement Department !");
                }
            break;
            //Display all the Companies visiting the College for Placement
            case 36:
                println("List of Companies visiting the College for Placement:");
                println2(getStringOfNameableMap(placementDepartment.getCompanies()));
            break;
            //Add an Offer for a Student
            case 37:
                Offer newOffer = OfferCreator.getInstance().getOffer();
                String currentStudentId = newOffer.getStudentId();
                printlnWithAnim("Adding New Offer to Student's offers...");
                if(trainingHead.addOffer(currentStudentId,newOffer))
                {
                    Student currentStudent = placementDepartment.getStudents(currentStudentId);
                    println2("New Offer added to the Student's offers successfully !");
                    if(!currentStudent.getIsPlaced())
                    {
                        trainingHead.setIsPlaced(currentStudentId, true);
                        println2("Student's Placement Status set as Placed now !");
                    }
                }
                else
                {
                    println2("Error occurred ! Failed to add New Offer to the Student's Offers !");
                }
            break;
            //Remove an Offer of a Student
            case 38:
                String studentId = "";
                String offerId;
                while(!placementDepartment.checkIfStudentIdValid(studentId))
                {
                    studentId = getUserInput(studentId, "Student ID of the Student");
                    if(!placementDepartment.checkIfStudentIdValid(studentId))
                    {
                        println("Invalid Student ID ! Enter a Valid Student ID !");
                    }
                }
                Student currentStudent = placementDepartment.getStudents(studentId);
                Menu offerIdMenu = new Menu.MenuBuilder().setHeader("Offer ID Selection")
                        .addMultipleOptions(getStringOfIdentifiableMap(currentStudent.getOffers()).replaceAll("\n",",").split(","))
                        .build();
                int selectedOfferChoice = -1;
                while(selectedOfferChoice == -1)
                {
                    selectedOfferChoice = offerIdMenu.displayMenuAndGetChoice();
                    if(selectedOfferChoice == -1)
                    {
                        println("Invalid Choice ! Enter a Valid Choice !");
                    }
                }
                offerId = offerIdMenu.getOptions(selectedOfferChoice);
                printlnWithAnim("Removing the Offer from the Student's Offers...");
                if(trainingHead.removeOffer(studentId, offerId))
                {
                    println2("Offer removed from the Student's Offers successfully !");
                    if(currentStudent.getOffers().size() == 0)
                    {
                        trainingHead.setIsPlaced(studentId, false);
                        println2("Student's Placement Status set as Unplaced now !");
                    }
                }
                else
                {
                    println2("An Error occurred ! Failed to remove the Student's Offer !");
                }
            break;
            //Display all the Offers obtained by a Student
            case 39:
                String studentId2 = "";
                while(!placementDepartment.checkIfStudentIdValid(studentId2))
                {
                    studentId2 = getUserInput(studentId2, "Student ID of the Student");
                    if(!placementDepartment.checkIfStudentIdValid(studentId2))
                    {
                        println("Invalid Student ID ! Enter a Valid Student ID !");
                    }
                }
                println2("Your offers are:");
                Student currentStudent2 = placementDepartment.getStudents(studentId2);
                for(Offer offer : currentStudent2.getOffers().values())
                {
                    println2(offer);
                }
            break;
        }
    }

    @Override
    public void displayUIAndExecuteActions()
    {
        int choice = -1;
        while(true)
        {
            choice = userMenu.displayMenuAndGetChoice();
            if(choice >= 1 && choice <= 7)
                executeGeneralUserActions(trainingHead, choice);
            else if(choice >= 8 && choice <= 25)
                executeGeneralFacultyActions(trainingHead, choice);
            else if(choice >= 26 && choice <= 33)
                executeGeneralHoDActions(trainingHead, choice);
            else if(choice >= 34 && choice <= userMenu.getOptions().size()-1)
                executeGeneralTrainingHeadActions(choice);
            else if(choice == userMenu.getOptions().size())
            {
                printlnWithAnim("Logging out...");
                sessionManager.logoutUser();
                return;
            }
            else
            {
                println2("Invalid choice ! Enter a valid choice...");
            }
        }
    }
}
