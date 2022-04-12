package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.User;
import com.techiness.collegecoordinator.enums.Gender;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static com.techiness.collegecoordinator.utils.IOUtils.*;

/**
 * A Helper Class for building Menu for each Type of {@link User}. Serial Number for option is generated automatically.
 * Instance of this Class is built using {@link MenuBuilder} Class.
 */
public final class Menu
{
    private Map<Integer,String> options;
    private String header;
    private int selectedChoice = -1;
    private int totalChars = 0;

    private Menu(String header, Map<Integer, String> options)
    {
        this.options = options;
        this.header = header;
        recalculateTotalChars();
    }

    /**
     * @return Options {@link Map} Object.
     */
    public Map<Integer, String> getOptions()
    {
        return options;
    }

    /**
     * Alternate method of {@link #getOptions()} Method.
     * @param index The {@link Integer} index of the Option to be retrieved.
     * @return The {@link String} option present at the given index.
     */
    public String getOptions(int index)
    {
        return options.get(index);
    }

    /**
     * @param options The {@link Map} Object to be set.
     */
    public void setOptions(Map<Integer, String> options)
    {
        this.options = options;
        recalculateTotalChars();
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
     * Used to add multiple {@link String} Options to the {@link Menu}
     * @param multipleOptions The VarArgs {@link String} parameter.
     */
    public void addMultipleOptions(String... multipleOptions)
    {
        if(multipleOptions.length == 0)
            return;
        Arrays.stream(multipleOptions).forEach(this::addOption);
    }

    /**
     * Used to remove an option using its Index.
     * @param index The {@link Integer} index of the {@link String} option to be removed.
     */
    public void removeOption(int index)
    {
        options.remove(index);
        reallocateIndexes();
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

    /**
     * Used to check if a {@link String} Option is present already in the Options.
     * @param option The {@link String} Option whose presence is to be checked.
     * @return {@code true} if the Option is present, else {@code false}.
     */
    public boolean contains(String option)
    {
        return options.containsValue(option);
    }

    /**
     * Used to extend the current Options with the extension {@link Menu}'s Options and also sets its Header as this {@link Menu}'s Header.
     * @param extensionMenu The {@link Menu} Object whose Options are to be extended.
     */
    public void extendMenu(Menu extensionMenu)
    {
        this.header = extensionMenu.header;
        extensionMenu.getOptions().values().forEach(this::addOption);
    }

    private void reallocateIndexes()
    {
        Map<Integer, String> oldMap = options;
        options = new HashMap<>();
        oldMap.values().forEach(this::addOption);
    }

    private void displayHeader()
    {
        recalculateTotalChars();
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println();
        print("|");
        int spaces = getSpaces();
        printSymbols(' ',spaces-1);
        print(header);
        printSymbols(' ',spaces);
        println("|");
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println();
    }

    /**
     * Used to display the {@link Menu}'s Options and get {@link Integer} choice from the User.
     * @return The {@link Integer} choice entered by the User. If the choice entered is out of bounds, {@code -1} is returned.
     */
    public int displayMenuAndGetChoice()
    {
        selectedChoice = -1;
        displayHeader();
        options.forEach((key, value) -> {
            print("|  " + key + ". " + value);
            printSymbols(' ', totalChars - (value).length() - ((key > 9) ? 8 : 7));
            print("|");
            println();
        });
        print("+");
        printSymbols('-',totalChars-2);
        print("+");
        println2();
        println("Choose an option (1-" + options.size() + ") from the above options to proceed:");
        selectedChoice = readInt();
        return selectedChoice > options.size()+1 ? -1 : selectedChoice;
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
         * Used to add multiple {@link String} Options to the {@link Menu}.
         * @param multipleOptions The VarArgs {@link String} parameter.
         * @return The same {@link MenuBuilder} instance itself.
         */
        public MenuBuilder addMultipleOptions(String... multipleOptions)
        {
            if(multipleOptions.length == 0)
                return this;
            Arrays.stream(multipleOptions).forEach(this::addOption);
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

    /**
     * Utility Function to get a bi-value Yes/No {@link Menu}. When called from this {@link Menu},
     * {@link Menu#displayMenuAndGetChoice()} will return {@code 1} representing {@code Yes}, or {@code 2} representing {@code No}, or {@code -1} representing invalid input.
     * @return The {@link Menu} object built with Yes/No options.
     */
    public static Menu getYesOrNoMenu()
    {
        return new MenuBuilder().setHeader("Select Yes/No")
                .addOption("Yes")
                .addOption("No")
                .build();
    }

    public static Menu getGenderSelectionMenu()
    {
        return new MenuBuilder().setHeader("Gender Menu")
                .addMultipleOptions(Gender.getStringArrayOfValues())
                .build();
    }

    private void recalculateTotalChars()
    {
        totalChars = 40 + options.values().stream().max(Comparator.comparingInt(String::length)).get().length();
        totalChars = totalChars % 2 == 1 ? totalChars+1 : totalChars;
    }

    public int getTotalChars()
    {
        return totalChars;
    }

    private int getSpaces()
    {
        int spaces = 0;
        int hLength = header.length();
        hLength = hLength % 2 == 1 ? hLength+1 : hLength;
        return (totalChars-hLength)/2;
    }
}
