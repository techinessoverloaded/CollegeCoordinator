package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.enums.RequestLetterType;
import com.techiness.collegecoordinator.helpers.*;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import java.time.LocalDate;
import java.util.*;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

//Factory Pattern
public class RequestLetterFactory
{
    private static RequestLetterFactory instance = null;
    private String reasonForRequest = "";
    private String destinationDeptId = "";
    private String currentDeptId = "";
    private String[] odRequestedDates = new String[0];
    private String[] leaveRequestedDates = new String[0];
    private String requestedQuitDate = null;

    private RequestLetterFactory()
    {

    }

    public synchronized static RequestLetterFactory getInstance()
    {
        if(instance == null)
            instance = new RequestLetterFactory();
        return instance;
    }

    private void resetVariables()
    {
        reasonForRequest = "";
        destinationDeptId = "";
        currentDeptId = "";
        odRequestedDates = new String[0];
        leaveRequestedDates = new String[0];
        requestedQuitDate = null;
    }

    private void getReason(RequestLetterType requestLetterType)
    {
        AccountsManager accountsManager = AccountsManager.getInstance();
        while (!InputDataValidator.validateReason(reasonForRequest))
        {
            reasonForRequest = getUserInput(reasonForRequest,"Reason for applying "+ requestLetterType);
            if(!InputDataValidator.validateReason(reasonForRequest))
                println2("Invalid Input ! Enter the reason properly !");
        }
    }

    private void getContinuousOrDiscreteDates(String[] datesArrayRef, String leaveOrOd, boolean isContinuous)
    {
        if(!isContinuous)
        {
            while(datesArrayRef.length == 0 || !Arrays.stream(datesArrayRef).allMatch(InputDataValidator::validateDateString))
            {
                datesArrayRef = getUserInput(datesArrayRef, "Enter the dates when you want to avail "+leaveOrOd+", separated by commas if more than 1 in the format dd/mm/yyyy");
                if(datesArrayRef.length == 0)
                {
                    println2("Enter valid input...");

                }
                else if(!Arrays.stream(datesArrayRef).allMatch(InputDataValidator::validateDateString))
                {
                    println2("Invalid dates ! Make sure that you apply " + leaveOrOd +" at least 1 day before the actual date ! Don't apply "+leaveOrOd+" for Sunday ! Enter the dates in dd/mm/yyyy format again !");
                }
            }
            Arrays.sort(datesArrayRef);
        }
        else
        {
            String startDate = "";
            int numberOfDays = -1;
            while(!InputDataValidator.validateDateString(startDate))
            {
                startDate = getUserInput(startDate,"Starting Date from when you want to take "+leaveOrOd);
                if(!InputDataValidator.validateDateString(startDate))
                {
                    println2("Invalid dates ! Make sure that you apply " + leaveOrOd +" at least 1 day before the actual date ! Don't apply "+leaveOrOd+" for Sunday ! Enter the dates in dd/mm/yyyy format again !");
                }
            }
            while(numberOfDays == -1)
            {
                numberOfDays = getUserInput(numberOfDays,"the Number of days you want to take "+leaveOrOd+" (excluding starting date, enter 1 if only starting date is needed)");
                if(numberOfDays <= 0)
                {
                    println2("Number of days should be a positive number starting from 1 ! Enter a valid input...");
                }
            }
            LocalDate startLocalDate = LocalDate.parse(startDate,getDateFormatter());
            List<String> tempListOfDates = new ArrayList<>();
            tempListOfDates.add(startDate);
            while(numberOfDays > 0)
            {
                tempListOfDates.add(startLocalDate.plusDays(numberOfDays--).format(getDateFormatter()));
            }
            tempListOfDates.sort(String::compareTo);
            datesArrayRef = tempListOfDates.toArray(new String[0]);
        }
    }

    private void getLeaveLetterDetails()
    {
        resetVariables();
        Menu continuousDiscreteMenu = new Menu.MenuBuilder().setHeader("Select Continuous/Discrete Leave")
                .addOption("Continuous Range of Leave Dates")
                .addOption("Discrete Leave Dates")
                .build();
        int dateTypeChoice = -1;
        while(dateTypeChoice == -1)
        {
            dateTypeChoice = continuousDiscreteMenu.displayMenuAndGetChoice();
            if(dateTypeChoice == -1)
                println2("Invalid Choice ! Enter a valid choice...");
        }
        getContinuousOrDiscreteDates(leaveRequestedDates, "Leave",dateTypeChoice == 1);
        getReason(RequestLetterType.LEAVE);
    }

