package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Department;
import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.concrete.AccountsManager;
import java.io.Console;
import java.util.Scanner;
import java.util.stream.Stream;

public class IOUtils
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final Console console = System.console();

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

    public static void printSymbols(char symbol, int count) {
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
                Thread.sleep(230);
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
        printlnWithAnim("Logging in now...");
        AccountsManager.getInstance().loginUser(user.getId(), user.getPassword());
        println();
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
}
