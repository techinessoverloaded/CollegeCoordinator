package com.techiness.collegecoordinator.abstraction;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import java.util.Observable;
import java.io.Serializable;

/**
 * The Basic Root Abstract class for the users of this application. Has basic details about the user.
 * Implements the {@link Nameable} interface and the {@link Comparable<User>} interface. Also, implements the {@link Serializable} interface and hence, can be serialized.
 * The subclasses of this class must override {@link #getId()}  and {@link #setId(String)} methods of the {@link Identifiable} interface.
 */
public abstract class User extends Observable implements Serializable, Nameable, Comparable<User>
{
    protected String id;
    protected String name;
    protected int age;
    protected Gender gender;
    protected String phone;
    protected String email;
    protected String password;

    public User(String name, int age, Gender gender, String phone, String email, String password)
    {
        this.id = String.valueOf(AccountsManager.getInstance().getUserIdGen());
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @Override
    public abstract String getId();

    @Override
    public abstract void setId(String id);

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
        setChanged();
        notifyObservers();
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
        setChanged();
        notifyObservers();
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
        setChanged();
        notifyObservers();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
        setChanged();
        notifyObservers();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
        setChanged();
        notifyObservers();
    }

    @Override
    public int compareTo(User o)
    {
        return getId().compareTo(o.getId());
    }


    @Override
    public String toString()
    {
        return " [ \nid = "+getId()+", \nname = "+name+", \nage = "+age+", \ngender = "+gender+", \nphone = "+phone+", \nemail = "+email;
    }
}
