package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.UserType;
import java.util.HashMap;
import java.util.Map;


public class LoginManager
{
    private static LoginManager instance = null;
    private Map<String,User> users = null;
    private UserType userType = null;
    private User currentUser = null;

    private LoginManager()
    {
        users = new HashMap<>();
        userType = UserType.ADMIN;
    }

    //Singleton Pattern
    public synchronized static LoginManager getInstance()
    {
        if(instance == null)
            instance = new LoginManager();
        return instance;
    }

    public Map<String,User> getUsers()
    {
        return users;
    }

    public void setUsers(Map<String, User> users)
    {
        this.users = users;
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

    public boolean registerUser(User user)
    {
        if(users.containsKey(user.getId()))
            return false;
        users.put(user.getId(),user);
        return true;
    }

    public boolean loginUser(String userId, String password)
    {
        if(!users.containsKey(userId)||users.get(userId)==null)
            return false;
        User reference = users.get(userId);
        if(password.equals(reference.getPassword()))
        {
            currentUser = reference;
            userType = UserType.valueOf(userId.substring(userId.indexOf('_')+1));
            return true;
        }
        else
        {
            return false;
        }
    }
}
