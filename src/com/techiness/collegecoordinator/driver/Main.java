package com.techiness.collegecoordinator.driver;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.Admin;
import com.techiness.collegecoordinator.concrete.LoginManager;
import com.techiness.collegecoordinator.concrete.Student;
import com.techiness.collegecoordinator.enums.Gender;
import com.techiness.collegecoordinator.enums.LetterType;
import com.techiness.collegecoordinator.helpers.InputDataValidation;
import com.techiness.collegecoordinator.helpers.Letter;
import com.techiness.collegecoordinator.helpers.Offer;
import com.techiness.collegecoordinator.helpers.SerializationHelper;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{
    private static Scanner scanner = null;
    private static Console console = null;
    private static String name = "", email = "", password = "", phone = "";
    private static Gender gender = null;
    private static int age = -1, genderChoice = -1;
    public static void println(Object val)
    {
        System.out.println(val);
    }

    public static void println()
    {
        System.out.println();
    }

    public static void print(Object val)
    {
        System.out.print(val);
    }

    public static String readLine()
    {
        return scanner.nextLine().trim();
    }

    public static int readInt()
    {
        return scanner.nextInt();
    }

    public static double readDouble()
    {
        return scanner.nextDouble();
    }

    public static void printSymbols(char symbol, int count)
    {
        /*
         while(count--!=0)
            print(symbol);
         */
        Stream.generate(()->symbol).limit(count).forEach(System.out::print);
    }

    public static void printTextWithinStarPattern(String text)
    {
        printSymbols('*',14);
        print(" "+text+" ");
        printSymbols('*',14);
        println();
        printSymbols('*',30+text.length());
        println();
        println();
    }

    public static String readPassword()
    {
        return console!=null ? String.valueOf(console.readPassword()) : readLine();
    }

    public static void resetBasicData()
    {
        name = email = phone = password = "";
        gender = null;
        age = genderChoice = -1;
    }

    public static void getBasicDataOfUser()
    {
        resetBasicData();
        while(!InputDataValidation.validateName(name))
        {
            println("Enter your name:");
            name = readLine();
            if(!InputDataValidation.validateName(name))
                println("Please enter your name to proceed...");
        }
        println();
        while(!InputDataValidation.validateAge(age))
        {
            println("Enter your age:");
            age = readInt();
            if(!InputDataValidation.validateAge(age))
                println("Invalid age ! Enter the actual age !");
        }
        println();
        while(gender==null)
        {
            println("Select your Gender (1-3): \n 1. MALE \n 2. FEMALE \n 3. OTHER");
            genderChoice = readInt();
            switch (genderChoice)
            {
                case 1:
                    gender = Gender.MALE;
                    break;
                case 2:
                    gender = Gender.FEMALE;
                    break;
                case 3:
                    gender = Gender.OTHER;
                    break;
                default:
                    gender = null;
                    println("Invalid Choice ! Enter the right choice...");
                    break;
            }
        }
        readLine();
        println();
        while(!InputDataValidation.validatePhone(phone))
        {
            println("Enter your phone number:");
            phone = readLine();
            if(!InputDataValidation.validatePhone(phone))
                println("Invalid Phone Number ! Enter phone number properly !");
        }
        println();
        while(!InputDataValidation.validateEmail(email))
        {
            println("Enter your email:");
            email = readLine();
            if(!InputDataValidation.validateEmail(email))
                println("Invalid Email ID ! Enter Email ID properly !");
        }
        println();
        while(!InputDataValidation.validatePassword(password))
        {
            println("Criteria for password : ");
            println("Password must contain at least one digit [0-9].\n" +
                    "Password must contain at least one lowercase character [a-z].\n" +
                    "Password must contain at least one uppercase character [A-Z].\n" +
                    "Password must contain at least one special character like ! @ # & ( ).\n" +
                    "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            println();
            println("Enter your desired password:");
            password = readPassword();
            if(!InputDataValidation.validatePassword(password))
                println("Invalid Password ! Try entering a different password matching the given criteria !");
        }
    }
    public static void main(String args[]) throws IOException,ClassNotFoundException
    {
        scanner = new Scanner(System.in);
        console = System.console();
        LoginManager loginManager = LoginManager.getInstance();
        printTextWithinStarPattern("Welcome to CollegeCoordinator !");
        if(loginManager.getUsers().isEmpty()||loginManager.noAdminAvailable())
        {
            println("You have to create an Admin account to proceed further....");
            println();
            getBasicDataOfUser();
            Admin admin = new Admin(name, age, gender, phone, email, password, new HashMap<>());
            loginManager.getUsers().put(admin.getId(),admin);
            println("\nAdmin account created successfully with User ID: "+admin.getId());
            println("\nAccount details are as follows:\n\n"+admin);
            println("\nNOTE: You will require your User ID and Password for Login !");
        }
       //manager.setUsers(SerializationHelper.getInstance().retrieveObject("users.txt"));
       //System.out.println(manager.getUsers().toString());
       /*Runtime.getRuntime().addShutdownHook(new Thread(() ->
       {
           try
           {
               SerializationHelper.getInstance().persistObject(manager.getUsers(),"users.txt");
           }
           catch (IOException ioException)

           {
               ioException.printStackTrace();
           }
           }));
       }*/
}
}
