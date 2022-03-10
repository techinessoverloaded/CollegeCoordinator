package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import com.techiness.collegecoordinator.helpers.Offer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingHead extends HoD
{
    private List<String> companies;

    public TrainingHead(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
                        List<String> qualifications, int experience, HashMap<String, Student> students, List<Letter> letters,
                        HashMap<String, Faculty> faculties, List<String> companies)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, students, letters, faculties);
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
        List<Student> trainees = new ArrayList<>();
        for(Student student : students.values())
        {
            if(student.isNeedsTraining())
                trainees.add(student);
        }
        return trainees;
    }

    public boolean addOffer(String id, Offer offer)
    {
        if(!students.containsKey(id)||students.get(id)==null)
            return false;
        Student currentStudent = students.get(id);
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

    public boolean setIsPlaced(String id,boolean isPlaced)
    {
        if(!students.containsKey(id)||students.get(id)==null)
            return false;
        Student currentStudent = students.get(id);
        currentStudent.setPlaced(isPlaced);
        return true;
    }
}
