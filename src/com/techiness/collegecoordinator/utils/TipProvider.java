package com.techiness.collegecoordinator.utils;

import java.util.Random;
import static com.techiness.collegecoordinator.utils.IOUtils.getStringWithinBox;

public class TipProvider
{
    private final static String[] tips =  new String[] {
            "Do not terminate the Application abruptly as it might result in loss of Data ! Use Exit Application Option from the Main Menu !",
            "IDs are used widely in this Application. Make sure to note down the IDs properly or use the Appropriate Display options to get available IDs !",
            "Some operations are not cancelable. Make sure to select an operation on the basis of necessity alone !",
            "Make sure to note down your User ID and Password as soon as the Account is created !"
    };

    public static String getRandomTip()
    {
        return getStringWithinBox("TIP: "+tips[new Random().nextInt(4)]);
    }
}
