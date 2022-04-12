package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.Qualification;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.managers.AccountsManager;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import static com.techiness.collegecoordinator.utils.IOUtils.getStringOfIdentifiableMap;

public class HoD extends Faculty
{
    protected Map<String, RequestLetter> letters;

    public HoD(String name, int age, Gender gender, String phone, String email, String password, Set<String> subjectsHandled,
               EnumSet<Qualification> qualifications, int experience, Map<String, RequestLetter> letters, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, deptId);
        this.letters = letters;
    }

    public HoD(Faculty existingFaculty, String newDeptId ,Map<String, RequestLetter> letters)
    {
        super(existingFaculty.getName(), existingFaculty.getAge(), existingFaculty.getGender(),
                existingFaculty.getPhone(), existingFaculty.getEmail(), existingFaculty.getPassword(),
                existingFaculty.subjectsHandled, existingFaculty.qualifications, existingFaculty.experience,
                newDeptId);
        this.letters = letters;
    }

    public HoD(TrainingHead existingTrainingHead, String newDeptId)
    {
        super(existingTrainingHead.name, existingTrainingHead.age, existingTrainingHead.gender,
                existingTrainingHead.phone, existingTrainingHead.email, existingTrainingHead.password,
                existingTrainingHead.subjectsHandled, existingTrainingHead.qualifications,
                existingTrainingHead.experience,newDeptId);
    }

    @Override
    public String getId()
    {
        return id+"@"+deptId+"_"+ UserType.HOD;
    }

    public Map<String, RequestLetter> getLetters()
    {
        return letters;
    }

    public RequestLetter getLetters(String letterId)
    {
        return !letters.containsKey(letterId) || letters.get(letterId)==null ? null : letters.get(letterId);
    }

    public void setLetters(Map<String, RequestLetter> letters)
    {
        this.letters = letters;
    }

    public boolean addFaculty(Faculty faculty)
    {
        String facultyId = faculty.getId();
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        return faculties.putIfAbsent(faculty.getId(),faculty) == null;
    }

    public boolean removeFaculty(String facultyId)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        return faculties.remove(facultyId) != null;
    }

    public boolean addLetter(RequestLetter requestLetter)
    {
        return letters.putIfAbsent(requestLetter.getId(), requestLetter) == null;
    }

    public boolean addSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        return currentFaculty.getSubjectsHandled().add(subject);
    }

    public boolean addSubjectHandled(String facultyId, Set<String> subjects)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || subjects.stream().anyMatch(s-> currentFaculty.getSubjectsHandled().contains(s)))
            return false;
        return currentFaculty.getSubjectsHandled().addAll(subjects);
    }

    public boolean removeSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || !currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        return currentFaculty.getSubjectsHandled().remove(subject);
    }

    public boolean removeSubjectHandled(String facultyId, Set<String> subjects)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || !currentFaculty.getSubjectsHandled().containsAll(subjects))
            return false;
        return currentFaculty.getSubjectsHandled().removeAll(subjects);
    }

    public boolean approveLetter(String letterId, boolean isApproved)
    {
        RequestLetter currentRequestLetter = getLetters(letterId);
        if(currentRequestLetter == null)
            return false;
        currentRequestLetter.setIsApproved(isApproved);
        return true;
    }

    public boolean checkIfLetterApproved(String letterId)
    {
        RequestLetter currentRequestLetter = getLetters(letterId);
        if(currentRequestLetter == null)
            return false;
        currentRequestLetter.setIsNotifiedToRequester(true);
        return currentRequestLetter.getIsApproved();
    }

    @Override
    public String toString()
    {
        return "HoD"+super.toString().substring(super.toString().indexOf("Faculty")+8)+"\nletters = "+getStringOfIdentifiableMap(letters)+" ]";
    }
}
