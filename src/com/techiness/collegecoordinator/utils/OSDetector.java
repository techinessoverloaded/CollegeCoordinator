package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.enums.OSType;

public final class OSDetector
{
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static OSType getCurrentOS()
    {
        if(OS.contains("mac"))
            return OSType.MACOS;

        else if(OS.contains("win"))
            return OSType.WINDOWS;

        else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
            return OSType.UNIX_LINUX;

        else
            return OSType.UNKNOWN;
    }
}
