package com.techiness.collegecoordinator.helpers;

public class Offer
{
    private String company;
    private String designation;
    private double compensation;

    public Offer(String company, String designation, double compensation)
    {
        this.company = company;
        this.designation = designation;
        this.compensation = compensation;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public double getCompensation()
    {
        return compensation;
    }

    public void setCompensation(double compensation)
    {
        this.compensation = compensation;
    }
}
