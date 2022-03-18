package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.SerializationHelper;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountsManager implements Serializable
{
    private static AccountsManager instance = null;
    private Map<String, Department> departments = null;
    private Map<String, User> users = null;
    private UserType userType = null;
    private User currentUser = null;
    private Admin admin = null;
    private boolean isFirstTime = true;

    private AccountsManager()
    {
        try
        {
            retrieveData();
        }
        catch (Exception e)
        {
            if(users == null)
                users = new HashMap<>();
            if(departments == null)
                departments = new HashMap<>();
            println("User Data Lost/Not obtained Unfortunately!!!");
            println("The Application may behave like opening for the first time...");
            println();
        }
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

    public UserType getUserType()
    {
        return userType;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    public User getCurrentUser()
    {
        return currentUser;
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

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
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
        if(users.containsKey(user.getId()))
            return null;
        users.put(user.getId(),user);
        return user.getId();
    }

    public String registerAdmin(Admin admin)
    {
        if(this.admin != null)
            return null;
        this.admin = admin;
        users.put(admin.getId(),admin);
        return admin.getId();
    }

    public boolean loginAdmin(String adminId, String adminPassword)
    {
        if(this.admin == null)
            return false;
        currentUser = admin;
        userType = UserType.ADMIN;
        return true;
    }

    public boolean loginUser(String userId, String password)
    {
        String deptId = userId.substring(userId.indexOf('#')+1,userId.indexOf('_'));
        UserType currentUserType = UserType.valueOf(userId.substring(userId.indexOf('_')+1));
        Department department = departments.get(deptId);
        User reference;
        if(department == null)
            return false;
        switch (currentUserType)
        {
            case STUDENT:
                if(!department.getStudents().containsKey(userId) || department.getStudents().get(userId)==null)
                    return false;
                reference = department.getStudents().get(userId);
                break;
            case FACULTY:
                if(!department.getFaculties().containsKey(userId) || department.getFaculties().get(userId)==null)
                    return false;
                reference = department.getFaculties().get(userId);
                break;
            case HOD:
                if(department.getHod() == null || !(department instanceof CourseDepartment))
                    return false;
                reference = department.getHod();
                break;
            case TRAINING_HEAD:
                if(!(department instanceof PlacementDepartment) || department.getHod() == null)
                    return false;
                reference = department.getHod();
                break;
            default:
                return false;
        }
        if(reference == null)
            return false;
        if(password.equals(reference.getPassword()))
        {
            currentUser = reference;
            userType = UserType.valueOf(userId.substring(userId.indexOf("_")+1));
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean logoutUser()
    {
        if(currentUser==null)
            return false;
        currentUser = null;
        userType = null;
        return true;
    }

    public boolean noAdminAvailable()
    {
        return admin == null;
    }

    public void persistData() throws IOException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        serializationHelper.persistObject(admin,"admin.txt");
        serializationHelper.persistObject(isFirstTime,"isFirstTime.txt");
        serializationHelper.persistObject((HashMap<String,User>)users,"users.txt");
        serializationHelper.persistObject((HashMap<String,Department>)departments,"departments.txt");
    }

    private void retrieveData() throws IOException, ClassNotFoundException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        admin = serializationHelper.retrieveObject("admin.txt");
        users = serializationHelper.retrieveObject("users.txt");
        departments = serializationHelper.retrieveObject("departments.txt");
        isFirstTime = serializationHelper.retrieveObject("isFirstTime.txt");
    }
}
