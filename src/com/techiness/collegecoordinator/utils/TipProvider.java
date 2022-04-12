package com.techiness.collegecoordinator.utils;

import java.util.Random;

public class TipProvider
{
    private static String[] tips =  new String[] {
            "Do not terminate the Application abruptly as it might result in loss of Data ! Use Exit Application Option from the Main Menu !",
            "IDs are used widely in this Application. Make sure to note down the IDs properly or use the Appropriate Display options to get available IDs !",
            "Make "
    };

    public static String getRandomTip()
    {
        return tips[new Random().nextInt(3)-1];
    }
}
