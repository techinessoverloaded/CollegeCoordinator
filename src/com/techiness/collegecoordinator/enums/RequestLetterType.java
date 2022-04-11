package com.techiness.collegecoordinator.enums;

import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.concrete.*;

import java.util.ArrayList;
import java.util.stream.Stream;

public enum RequestLetterType
{
    ON_DUTY,LEAVE,RESIGNATION,PROMOTION,DEMOTION,DEPT_CHANGE,TC;

    public static String[] getStringArrayOfValues()
    {
        return Stream.of(values()).map(Enum::name).toArray(String[]::new);
    }

    public static RequestLetterType getLetterTypeFromInstance(RequestLetter requestLetter)
    {
        if(requestLetter instanceof LeaveRequestLetter)
            return LEAVE;
        else if(requestLetter instanceof ODRequestLetter)
            return ON_DUTY;
        else if(requestLetter instanceof PromotionRequestLetter)
            return PROMOTION;
        else if(requestLetter instanceof DemotionRequestLetter)
            return DEMOTION;
        else if(requestLetter instanceof DeptChangeRequestLetter)
            return DEPT_CHANGE;
        else if(requestLetter instanceof ResignationRequestLetter)
            return RESIGNATION;
        else if(requestLetter instanceof TCRequestLetter)
            return TC;
        else
            return null;
    }

    public static String[] getStringArrayOfStudentLetterTypes()
    {
        ArrayList<RequestLetterType> listOfRequestLetterType = new ArrayList<>();
        listOfRequestLetterType.add(LEAVE);
        listOfRequestLetterType.add(ON_DUTY);
        listOfRequestLetterType.add(TC);
        return listOfRequestLetterType.stream().map(Enum::name).toArray(String[]::new);
    }
}
