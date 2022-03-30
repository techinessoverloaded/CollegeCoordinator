package com.techiness.collegecoordinator.helpers;

import java.util.regex.Pattern;

public final class InputDataValidator
{
    public static boolean validateName(String name, String... existingName)
    {
        boolean result = !name.isEmpty() && name.matches("[a-zA-Z]+");;
        if(existingName.length == 1)
            result = result && !name.equals(existingName[0]);
        return result;
    }

    public static boolean validatePhone(String phone, String... existingPhone)
    {
        /*
        (0/91): number starts with (0/91)
        [6-9]: starting of the number may contain a digit between 6 and 9
        [0-9]: then contains digits 0 to 9
         */
        String phonePattern = "(0/91)?[6-9][0-9]{9}";
        boolean result = Pattern.compile(phonePattern).matcher(phone).matches();
        if(existingPhone.length == 1)
            result = result && !phone.equals(existingPhone[0]);
        return result;
    }

    public static boolean validateAge(int age, int... existingAge)
    {
        boolean result = age>=18 && age<=100;
        if(existingAge.length == 1)
            result = result && age != existingAge[0];
        return result;
    }

    public static boolean validateEmail(String email, String... existingEmail)
    {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean result = Pattern.compile(emailPattern).matcher(email).matches();
        if(existingEmail.length == 1)
            result = result && !email.equals(existingEmail[0]);
        return result;
    }

    public static boolean validatePassword(String password, boolean confirmPassword, String... existingPassword)
    {
        /*
        Password must contain at least one digit [0-9].
        Password must contain at least one lowercase character [a-z].
        Password must contain at least one uppercase character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters.
         */
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        boolean result = Pattern.compile(passwordPattern).matcher(password).matches();
        if(existingPassword.length == 1 && !confirmPassword)
            result = result && !password.equals(existingPassword[0]);
        else if(existingPassword.length == 1)
            result = result && password.equals(existingPassword[0]);
        return result;
    }
}
