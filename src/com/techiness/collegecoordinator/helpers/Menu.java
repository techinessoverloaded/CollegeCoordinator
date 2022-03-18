package com.techiness.collegecoordinator.helpers;

import java.util.HashMap;
import java.util.Map;
import static com.techiness.collegecoordinator.helpers.IOUtils.*;

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
        this.totalChars = 38+header.length();
    }

    public Map<Integer, String> getOptions()
    {
        return options;
    }

    public void setOptions(Map<Integer, String> options)
    {
        this.options = options;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
        totalChars = 38+header.length();
    }

    private void displayHeader()
    {
        printSymbols('-',totalChars);
        println();
        print("|");
        printSymbols(' ',18);
        print(header+" ");
        printSymbols(' ',17);
        print("|\n");
        printSymbols('-',totalChars);
        println();
    }

    public int displayMenuAndGetChoice()
    {
        selectedChoice = -1;
        displayHeader();
        for(Integer key : options.keySet())
        {
            print("|  "+key+". "+options.get(key));
            printSymbols(' ', totalChars - (options.get(key)).length() - ((key>9) ? 8 : 7));
            print("|");
            println();
        }
        printSymbols('-',totalChars);
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

        public MenuBuilder removeOption(int number)
        {
            this.options.remove(number);
            return this;
        }

        public Menu build()
        {
            return new Menu(this.header,this.options);
        }
    }
}
