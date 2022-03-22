package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Identifiable;

public class Offer implements Identifiable
{
    private static int idGen = 1;
    private String id;
    private String studentId;
    private String companyId;
    private String designation;
    private double compensation;

    public Offer(String studentId, String companyId, String designation, double compensation)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.studentId = studentId;
        this.companyId = companyId;
        this.designation = designation;
        this.compensation = compensation;
    }

    public static int getIdGen()
    {
        return idGen;
    }

    public static void setIdGen(int idGen)
    {
        Offer.idGen = idGen;
    }

    @Override
    public String getId()
    {
        return id+"~"+studentId+"@"+companyId;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
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
