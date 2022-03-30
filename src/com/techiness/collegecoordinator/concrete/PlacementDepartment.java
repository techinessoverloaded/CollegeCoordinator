package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.enums.DepartmentType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Company;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.techiness.collegecoordinator.helpers.IOUtils.getStringOfNameableMap;

public final class PlacementDepartment extends Department
{
    private TrainingHead trainingHead;
    private Map<String,Company> companies;

    public PlacementDepartment(String name, TrainingHead trainingHead, Map<String, Faculty> faculties, Map<String,Company> companies)
    {
        super(name, trainingHead, faculties, new HashMap<>());
        this.trainingHead = trainingHead;
        this.companies = companies;
        List<Department> allCourseDepartments = AccountsManager.getInstance().getDepartments().values().stream().filter(department -> department instanceof CourseDepartment).collect(Collectors.toList());
        List<Student> allDeptStudentsWhoNeedTraining = allCourseDepartments.stream().map(Department::getStudents).map(Map::values).flatMap(Collection::stream).filter(Student::isNeedsTraining).collect(Collectors.toList());
        allDeptStudentsWhoNeedTraining.forEach(trainingHead::addStudent);
    }

    @Override
    public String getId()
    {
        return id+"*"+name+"#"+DepartmentType.PLACEMENT;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public HoD getHod()
    {
        return trainingHead;
    }

    @Override
    public void setHod(HoD hod)
    {
        this.hod = this.trainingHead = (TrainingHead) hod;
    }

    public TrainingHead getTrainingHead()
    {
        return trainingHead;
    }

    public void setTrainingHead(TrainingHead trainingHead)
    {
        this.trainingHead = trainingHead;
    }

    public Map<String,Company> getCompanies()
    {
        return companies;
    }

    public Company getCompanies(String companyId)
    {
        if(!companies.containsKey(companyId) || companies.get(companyId) == null)
            return null;
        return companies.get(companyId);
    }

    public void setCompanies(Map<String,Company> companies)
    {
        this.companies = companies;
    }

    @Override
    public String getDeptShortName()
    {
        //return name.chars().filter(Character::isUpperCase).collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();
        return name.chars().filter(Character::isUpperCase).mapToObj(ch -> String.valueOf((char)ch)).collect(Collectors.joining());
    }

    public Map<String,Student> getTrainees()
    {
        List<Student> traineeList = students.values().stream().filter(Student::isNeedsTraining).collect(Collectors.toList());
        HashMap<String,Student> trainees = new HashMap<>();
        for(Student s : traineeList)
        {
            trainees.put(s.getId(),s);
        }
        return trainees;
    }

    @Override
    public String toString()
    {
        return "PlacementDepartment"+super.toString()+
                ", \ntrainingHead = "+trainingHead+
                ", \ncompanies = "+getStringOfNameableMap(companies)+ " ]";
    }
}
