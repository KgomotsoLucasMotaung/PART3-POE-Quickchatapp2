/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchtapp2;

/**
 *
 * @author USER
 */

import javax.swing.JOptionPane;
import java.util.regex.Pattern;

public class Login {

    private String username;

    private String password;

    private String cellPhoneNumber;


    private String registeredUsername;

    private String registeredPassword;

    private String registeredCellPhoneNumber;


    public Login() {

        this.username = "";

        this.password = "";

        this.cellPhoneNumber = "";

        this.registeredUsername = "";

        this.registeredPassword = "";

        this.registeredCellPhoneNumber = "";

    }


    public boolean checkUserName(String username) {

        if (username == null) return false;

        return username.contains("_") && username.length() <= 5;

    }


    public boolean checkPasswordComplexity(String password) {

        if (password == null) return false;

        if (password.length() < 8) return false;

        if (!Pattern.compile("[A-Z]").matcher(password).find()) return false;

        if (!Pattern.compile("[0-9]").matcher(password).find()) return false;

        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) return false;

        return true;

    }


    public boolean checkCellPhoneNumber(String cellPhoneNumber) {

        if (cellPhoneNumber == null) return false;

        String regex = "^\\+\\d{1,3}\\d{7,10}$";

        return Pattern.matches(regex, cellPhoneNumber);

    }


    public String registerUser(String username, String password, String cellPhoneNumber) {

        boolean usernameValid = checkUserName(username);

        boolean passwordValid = checkPasswordComplexity(password);

        boolean cellValid = checkCellPhoneNumber(cellPhoneNumber);


        if (!usernameValid) {

            JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");

            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";

        }

        if (!passwordValid) {

            JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        }

        if (!cellValid) {

            JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.");

            return "Cell phone number incorrectly formatted or does not contain international code.";

        }


        this.registeredUsername = username;

        this.registeredPassword = password;

        this.registeredCellPhoneNumber = cellPhoneNumber;


        JOptionPane.showMessageDialog(null, "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.");

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";

    }


    public boolean loginUser(String username, String password) {

        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword);

    }


    public String returnLoginStatus(boolean loginSuccess, String firstName, String lastName) {

        if (loginSuccess) {

            return "Welcome " + firstName + " ," + lastName + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";

        }

    }

}

