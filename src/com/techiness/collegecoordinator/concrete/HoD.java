package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoD extends Faculty
{
    protected Map<String, Letter> letters;
    protected Map<String, Faculty> faculties;

    public HoD(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
               List<String> qualifications, int experience, HashMap<String, Student> students, Map<String, Letter> letters,
               Map<String, Faculty> faculties, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, students, deptId);
        this.letters = letters;
        this.faculties = faculties;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.HOD;
    }

    public Map<String, Letter> getLetters()
    {
        return letters;
    }

    public void setLetters(Map<String, Letter> letters)
    {
        this.letters = letters;
    }

    public Map<String, Faculty> getFaculties()
    {
        return faculties;
    }

    public void setFaculties(Map<String, Faculty> faculties)
    {
        this.faculties = faculties;
    }

    public boolean addFaculty(Faculty faculty)
    {
        String facultyId = faculty.getId();
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
        if(!faculties.containsKey(facultyId)||faculties.get(facultyId)==null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled()==null||currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().add(subject);
        return true;
    }

    public boolean removeSubjectHandled(String facultyId, String subject)
    {
        if(!faculties.containsKey(facultyId)||faculties.get(facultyId)==null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled()==null||!currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().remove(subject);
        return true;
    }

    public void signLetter(String letterId, boolean isGranted)
    {
        letters.get(letterId).setIsGranted(isGranted);
    }
}
