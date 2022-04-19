package com.techiness.collegecoordinator.managers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.concrete.PlacementDepartment;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.utils.SerializationHelper;
import com.techiness.collegecoordinator.utils.StringToEnumUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class AccountsManager implements Serializable
{
    private static AccountsManager instance = null;
    private Map<String, Department> departments = null;
    private Map<String, User> users = null;
    private Admin admin = null;


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

    public int getUserIdGen()
    {
        int userIdGen = users.size()+1;
        while (checkIfUserIdExists(userIdGen))
        {
            ++userIdGen;
        }
        return userIdGen;
    }

    private boolean checkIfUserIdExists(int userIdToBeChecked)
    {
        return users.keySet().stream().mapToInt(id-> Integer.parseInt(id.substring(0,1))).anyMatch(i-> i == userIdToBeChecked);
    }


    public int getDepartmentIdGen()
    {
        int deptIdGen = departments.size()+1;
        while (checkIfDeptIdExists(deptIdGen))
        {
            ++deptIdGen;
        }
        return deptIdGen;
    }

    private boolean checkIfDeptIdExists(int deptIdToBeChecked)
    {
        return departments.keySet().stream().mapToInt(id-> Integer.parseInt(id.substring(0,1))).anyMatch(i-> i == deptIdToBeChecked);
    }

    public boolean checkIfDeptIdExists(String deptIdToBeChecked)
    {
        return departments.containsKey(deptIdToBeChecked);
    }

    public Map<String, Department> getDepartments()
    {
        return departments;
    }

    public Department getDepartments(String deptId)
    {
        return !departments.containsKey(deptId) || departments.get(deptId) == null ? null : departments.get(deptId);
    }

    public PlacementDepartment getPlacementDepartment()
    {
        return (PlacementDepartment) departments.values().stream().filter(dept -> dept instanceof PlacementDepartment).limit(1).collect(Collectors.toList()).get(0);
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

    public String registerUser(User user)
    {
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
        serializationHelper.persistObject(SessionManager.getInstance().isFirstTime(), "isFirstTime.txt");
        serializationHelper.persistObject((HashMap<String,User>)users,"users.txt");
        serializationHelper.persistObject((HashMap<String,Department>)departments,"departments.txt");
    }

    public void restoreState() throws IOException, ClassNotFoundException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        admin = serializationHelper.retrieveObject("admin.txt");
        users = serializationHelper.retrieveObject("users.txt");
        departments = serializationHelper.retrieveObject("departments.txt");
        SessionManager.getInstance().setFirstTime(serializationHelper.retrieveObject("isFirstTime.txt"));
    }
}