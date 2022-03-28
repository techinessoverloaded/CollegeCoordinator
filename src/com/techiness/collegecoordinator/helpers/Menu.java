package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.User;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.techiness.collegecoordinator.consoleui.IOUtils.*;

/**
 * A Helper Class for building Menu for each Type of {@link User}. Serial Number for option is generated automatically.
 * Instance of this Class is built using {@link MenuBuilder} Class.
 */
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

    /**
     * @return Options {@link Map} Object.
     */
    public Map<Integer, String> getOptions()
    {
        return options;
    }

    /**
     * @param options The {@link Map} Object to be set.
     */
    public void setOptions(Map<Integer, String> options)
    {
        this.options = options;
        this.totalChars = 40+options.values().stream().max(Comparator.comparingInt(String::length)).get().length();
    }

    /**
     * Used to add an Option to the Existing Options. The serial number of the Option is generated internally.
     * @param option The {@link String} to be added.
     */
    public void addOption(String option)
    {
        options.put(options.size()+1,option);
    }

    /**
     * Used to remove an option using its Index.
     * @param index The {@link Integer} index of the {@link String} option to be removed.
     */
    public void removeOption(int index)
    {
        options.remove(index);
    }

    /**
     * @return The {@link String} header of the Menu.
     */
    public String getHeader()
    {
        return header;
    }

    /**
     * Used to set the header of the Menu.
     * @param header The {@link String} header to be set.
     */
    public void setHeader(String header)
    {
        this.header = header;
    }

    /**
     * Returns the index of the given header in the options.
     * @param option The {@link String} option whose index is to be found.
     * @return The {@link Integer} index of the given option.
     */
    public int indexOf(String option)
    {
        return options.entrySet().stream().filter(entry -> entry.getValue().equals(option)).collect(Collectors.toList()).get(0).getKey();
    }

    public void extendMenu(Menu extensionMenu)
    {
        this.header = extensionMenu.header;
        extensionMenu.getOptions().values().forEach(this::addOption);
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
        return selectedChoice;
    }

    /**
     * A Builder Class for {@link Menu} based on Builder Pattern. It is used to construct the {@link Menu} Object step by step.
     */
    public static class MenuBuilder
    {
        private Map<Integer,String> options;
        private String header;

        /**
         * Simple Constructor for instantiating the {@link MenuBuilder} Class.
         */
        public MenuBuilder()
        {
            options = new HashMap<>();
            header = "";
        }

        /**
         * Alternate Constructor for instantiating the {@link MenuBuilder} Class.
         * Takes an existing {@link Menu} object as a parameter whose options are copied to the new object.
         * @param existingMenu The existing {@link Menu} object whose options are copied to the new object.
         */
        public MenuBuilder(Menu existingMenu)
        {
            this.options = existingMenu.options;
            header = "";
        }

        /**
         * Used to set Header for the {@link Menu} object.
         * @param header The {@link String} to be set as {@link Menu} Header.
         * @return The same {@link MenuBuilder} instance itself.
         */
        public MenuBuilder setHeader(String header)
        {
            this.header = header;
            return this;
        }

        /**
         * Used to add an option to the {@link Menu}.
         * @param option The {@link String} option to be added.
         * @return The same {@link MenuBuilder} instance itself.
         */
        public MenuBuilder addOption(String option)
        {
            this.options.put(options.size()+1,option);
            return this;
        }

        /**
         * Used to remove an option from the {@link Menu}.
         * @param index The {@link Integer} index of the Option to be removed.
         * @return The same {@link MenuBuilder} instance itself.
         */
        public MenuBuilder removeOption(int index)
        {
            this.options.remove(index);
            return this;
        }

        /**
         * Used to build the {@link Menu} object with the set Header and set Options.
         * @return null, if header or options is not set, else {@link Menu} Object.
         */
        public Menu build()
        {
            return this.options.size() == 0 || this.header.equals("") ? null : new Menu(this.header,this.options);
        }
    }
}
