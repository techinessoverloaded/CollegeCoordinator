package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Company;
import com.techiness.collegecoordinator.helpers.Letter;
import com.techiness.collegecoordinator.helpers.Offer;
import java.util.Map;
import java.util.List;

public final class TrainingHead extends HoD
{
    public TrainingHead(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
                        List<String> qualifications, int experience, Map<String, Letter> letters, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, letters, deptId);
    }

    @Override
    public String getId()
    {
        return id+"#"+deptId+"_"+ UserType.TRAINING_HEAD;
    }

    public boolean addOffer(String studentId, Offer offer)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        if(currentStudent.getOffers()==null||currentStudent.getOffers().contains(offer))
            return false;
        currentStudent.getOffers().add(offer);
        return true;
    }

    public boolean addCompany(Company company)
    {
        PlacementDepartment placementDepartment = (PlacementDepartment) AccountsManager.getInstance().getDepartments().get(deptId);
        if(placementDepartment == null)
            return false;
        if(placementDepartment.getCompanies().containsKey(company.getId()))
            return false;
        placementDepartment.getCompanies().put(company.getId(),company);
        return true;
    }

    public boolean setIsPlaced(String studentId, boolean isPlaced)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        currentStudent.setIsPlaced(isPlaced);
        return true;
    }

    @Override
    public String toString()
    {
        return "TrainingHead"+super.toString().substring(super.toString().indexOf("HoD")+1)+" ]";
    }
}