    private void getOdLetterDetails()
    {
        resetVariables();
        Menu continuousDiscreteMenu = new Menu.MenuBuilder().setHeader("Select Continuous/Discrete On Duty")
                .addOption("Continuous Range of On Duty Dates")
                .addOption("Discrete On Duty Dates")
                .build();
        int dateTypeChoice = -1;
        while(dateTypeChoice == -1)
        {
            dateTypeChoice = continuousDiscreteMenu.displayMenuAndGetChoice();
            if(dateTypeChoice == -1)
                println2("Invalid Choice ! Enter a valid choice...");
        }
        getContinuousOrDiscreteDates(odRequestedDates, "On Duty",dateTypeChoice == 1);
        getReason(RequestLetterType.ON_DUTY);
    }

    private void getDeptChangeLetterDetails()
    {
        resetVariables();
        while(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
        {
            destinationDeptId = getUserInput(destinationDeptId, "Department ID of the department where you want to get transferred");
            if(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
                println2("Enter a valid Department ID...");
        }
        getReason(RequestLetterType.DEPT_CHANGE);
    }

    private void getPromotionLetterDetails()
    {
        resetVariables();
        while(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
        {
            destinationDeptId = getUserInput(destinationDeptId, "Department ID of the department where you want to assume a higher position (Enter 1 if it is the Current Department)");
            if(destinationDeptId.equals("1"))
            {
                break;
            }
            if(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
                println2("Enter a valid Department ID...");
        }
        getReason(RequestLetterType.PROMOTION);
    }

    private void getDemotionLetterDetails()
    {
        resetVariables();
        while(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
        {
            destinationDeptId = getUserInput(destinationDeptId, "Department ID of the department where you want to assume a lower position (Enter 1 if it is the Current Department)");
            if(destinationDeptId.equals("1"))
            {
                break;
            }
            if(!AccountsManager.getInstance().checkIfDeptIdExists(destinationDeptId))
                println2("Enter a valid Department ID...");
        }
        getReason(RequestLetterType.DEMOTION);
    }

    private void getResignationLetterDetails()
    {
        resetVariables();
        int noticePeriod = 60;
        while(!InputDataValidator.validateDateString(requestedQuitDate,noticePeriod))
        {
            requestedQuitDate = getUserInput(requestedQuitDate, "Date when you want to be relieved of job (Note that the notice period is 60 days)");
            if(!InputDataValidator.validateDateString(requestedQuitDate,noticePeriod))
                println("Enter a valid date and make sure that you are considering the notice period of 60 days...");
        }
        getReason(RequestLetterType.RESIGNATION);
    }

    public synchronized RequestLetter getLetter(String requesterId, String receiverId, RequestLetterType requestLetterType, String... crtDeptId)
    {
        switch (requestLetterType)
        {
            case LEAVE:
                getLeaveLetterDetails();
                return new LeaveRequestLetter(requesterId, receiverId, reasonForRequest, leaveRequestedDates);

            case ON_DUTY:
                getOdLetterDetails();
                return new ODRequestLetter(requesterId, receiverId, reasonForRequest, odRequestedDates);

            case DEPT_CHANGE:
                if(crtDeptId.length != 1)
                    return null;
                getDeptChangeLetterDetails();
                currentDeptId = crtDeptId[0];
                return new DeptChangeRequestLetter(requesterId, receiverId, reasonForRequest, currentDeptId, destinationDeptId);

            case PROMOTION:
                if(crtDeptId.length != 1)
                    return null;
                getPromotionLetterDetails();
                currentDeptId = crtDeptId[0];
                return new PromotionRequestLetter(requesterId, receiverId, reasonForRequest, destinationDeptId.equals("1") ? currentDeptId : destinationDeptId);

            case DEMOTION:
                if(crtDeptId.length != 1)
                    return null;
                getDemotionLetterDetails();
                currentDeptId = crtDeptId[0];
                return new DemotionRequestLetter(requesterId, receiverId, reasonForRequest, destinationDeptId.equals("1") ? currentDeptId : destinationDeptId);

            case RESIGNATION:
                getResignationLetterDetails();
                return new ResignationRequestLetter(requesterId, receiverId, reasonForRequest, requestedQuitDate);

            case TC:
                if(crtDeptId.length != 1)
                    return null;
                getReason(RequestLetterType.TC);
                currentDeptId = crtDeptId[0];
                return new TCRequestLetter(requesterId,receiverId, reasonForRequest, currentDeptId);

            default:
                return null;
        }
    }
}
