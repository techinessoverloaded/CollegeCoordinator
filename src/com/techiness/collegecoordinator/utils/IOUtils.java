package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.*;
import com.techiness.collegecoordinator.enums.RequestLetterType;
import com.techiness.collegecoordinator.managers.AccountsManager;
import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public static void printlnVal(Object val)
    {
        println("\n"+val);
    }

    public static void printlnValLn(Object val)
    {
        printlnVal(val+"\n");
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
        return val < 0 ? -1 : val;
    }

    public static double readDouble()
    {
        double val = -1;
        try
        {
            val = Double.parseDouble(readLine());
        }
        catch (NumberFormatException e)
        {
            println2("Invalid Input ! Input should be a positive decimal number !");
            return val;
        }
        return val;
    }

    public static void printSymbols(char symbol, int count)
    {
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
        char incomplete = '░';
        char complete = '█';
        StringBuilder builder = new StringBuilder();
        int count = 25;
        Stream.generate(() -> incomplete).limit(count).forEach(builder::append);
        printlnVal(text);
        for(int i = 0; i < count; i++)
        {
            builder.replace(i,i+1,String.valueOf(complete));
            String output = "\r"+builder;
            print(output);
            try
            {
                Thread.sleep(60);
            }
            catch (InterruptedException ignored)
            {

            }
        }
        println2();
    }

    public static void printAccountDetails(User user, boolean onCreation)
    {
        println();
        if(onCreation)
        {
            println2(StringToEnumUtils.getUserTypeFromUserId(user.getId()) + " Account created successfully with User ID: " + user.getId());
            println2("NOTE: You would need the User ID and Password for logging in next time!");
        }
        println2("\nAccount Details\n\n" + user);
    }

    public static void printDepartmentDetails(Department department, boolean onCreation)
    {
        println();
        if(onCreation)
        {
            println2(StringToEnumUtils.getDepartmentTypeFromDeptId(department.getId()) + " created successfully with Department ID: " + department.getId());
        }
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
        for(int i = 0; i < names.size(); ++i)
        {
            builder.append(ids.get(i)).append(" : ").append(names.get(i));
            if(i != names.size()-1)
                builder.append("\n");
        }
        return builder.toString();
    }

    public static <T extends Nameable> String getStringOfNameableMap(Map<String,T> map)
    {
        return getStringOfNameableCollection(map.values());
    }

    public static <T extends Identifiable> String getStringOfIdentifiableCollection(Collection<T> objects)
    {
        List<String> ids = objects.stream().map(T::getId).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < ids.size(); ++i)
        {
            builder.append(ids.get(i));
            if(i != ids.size()-1)
                builder.append("\n");
        }
        return builder.toString();
    }

    public static <T extends Identifiable> String getStringOfIdentifiableMap(Map<String,T> map)
    {
        return getStringOfIdentifiableCollection(map.values());
    }

    public static String[] getStringArrayOfStringSet(Set<String> set)
    {
        return set.stream().toArray(String[]::new);
    }

    public static <T> T getUserInput(T container, String message)
    {
        if(container instanceof Integer)
        {
            println("Enter the "+message+" :");
            container = (T)Integer.valueOf(readInt());
        }
        else if(container instanceof String)
        {
            println("Enter the "+message+" :");
            container = (T)readLine();
        }
        else if(container instanceof Double)
        {
            println("Enter the "+message+" :");
            container = (T)Double.valueOf(readDouble());
        }
        else if(container instanceof String[])
        {
            println("Enter the "+message+" :");
            container = (T) Arrays.stream(readLine().split(",")).map(String::trim).toArray(String[]::new);
        }
        return container;
    }

    public static String getPasswordInput(String message)
    {
        println("Enter the "+message+" :");
        return readPassword();
    }

    public static void hideFile(Path filePath)
    {
        try
        {
            DosFileAttributes attributes = Files.readAttributes(filePath, DosFileAttributes.class);
            if (!attributes.isHidden())
                Files.setAttribute(filePath, "dos:hidden", true);
        }
        catch (IOException ignored)
        {

        }
    }

    public static void unHideFile(Path filePath) throws IOException
    {
        DosFileAttributes attributes = Files.readAttributes(filePath,DosFileAttributes.class);
        if(attributes.isHidden())
            Files.setAttribute(filePath,"dos:hidden",false);
    }

    public static LocalDate convertDateToLocalDate(Date dateToBeConverted)
    {
        return Instant.ofEpochSecond(dateToBeConverted.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        //return dateToBeConverted.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static DateTimeFormatter getDateFormatter()
    {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static LocalDate parseStringWithDateFormatter(String dateString)
    {
        return LocalDate.parse(dateString, getDateFormatter());
    }

    public static String formatLocalDateWithDateFormatter(LocalDate localDate)
    {
        return localDate.format(getDateFormatter());
    }

    public static void printRequestLetter(RequestLetter requestLetter)
    {
        User requester = AccountsManager.getInstance().getUsers(requestLetter.getRequesterId());
        RequestLetterType requestLetterType = RequestLetterType.getLetterTypeFromInstance(requestLetter);
        printSymbols('-', 36);
        println("| Letter ID: "+requestLetter.getId()+" |");
        println("| Letter Type: "+requestLetterType+" |");
        println("| Requester Name: "+requester.getName()+" |");
        println("| Is Approved: "+requestLetter.getIsApproved()+" |");
        printSymbols('-', 36);
        println2();
    }
}
