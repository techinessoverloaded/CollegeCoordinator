package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.RequestLetter;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import javafx.util.Pair;
import java.util.Map;

public final class Admin extends User
{
    private Map<String, RequestLetter> letters;

    public Admin(String name, int age, Gender gender, String phone, String email, String password, Map<String, RequestLetter> letters)
    {
        super(name, age, gender, phone, email, password);
        this.letters = letters;
    }

    @Override
    public String getId()
    {
        return id+"_"+ UserType.ADMIN;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
        setChanged();
        notifyObservers();
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

    public boolean addLetter(RequestLetter requestLetter)
    {
        return letters.putIfAbsent(requestLetter.getId(), requestLetter) == null;
    }

    public boolean approveLetter(String letterId, boolean isApproved)
    {
        RequestLetter currentRequestLetter = getLetters(letterId);
        if(currentRequestLetter == null)
            return false;
        currentRequestLetter.setIsGranted(isApproved);
        return true;
    }

    public boolean checkIfLetterApproved(String letterId)
    {
        RequestLetter currentRequestLetter = getLetters(letterId);
        if(currentRequestLetter == null)
            return false;
        currentRequestLetter.setIsNotifiedToRequester(true);
        return currentRequestLetter.getIsGranted();
    }

    public boolean addDepartment(Department department)
    {
        return AccountsManager.getInstance().getDepartments().putIfAbsent(department.getId(), department) == null;
    }

    public boolean removeDepartment(String deptId)
    {
        return AccountsManager.getInstance().getDepartments().remove(deptId) != null;
    }

    public boolean assignHoD(String deptId, HoD hod)
    {
        Map<String,Department> departments = AccountsManager.getInstance().getDepartments();
        if(!departments.containsKey(deptId) || departments.get(deptId) == null)
            return false;
        Department currentDepartment = departments.get(deptId);
        if(currentDepartment.getHod() != null)
            return false;
        currentDepartment.setHod(hod);
        return true;
    }

    public boolean changeHoD(String deptId, HoD newHoD, boolean isDemotedToSameDept, String... newDeptId)
    {
        AccountsManager accountsManager = AccountsManager.getInstance();
        Map<String,Department> departments = accountsManager.getDepartments();
        if(!departments.containsKey(deptId) || departments.get(deptId) == null)
            return false;
        Department currentDepartment = departments.get(deptId);
        HoD oldHoD = currentDepartment.getHod();
        if(isDemotedToSameDept)
        {
            String assigningDeptId = newDeptId.length == 1 ? newDeptId[0] : deptId;
            Faculty newFaculty = new Faculty(oldHoD, assigningDeptId);
            departments.get(assigningDeptId).getFaculties().put(newFaculty.getId(),newFaculty);
            accountsManager.getUsers().remove(oldHoD.getId());
            accountsManager.getUsers().put(newFaculty.getId(), newFaculty);
            accountsManager.getUsers().put(newHoD.getId(), newHoD);
            currentDepartment.setHod(newHoD);
        }
        else
        {
            currentDepartment.setHod(newHoD);
            accountsManager.getUsers().remove(oldHoD.getId());
        }
        return true;
    }


    public Pair<HoD,Faculty> promoteFacultyToSameDeptHoD(String facultyId, String deptId, boolean isDemoted, String... diffDeptId)
    {
        AccountsManager accountsManager = AccountsManager.getInstance();
        Map<String,Department> departments = accountsManager.getDepartments();
        if(!departments.containsKey(deptId) || departments.get(deptId) == null)
            return null;
        Department currentDepartment = departments.get(deptId);
        HoD oldHoD = currentDepartment.getHod();
        Faculty existingFaculty = currentDepartment.getFaculties(facultyId);
        if(existingFaculty == null)
            return null;
        HoD newHoD = new HoD(existingFaculty, deptId, oldHoD.letters);
        accountsManager.getUsers().remove(facultyId);
        accountsManager.getUsers().put(newHoD.getId(), newHoD);
        currentDepartment.getFaculties().remove(facultyId);
        currentDepartment.setHod(newHoD);
        Faculty newFaculty = null;
        if(isDemoted)
        {
            String assigningDeptId = diffDeptId.length == 1 ? diffDeptId[0] : deptId;
            newFaculty = new Faculty(oldHoD, assigningDeptId);
            accountsManager.getUsers().remove(oldHoD.getId());
            accountsManager.getUsers().put(newFaculty.getId(), newFaculty);
            accountsManager.getDepartments(assigningDeptId).getFaculties().put(newFaculty.getId(), newFaculty);
        }
        else
        {
            accountsManager.getUsers().remove(oldHoD.getId());
        }
        return new Pair<>(newHoD, newFaculty);
    }

    public Pair<HoD,Faculty> promoteFacultyToDifferentDeptHoD(String facultyId, String currentDeptId, String destDeptId,
                                                     boolean isDemoted, String... diffDeptId)
    {
        AccountsManager accountsManager = AccountsManager.getInstance();
        Map<String,Department> departments = accountsManager.getDepartments();
        if(!departments.containsKey(currentDeptId) || departments.get(currentDeptId) == null ||
                departments.containsKey(destDeptId) || departments.get(destDeptId) == null)
            return null;
        Department currentDepartment = departments.get(currentDeptId);
        Department destinationDepartment = departments.get(destDeptId);
        Faculty existingFaculty = currentDepartment.getFaculties(facultyId);
        if(existingFaculty == null)
            return null;
        HoD oldHoD = destinationDepartment.getHod();
        HoD newHoD = new HoD(existingFaculty, destDeptId, oldHoD.letters);
        accountsManager.getUsers().remove(facultyId);
        currentDepartment.getFaculties().remove(facultyId);
        destinationDepartment.setHod(newHoD);
        Faculty newFaculty = null;
        if(oldHoD != null)
        {
            if (isDemoted)
            {
                String assigningDeptId = diffDeptId.length == 1 ? diffDeptId[0] : destDeptId;
                newFaculty = new Faculty(oldHoD, assigningDeptId);
                accountsManager.getUsers().remove(oldHoD.getId());
                accountsManager.getUsers().put(newFaculty.getId(), newFaculty);
                accountsManager.getDepartments(assigningDeptId).getFaculties().put(newFaculty.getId(), newFaculty);
            }
            else
            {
                accountsManager.getUsers().remove(oldHoD.getId());
            }
        }
        return new Pair<>(newHoD, newFaculty);
    }

    @Override
    public String toString()
    {
        return "Admin"+super.toString()+" ]";
    }
}
