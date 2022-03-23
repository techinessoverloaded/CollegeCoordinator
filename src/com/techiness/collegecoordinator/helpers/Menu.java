package com.techiness.collegecoordinator.helpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

public class Menu
{
    private Map<Integer,String> options;
    private String header;
    private int selectedChoice = -1;
    private int totalChars = 0;

    private Menu(String header, Map<Integer, String> options)
    {
        this.options = options;
        this.header = header;
        this.totalChars = 40+options.values().stream().max(Comparator.comparingInt(String::length)).get().length();
    }

    public Map<Integer, String> getOptions()
    {
        return options;
    }

    public void setOptions(Map<Integer, String> options)
    {
        this.options = options;
        this.totalChars = 40+options.values().stream().max(Comparator.comparingInt(String::length)).get().length();
    }

    public void addOption(String option)
    {
        options.put(options.size()+1,option);
    }

    public void removeOption(int index)
    {
        options.remove(index);
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public void extendMenu(Menu existingMenu)
    {
        this.header = existingMenu.header;
        existingMenu.getOptions().values().forEach(this::addOption);
    }

    private void displayHeader()
    {
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println();
        print("|");
        int spaces = header.length()%2==0 ? (totalChars-header.length())/2 : (totalChars-header.length()+1)/2;
        printSymbols(' ',spaces-1);
        print(header);
        printSymbols(' ',spaces-1);
        println("|");
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println();
    }

    public int displayMenuAndGetChoice()
    {
        selectedChoice = -1;
        totalChars = 40+options.values().stream().max(Comparator.comparingInt(String::length)).get().length();
        displayHeader();
        for(Integer key : options.keySet())
        {
            print("|  "+key+". "+options.get(key));
            printSymbols(' ', totalChars - (options.get(key)).length() - ((key>9) ? 8 : 7));
            print("|");
            println();
        }
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println('\n');
        println("Choose an option (1-" + options.size() + ") from the above options to proceed:");
        selectedChoice = readInt();
        readLine();
        return selectedChoice;
    }


    public static class MenuBuilder
    {
        private Map<Integer,String> options;
        private String header;

        public MenuBuilder()
        {
            options = new HashMap<>();
            header = "";
        }

        public MenuBuilder(Menu existingMenu)
        {
            this.options = existingMenu.options;
            header = "";
        }

        public MenuBuilder setHeader(String header)
        {
            this.header = header;
            return this;
        }

        public MenuBuilder addOption(String option)
        {
            this.options.put(options.size()+1,option);
            return this;
        }

        public MenuBuilder removeOption(int index)
        {
            this.options.remove(index);
            return this;
        }

        public Menu build()
        {
            return new Menu(this.header,this.options);
        }
    }
}
