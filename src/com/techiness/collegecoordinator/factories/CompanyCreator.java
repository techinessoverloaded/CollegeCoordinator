package com.techiness.collegecoordinator.factories;

public class CompanyCreator
{
    private static CompanyCreator instance = null;

    private CompanyCreator()
    {

    }

    public synchronized static CompanyCreator getInstance()
    {
        if(instance == null)
            instance = new CompanyCreator();
        return instance;
    }
}