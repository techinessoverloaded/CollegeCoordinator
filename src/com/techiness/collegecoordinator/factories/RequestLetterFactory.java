package com.techiness.collegecoordinator.factories;

import com.techiness.collegecoordinator.enums.LetterType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.InputDataValidator;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import static com.techiness.collegecoordinator.helpers.IOUtils.getUserInput;
import static com.techiness.collegecoordinator.helpers.IOUtils.println;

public class RequestLetterFactory
{
    private static RequestLetterFactory instance = null;
    private String reasonForRequest = "";

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
    }

    private void getLetterDetails(LetterType letterType)
    {
        resetVariables();
        AccountsManager accountsManager = AccountsManager.getInstance();
        while (!InputDataValidator.validateReason(reasonForRequest))
        {
            reasonForRequest = getUserInput(reasonForRequest,"Reason for applying "+letterType);
            if(!InputDataValidator.validateReason(reasonForRequest))
                println("Invalid Input ! Enter the reason properly !");
        }
    }

    public RequestLetter getLetter(String requesterId, String receiverId, LetterType letterType)
    {
        getLetterDetails(letterType);
        return new RequestLetter(requesterId, receiverId, letterType, reasonForRequest);
    }
}
