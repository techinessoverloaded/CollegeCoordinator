package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.Identifiable;
import com.techiness.collegecoordinator.abstraction.Nameable;
import com.techiness.collegecoordinator.abstraction.User;
import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IOUtils
{
    public static final Scanner scanner = new Scanner(System.in);
    public static final Console console = System.console();

    public static void println(Object val)
    {
        System.out.println(val);
    }

    public static void println()
    {
        System.out.println();
    }

    public static void println2()
    {
        println("\n");
    }

    public static void println2(Object val)
    {
        println(val+"\n");
    }

    public static void print(Object val) {
        System.out.print(val);
    }

    public static String readLine()
    {
        return scanner.nextLine().trim();
    }

    public static int readInt()
    {
        int val = -1;
        try
        {
            val = Integer.parseInt(readLine());
        }
        catch (NumberFormatException e)
        {
            println2("Invalid Input ! Input should be a positive number !");
            return val;
        }
        return val;
    }

    public static double readDouble() {
        return scanner.nextDouble();
    }

    public static void printSymbols(char symbol, int count)
    {
        /*
         while(count--!=0)
            print(symbol);
         */
        Stream.generate(() -> symbol).limit(count).forEach(System.out::print);
    }

    public static void printTextWithinStarPattern(String text)
    {
        printSymbols('*', 14);
        print(" " + text + " ");
        printSymbols('*', 14);
        println();
        printSymbols('*', 30 + text.length());
        println2();
    }

    public static void printlnWithAnim(String text)
    {
        for (char c : text.toCharArray())
        {
            print(c);
            try
            {
                Thread.sleep(205);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        println();
    }

    public static void printAccountCreationSuccess(User user)
    {
        println();
        println2(user.getId().substring(user.getId().indexOf('_') + 1) + " Account created successfully with User ID: " + user.getId());
        println2("\nAccount Details:\n\n" + user);
        println("NOTE: You would need your User ID and Password for logging in next time!\n");
    }

    public static void printDepartmentCreationSuccess(Department department)
    {
        println();
        println2(department.getId().substring(department.getId().indexOf('@')+1) + " created successfully with Department ID: " + department.getId());
        println2("\nDepartment Details:\n\n" + department);
    }

    public static String readPassword()
    {
        return console != null ? String.valueOf(console.readPassword()) : readLine();
    }

    public static <T extends Nameable> String getStringOfNameableCollection(Collection<T> objects)
    {
        List<String> names = objects.stream().map(Nameable::getName).collect(Collectors.toList());
        List<String> ids = objects.stream().map(Nameable::getId).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for(int i = 0; i < names.size(); ++i)
        {
            builder.append(ids.get(i)).append(" : ").append(names.get(i));
            if(i != names.size()-1)
                builder.append(", ");
        }
        builder.append(" ]");
        return builder.toString();
    }

    public static <T extends Nameable> String getStringOfNameableMap(Map<String,T> map)
    {
        return getStringOfNameableCollection(map.values());
    }

    public static <T extends Identifiable> String getStringOfIdentifiableCollection(Collection<T> objects)
    {
        List<String> ids = objects.stream().map(T::getId).collect(Collectors.toList());
        return ids.toString();
    }

    public static <T extends Identifiable> String getStringOfIdentifiableMap(Map<String,T> map)
    {
        return getStringOfIdentifiableCollection(map.values());
    }
}
