package com.techiness.collegecoordinator;

import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.Letter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HoD extends Faculty
{
    private List<Letter> letters;
    private HashMap<String,Faculty> faculties;

    public HoD()
    {
        super(new ArrayList<>(),new ArrayList<>(),1,new HashMap<>());
        this.letters = new ArrayList<>();
        this.faculties = new HashMap<>();
    }

    public HoD(List<String> subjectsHandled, List<String> qualifications, int experience, HashMap<String, Student> students, List<Letter> letters, HashMap<String, Faculty> faculties)
    {
        super(subjectsHandled, qualifications, experience, students);
        this.letters = letters;
        this.faculties = faculties;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.HOD;
    }

    public List<Letter> getLetters()
    {
        return letters;
    }

    public void setLetters(List<Letter> letters)
    {
        this.letters = letters;
    }

    public HashMap<String, Faculty> getFaculties()
    {
        return faculties;
    }

    public void setFaculties(HashMap<String, Faculty> faculties)
    {
        this.faculties = faculties;
    }

    public boolean addFaculty(String id, Faculty faculty)
    {
        if(faculties.containsKey(id)&&faculties.get(id)!=null)
            return false;
        faculties.put(id,faculty);
        return true;
    }

    public boolean removeFaculty(String id)
    {
        if(!faculties.containsKey(id))
            return false;
        faculties.remove(id);
        return true;
    }

    public boolean addStudent(String id, Student student)
    {
        if(students.containsKey(id)&&students.get(id)!=null)
            return false;
        students.put(id,student);
        return true;
    }

    public boolean removeStudent(String id)
    {
        if(!students.containsKey(id))
            return false;
        students.remove(id);
        return true;
    }

    public void addLetter(Letter letter)
    {
        letters.add(letter);
    }

    public boolean addSubjectHandled(String id, String subject)
    {
        if(!faculties.containsKey(id)||faculties.get(id)==null)
            return false;
        Faculty currentFaculty = faculties.get(id);
        if(currentFaculty.getSubjectsHandled()==null||currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().add(subject);
        return true;
    }

    public boolean removeSubjectHandled(String id, String subject)
    {
        if(!faculties.containsKey(id)||faculties.get(id)==null)
            return false;
        Faculty currentFaculty = faculties.get(id);
        if(currentFaculty.getSubjectsHandled()==null||!currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().remove(subject);
        return true;
    }

    public void signLetters()
    {

    }
}
