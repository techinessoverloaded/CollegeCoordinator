package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import java.util.List;
import java.util.Map;

public class HoD extends Faculty
{
    protected Map<String, Letter> letters;

    public HoD(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
               List<String> qualifications, int experience, Map<String, Letter> letters, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, deptId);
        this.letters = letters;
    }

    public HoD(Faculty existingFaculty, Map<String, Letter> letters)
    {
        super(existingFaculty.getName(), existingFaculty.getAge(), existingFaculty.getGender(),
                existingFaculty.getPhone(), existingFaculty.getEmail(), existingFaculty.getPassword(),
                existingFaculty.subjectsHandled, existingFaculty.qualifications, existingFaculty.experience,
                existingFaculty.deptId);
        this.letters = letters;
    }

    @Override
    public String getId()
    {
        return id+"#"+deptId+"_"+ UserType.HOD;
    }

    public Map<String, Letter> getLetters()
    {
        return letters;
    }

    public void setLetters(Map<String, Letter> letters)
    {
        this.letters = letters;
    }

    public boolean addFaculty(Faculty faculty)
    {
        String facultyId = faculty.getId();
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(faculties.containsKey(facultyId)&&faculties.get(facultyId)!=null)
            return false;
        else if(faculties.get(facultyId)==null)
        {
            faculties.remove(facultyId);
            faculties.put(facultyId,faculty);
            return true;
        }
        else
        {
            faculties.put(facultyId, faculty);
            return true;
        }
    }

    public boolean removeFaculty(String facultyId)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId))
            return false;
        faculties.remove(facultyId);
        return true;
    }

    public void addLetter(Letter letter)
    {
        letters.put(letter.getLetterId(),letter);
    }

    public boolean addSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId)||faculties.get(facultyId)==null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled()==null || currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().add(subject);
        return true;
    }

    public boolean removeSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId)==null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled()==null || !currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().remove(subject);
        return true;
    }

    public void signLetter(String letterId, boolean isGranted)
    {
        letters.get(letterId).setIsGranted(isGranted);
    }

    @Override
    public String toString()
    {
        return "HoD"+super.toString().substring(super.toString().indexOf("Faculty")+1)+", \ndepartmentID = "+deptId+", \nqualifications = "+qualifications
            +", experience = "+experience+", subjectsHandled = "+subjectsHandled+", letters = "+letters+" ]";
    }
}
