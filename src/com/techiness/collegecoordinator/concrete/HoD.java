package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.Letter;
import java.util.List;
import java.util.Map;
import static com.techiness.collegecoordinator.helpers.IOUtils.getStringOfIdentifiableMap;

public class HoD extends Faculty
{
    protected Map<String, Letter> letters;

    public HoD(String name, int age, Gender gender, String phone, String email, String password, List<String> subjectsHandled,
               List<String> qualifications, int experience, Map<String, Letter> letters, String deptId)
    {
        super(name, age, gender, phone, email, password, subjectsHandled, qualifications, experience, deptId);
        this.letters = letters;
    }

    public HoD(Faculty existingFaculty, String newDeptId ,Map<String, Letter> letters)
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
        return faculties.putIfAbsent(faculty.getId(),faculty) == null;
    }

    public boolean removeFaculty(String facultyId)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        return faculties.remove(facultyId) != null;
    }

    public boolean addLetter(Letter letter)
    {
        return letters.putIfAbsent(letter.getId(),letter) == null;
    }

    public boolean addSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().add(subject);
        return true;
    }

    public boolean removeSubjectHandled(String facultyId, String subject)
    {
        Map<String,Faculty> faculties = AccountsManager.getInstance().getDepartments().get(deptId).getFaculties();
        if(!faculties.containsKey(facultyId) || faculties.get(facultyId) == null)
            return false;
        Faculty currentFaculty = faculties.get(facultyId);
        if(currentFaculty.getSubjectsHandled() == null || !currentFaculty.getSubjectsHandled().contains(subject))
            return false;
        currentFaculty.getSubjectsHandled().remove(subject);
        return true;
    }

    public void signLetter(String letterId, boolean isGranted)
    {
        letters.get(letterId).setIsGranted(isGranted);
    }

    public boolean checkIfPermissionGranted(String letterId)
    {
        Letter currentLetter = letters.get(letterId);
        if(currentLetter == null)
            return false;
        currentLetter.setIsNotifiedToRequester(true);
        return currentLetter.getIsGranted();
    }

    @Override
    public String toString()
    {
        return "HoD"+super.toString().substring(super.toString().indexOf("Faculty")+8)+"\nletters = "+getStringOfIdentifiableMap(letters)+" ]";
    }
}
