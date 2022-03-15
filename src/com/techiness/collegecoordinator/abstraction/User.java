package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.enums.Gender;
import java.io.Serializable;

public abstract class User implements Serializable
{
    protected static int idGen = 1;
    protected String id;
    protected String name;
    protected int age;
    protected Gender gender;
    protected String phone;
    protected String email;
    protected String password;

    public User(String name, int age, Gender gender, String phone, String email, String password)
    {
        this.id = String.valueOf(idGen);
        idGen += 1;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public abstract String getId();

    public abstract void setId(String id);

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return " [ \nid = "+getId()+", \nname = "+name+", \nage = "+age+", \ngender = "+gender+", \nphone = "+phone+", \nemail = "+email;
    }
}
