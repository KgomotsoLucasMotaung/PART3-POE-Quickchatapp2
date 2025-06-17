/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchatapp2;

/**
 *
 * @author USER
 */
import javax.swing.JOptionPane;

public class QuickChatApp2 {

    public static void main(String[] args) {

        Login login = new Login();

        String username = JOptionPane.showInputDialog(null, "Enter username:", "Registration", JOptionPane.QUESTION_MESSAGE);

        String password = JOptionPane.showInputDialog(null, "Enter password:", "Registration", JOptionPane.QUESTION_MESSAGE);

        String cellPhone = JOptionPane.showInputDialog(null, "Enter South African cell phone number (with international code):", "Registration", JOptionPane.QUESTION_MESSAGE);

        String registrationResult = login.registerUser(username, password, cellPhone);

        JOptionPane.showMessageDialog(null, registrationResult);

        if (!registrationResult.contains("successfully")) {

            JOptionPane.showMessageDialog(null, "Registration failed. Exiting.");

            return;

        }

        String loginUsername = JOptionPane.showInputDialog(null, "Enter username:", "Login", JOptionPane.QUESTION_MESSAGE);

        String loginPassword = JOptionPane.showInputDialog(null, "Enter password:", "Login", JOptionPane.QUESTION_MESSAGE);

        boolean loginSuccess = login.loginUser(loginUsername, loginPassword);

        String firstName = JOptionPane.showInputDialog(null, "Enter your first name:", "Login", JOptionPane.QUESTION_MESSAGE);

        String lastName = JOptionPane.showInputDialog(null, "Enter your last name:", "Login", JOptionPane.QUESTION_MESSAGE);

        JOptionPane.showMessageDialog(null, login.returnLoginStatus(loginSuccess, firstName, lastName));

        if (!loginSuccess) {

            JOptionPane.showMessageDialog(null, "Login failed. Exiting.");

            return;

        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        String numMessagesStr = JOptionPane.showInputDialog(null, "How many messages do you want to send?", "Message Count", JOptionPane.QUESTION_MESSAGE);

        int numMessages;

        try {

            numMessages = Integer.parseInt(numMessagesStr);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Invalid number. Exiting.");

            return;

        }

        for (int i = 1; i <= numMessages; i++) {

            String recipient = JOptionPane.showInputDialog(null, "Enter recipient cell number (with international code):", "Message " + i, JOptionPane.QUESTION_MESSAGE);

            String messageText = JOptionPane.showInputDialog(null, "Enter message (max 250 characters):", "Message " + i, JOptionPane.QUESTION_MESSAGE);

            if (messageText.length() > 250) {

                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");

                i--;

                continue;

            }

            Message message = new Message(recipient, messageText, i);

            String sendResult = message.SentMessage();

            JOptionPane.showMessageDialog(null, sendResult);

            String details = "Message Details:\n" +

                    "MessageID: " + message.getMessageID() + "\n" +

                    "Message Hash: " + message.getMessageHash() + "\n" +

                    "Recipient: " + message.getRecipient() + "\n" +

                    "Message: " + message.getMessage();

            JOptionPane.showMessageDialog(null, details);

        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());

        boolean exit = false;

        while (!exit) {

            String[] options = {

                    "Display sender and recipient of all sent messages",

                    "Display the longest sent message",

                    "Search for a message by Message ID",

                    "Search for messages by recipient",

                    "Delete a message by Message Hash",

                    "Display report of all sent messages",

                    "Quit"

            };

            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Menu",

                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {

                case 0:

                    Message.displaySenderRecipient();

                    break;

                case 1:

                    Message.displayLongestMessage();

                    break;

                case 2:

                    String id = JOptionPane.showInputDialog(null, "Enter Message ID to search:", "Search by ID", JOptionPane.QUESTION_MESSAGE);

                    Message.searchByMessageID(id);

                    break;

                case 3:

                    String recipient = JOptionPane.showInputDialog(null, "Enter recipient to search:", "Search by Recipient", JOptionPane.QUESTION_MESSAGE);

                    Message.searchByRecipient(recipient);

                    break;

                case 4:

                    String hash = JOptionPane.showInputDialog(null, "Enter Message Hash to delete:", "Delete by Hash", JOptionPane.QUESTION_MESSAGE);

                    Message.deleteMessageByHash(hash);

                    break;

                case 5:

                    Message.displayReport();

                    break;

                case 6:

                    exit = true;

                    JOptionPane.showMessageDialog(null, "Exiting program.");

                    break;

                default:

                    JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");

            }

        }

    }

}


