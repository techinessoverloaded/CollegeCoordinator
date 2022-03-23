package com.techiness.collegecoordinator.consoleui;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.Identifiable;
import com.techiness.collegecoordinator.abstraction.Nameable;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.AccountsManager;
import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOUtils
{
    public static final Scanner scanner = new Scanner(System.in);
    public static final Console console = System.console();

    public static void println(Object val) {
        System.out.println(val);
    }

    public static void println() {
        System.out.println();
    }

    public static void print(Object val) {
        System.out.print(val);
    }

    public static String readLine() {
        return scanner.nextLine().trim();
    }

    public static int readInt() {
        return scanner.nextInt();
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
        println();
        println();
    }

    public static void printlnWithAnim(String text)
    {
        for (char c : text.toCharArray())
        {
            print(c);
            try
            {
                Thread.sleep(210);
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
        println(user.getId().substring(user.getId().indexOf('_') + 1) + " Account created successfully with User ID: " + user.getId());
        println("\nAccount Details:\n\n" + user);
        println("NOTE: You would need your User ID and Password for logging in next time!\n");
    }

    public static void printDepartmentCreationSuccess(Department department)
    {
        println(department.getId().substring(department.getId().indexOf('@')+1) + " created successfully with Department ID: " + department.getId());
        println("\nDepartment Details:\n\n" + department);
        println();
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
