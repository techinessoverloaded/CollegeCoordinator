package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.SerializationHelper;
import static com.techiness.collegecoordinator.driver.Main.println;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AccountsManager implements Serializable
{
    private static AccountsManager instance = null;
    private Map<String,User> users = null;
    private Map<String, Department> departments = null;
    private UserType userType = null;
    private User currentUser = null;
    private boolean isFirstTime = true;

    private AccountsManager()
    {
        try
        {
            retrieveData();
        }
        catch (Exception e)
        {
            println("User Data Lost/Not obtained Unfortunately!!!");
            println("The Application may behave like opening for the first time...");
            println();
            users = new HashMap<>();
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

    public Map<String,User> getUsers()
    {
        return users;
    }

    public void setUsers(Map<String, User> users)
    {
        this.users = users;
    }

    public Map<String, Department> getDepartments()
    {
        return departments;
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
        if(users.containsKey(user.getId()))
            return null;
        users.put(user.getId(),user);
        return user.getId();
    }

    public boolean loginUser(String userId, String password)
    {
        if(!users.containsKey(userId)||users.get(userId)==null)
            return false;
        User reference = users.get(userId);
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
        for(User user : users.values())
        {
            if(user instanceof Admin)
                return false;
        }
        return true;
    }

    public void persistData() throws IOException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        serializationHelper.persistObject(users,"users.txt");
        serializationHelper.persistObject(isFirstTime,"isFirstTime.txt");
        serializationHelper.persistObject(departments,"departments.txt");
    }

    private void retrieveData() throws IOException, ClassNotFoundException
    {
        SerializationHelper serializationHelper = SerializationHelper.getInstance();
        users = serializationHelper.retrieveObject("users.txt");
        departments = serializationHelper.retrieveObject("departments.txt");
        isFirstTime = serializationHelper.retrieveObject("isFirstTime.txt");
    }
}
