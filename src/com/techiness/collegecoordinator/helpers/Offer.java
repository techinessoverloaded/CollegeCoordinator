package com.techiness.collegecoordinator.helpers;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.compensation, compensation) == 0 && company.equals(offer.company)
                && designation.equals(offer.designation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(company, designation, compensation);
    }
}
