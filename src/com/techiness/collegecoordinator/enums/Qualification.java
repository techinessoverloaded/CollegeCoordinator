package com.techiness.collegecoordinator.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Stream;

public enum Qualification
{
    BE,ME,BTECH,MTECH,BBA,MBA,BSC,MSC,BA,MA,BED,MED,MPHIL,PHD;

    public static String[] getStringArrayOfValues()
    {
        return Stream.of(values()).map(Enum::name).toArray(String[]::new);
    }

    public static String[] getStringArrayOfSetDifferenceValues(EnumSet<Qualification> existingQualifications)
    {
        return Stream.of(values()).filter(qualification -> !existingQualifications.contains(qualification)).map(Enum::name).toArray(String[]::new);
    }

    public static String[] getStringArrayOfValues(Qualification[] qualifications)
    {
        return Arrays.stream(qualifications).map(Enum::name).toArray(String[]::new);
    }

    public static int getEnumSetDifference(EnumSet<Qualification> existingQualifications)
    {
        return Stream.of(values()).filter(qualification -> !existingQualifications.contains(qualification)).mapToInt(value -> 1).sum();
    }
}
