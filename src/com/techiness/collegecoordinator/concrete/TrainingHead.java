package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import com.techiness.collegecoordinator.helpers.Offer;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingHead extends HoD
{
    private List<String> companies;

    public TrainingHead(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
                        List<String> qualifications, int experience, HashMap<String, Student> students, Map<String, Letter> letters,
                        HashMap<String, Faculty> faculties, List<String> companies, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, students, letters, faculties, deptId);
        this.companies = companies;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.TRAINING_HEAD;
    }

    public List<String> getCompanies()
    {
        return companies;
    }

    public void setCompanies(List<String> companies)
    {
        this.companies = companies;
    }

    public List<Student> getTrainees()
    {
        return students.values().stream().filter(Student::isNeedsTraining).collect(Collectors.toList());
    }

    public boolean addOffer(String studentId, Offer offer)
    {
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        if(currentStudent.getOffers()==null||currentStudent.getOffers().contains(offer))
            return false;
        currentStudent.getOffers().add(offer);
        return true;
    }

    public boolean addCompany(String company)
    {
        if(companies.contains(company))
            return false;
        companies.add(company);
        return true;
    }

    public boolean setIsPlaced(String studentId, boolean isPlaced)
    {
        if(!students.containsKey(studentId)||students.get(studentId)==null)
            return false;
        Student currentStudent = students.get(studentId);
        currentStudent.setIsPlaced(isPlaced);
        return true;
    }
}