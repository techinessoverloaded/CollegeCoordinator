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

    public TrainingHead(Faculty existingFaculty, String newDeptId, Map<String, Letter> letters)
    {
        super(existingFaculty.getName(), existingFaculty.getAge(), existingFaculty.getGender(),
                existingFaculty.getPhone(), existingFaculty.getEmail(), existingFaculty.getPassword(),
                existingFaculty.getSubjectsHandled(), existingFaculty.getQualifications(),
                existingFaculty.getExperience(), letters, newDeptId);
    }

    public TrainingHead(HoD existingHoD, String newDeptId)
    {
        super(existingHoD.getName(), existingHoD.getAge(), existingHoD.getGender(),
                existingHoD.getPhone(), existingHoD.getEmail(), existingHoD.getPassword(),
                existingHoD.subjectsHandled, existingHoD.qualifications,
                existingHoD.experience, existingHoD.letters, newDeptId);
    }

    @Override
    public String getId()
    {
        return id+"@"+deptId+"_"+ UserType.TRAINING_HEAD;
    }

    public boolean addOffer(String studentId, Offer offer)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        return currentStudent.getOffers().putIfAbsent(offer.getId(), offer) == null;
    }

    public boolean removeOffer(String studentId, String offerId)
    {
        Map<String,Student> students = AccountsManager.getInstance().getDepartments().get(deptId).getStudents();
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        return currentStudent.getOffers().remove(offerId) != null;
    }

    public boolean addCompany(Company company)
    {
        PlacementDepartment placementDepartment = (PlacementDepartment) AccountsManager.getInstance().getDepartments().get(deptId);
        if(placementDepartment == null)
            return false;
        return placementDepartment.getCompanies().putIfAbsent(company.getId(),company) == null;
    }

    public boolean removeCompany(String companyId)
    {
        PlacementDepartment placementDepartment = (PlacementDepartment) AccountsManager.getInstance().getDepartments().get(deptId);
        if(placementDepartment == null)
            return false;
        return placementDepartment.getCompanies().remove(companyId) != null;
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