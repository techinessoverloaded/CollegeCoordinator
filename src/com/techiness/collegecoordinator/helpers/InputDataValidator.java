package com.techiness.collegecoordinator.helpers;

import java.util.regex.Pattern;

public class InputDataValidator
{
    public static boolean validateName(String name)
    {
        return !name.isEmpty();
    }

    public static boolean validatePhone(String phone)
    {
        /*
        (0/91): number starts with (0/91)
        [6-9]: starting of the number may contain a digit between 6 and 9
        [0-9]: then contains digits 0 to 9
         */
        String phonePattern = "(0/91)?[6-9][0-9]{9}";
        return Pattern.compile(phonePattern).matcher(phone).matches();
    }

    public static boolean validateAge(int age)
    {
        return age>=18 && age<=100;
    }

    public static boolean validateEmail(String email)
    {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    public static boolean validatePassword(String password)
    {
        /*
        Password must contain at least one digit [0-9].
        Password must contain at least one lowercase character [a-z].
        Password must contain at least one uppercase character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters.
         */
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
