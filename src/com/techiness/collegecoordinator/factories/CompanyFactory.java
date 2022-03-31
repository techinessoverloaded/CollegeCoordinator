package com.techiness.collegecoordinator.factories;

public class CompanyFactory
{
    private static CompanyFactory instance = null;

    private CompanyFactory()
    {

    }

    public synchronized static CompanyFactory getInstance()
    {
        if(instance == null)
            instance = new CompanyFactory();
        return instance;
    }
}
