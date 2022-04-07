package com.techiness.collegecoordinator.enums;

import java.util.stream.Stream;

public enum Gender
{
    MALE,FEMALE,OTHER;

    public static String[] getStringArrayOfValues()
    {
        return Stream.of(values()).map(Enum::name).toArray(String[]::new);
    }
}
