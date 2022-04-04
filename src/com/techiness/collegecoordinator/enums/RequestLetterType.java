package com.techiness.collegecoordinator.enums;

import java.util.stream.Stream;

public enum RequestLetterType
{
    ON_DUTY,LEAVE,RESIGNATION,PROMOTION,DEMOTION,DEPT_CHANGE;

    public static String[] getStringArrayOfValues()
    {
        return Stream.of(values()).map(Enum::name).toArray(String[]::new);
    }
}
