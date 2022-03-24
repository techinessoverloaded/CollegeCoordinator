package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.concrete.CourseDepartment;
import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.enums.UserType;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class AccountsManager implements Serializable
{
    private static AccountsManager instance = null;
    private Map<String, Department> departments = null;
    private Map<String, User> users = null;
    private Admin admin = null;
    private boolean isFirstTime = true;

    private AccountsManager()
    {

    }

    //Singleton Pattern
    public synchronized static AccountsManager getInstance()
    {
        if(instance == null)
            instance = new AccountsManager();
        return instance;
    }

    public boolean isFirstTime()
    {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime)
    {
        this.isFirstTime = firstTime;
    }

    public Map<String, Department> getDepartments()
    {
        return departments;
    }

    public Department getDepartments(String deptId)
    {
        return !departments.containsKey(deptId) || departments.get(deptId) == null ? null : departments.get(deptId);
    }

    public boolean checkIfDeptAlreadyExists(String deptName)
    {
        return departments.values().stream().anyMatch(department -> department.getName().equals(deptName));
    }

    public void setDepartments(Map<String, Department> departments)
    {
        this.departments = departments;
    }

    public Map<String, User> getUsers()
    {
        return users;
    }

    public User getUsers(String userId)
    {
        return !users.containsKey(userId) || users.get(userId)==null ? null : users.get(userId);
    }

    public void setUsers(Map<String, User> users)
    {
        this.users = users;
    }

    public Admin getAdmin()
    {
        return admin;
    }

    public void setAdmin(Admin admin)
    {
        this.admin = admin;
    }


    public boolean deleteUser(String id)
    {
        if(!users.containsKey(id)||users.get(id)==null)
            return false;
        users.remove(id);
        return true;
    }

    public String registerUser(User user) {
        String userId = user.getId();
        if (users.containsKey(user.getId()))
            return null;
        users.put(user.getId(), user);
        return user.getId();
    }

    public boolean noAdminAvailable()
    {
        return admin == null;
    }

    public boolean checkIfAnyUserAvailable(UserType userType)
    {
        return users.values().stream().map(User::getId).map(StringToEnumUtils::getUserTypeFromUserId).anyMatch(uType -> uType == userType);
    }

    public void persistState() throws IOException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        serializationHelper.persistObject(admin,"admin.txt");
        serializationHelper.persistObject(isFirstTime,"isFirstTime.txt");
        serializationHelper.persistObject((HashMap<String,User>)users,"users.txt");
        serializationHelper.persistObject((HashMap<String,Department>)departments,"departments.txt");
        int userIdGen = User.getIdGen();
        serializationHelper.persistObject(userIdGen,"userIdGen.txt");
        int deptIdGen = Department.getIdGen();
        serializationHelper.persistObject(deptIdGen,"deptIdGen.txt");
        int letterIdGen = Letter.getIdGen();
        serializationHelper.persistObject(letterIdGen,"letterIdGen.txt");
        int companyIdGen = Company.getIdGen();
        serializationHelper.persistObject(companyIdGen,"companyIdGen.txt");
        int offerIdGen = Offer.getIdGen();
        serializationHelper.persistObject(offerIdGen,"offerIdGen.txt");
    }

    public void restoreState() throws IOException, ClassNotFoundException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        admin = serializationHelper.retrieveObject("admin.txt");
        users = serializationHelper.retrieveObject("users.txt");
        departments = serializationHelper.retrieveObject("departments.txt");
        isFirstTime = serializationHelper.retrieveObject("isFirstTime.txt");
        User.setIdGen(serializationHelper.retrieveObject("userIdGen.txt"));
        Department.setIdGen(serializationHelper.retrieveObject("deptIdGen.txt"));
        Letter.setIdGen(serializationHelper.retrieveObject("letterIdGen.txt"));
        Company.setIdGen(serializationHelper.retrieveObject("companyIdGen.txt"));
        Offer.setIdGen(serializationHelper.retrieveObject("offerIdGen.txt"));
    }
}
