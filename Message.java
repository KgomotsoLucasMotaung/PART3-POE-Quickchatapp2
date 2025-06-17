/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatapp2;

/**
 *
 * @author USER
 */
import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import java.util.Scanner;

import java.util.regex.Pattern;

import java.io.FileWriter;

import java.io.FileReader;

import java.io.IOException;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class Message {

    private String messageID;

    private int numMessagesSent;

    private String recipient;

    private String message;

    private String messageHash;


    private static List<Message> sentMessages = new ArrayList<>();

    private static List<Message> disregardedMessages = new ArrayList<>();

    private static List<Message> storedMessages = new ArrayList<>();

    private static List<String> messageHashes = new ArrayList<>();

    private static List<String> messageIDs = new ArrayList<>();


    public Message() {

        this.messageID = "";

        this.numMessagesSent = 0;

        this.recipient = "";

        this.message = "";

        this.messageHash = "";

    }


    public Message(String recipient, String message, int numMessagesSent) {

        this.messageID = generateMessageID();

        this.numMessagesSent = numMessagesSent;

        this.recipient = recipient;

        this.message = message;

        this.messageHash = createMessageHash();

        messageHashes.add(this.messageHash);

        messageIDs.add(this.messageID);

    }


    private String generateMessageID() {

        Random rand = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {

            sb.append(rand.nextInt(10));

        }

        return sb.toString();

    }


    public boolean checkMessageID() {

        return this.messageID.length() <= 10;

    }


    public boolean checkRecipientCell() {

        if (recipient == null) return false;

        String regex = "^\\+\\d{1,3}\\d{7,10}$";

        return Pattern.matches(regex, recipient);

    }


    public String createMessageHash() {

        String firstTwo = messageID.substring(0, 2);

        String numStr = Integer.toString(numMessagesSent);

        String[] words = message.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";

        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        String hash = firstTwo + ":" + numStr + ":" + firstWord + lastWord;

        return hash.toUpperCase();

    }


    public String SentMessage() {

        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};

        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Message Options",

                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {

            case 0:

                sentMessages.add(this);

                return "Message successfully sent.";

            case 1:

                disregardedMessages.add(this);

                return "Press 0 to delete message.";

            case 2:

                storedMessages.add(this);

                storeMessage();

                return "Message successfully stored.";

            default:

                return "Invalid option.";

        }

    }


    public void storeMessage() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.json"))) {

            for (Message msg : storedMessages) {

                writer.write(msg.toString());

                writer.newLine();

            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error storing messages: " + e.getMessage());

        }

    }


    public static void readStoredMessages() {

        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {

            storedMessages.clear();

            String line;

            while ((line = reader.readLine()) != null) {

                // Assuming a simple format, parse line to Message object

                // This requires implementing a fromString method or similar

                Message msg = Message.fromString(line);

                if (msg != null) {

                    storedMessages.add(msg);

                }

            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error reading stored messages: " + e.getMessage());

        }

    }


    public static String printMessages() {

        StringBuilder sb = new StringBuilder();

        for (Message msg : sentMessages) {

            sb.append("MessageID: ").append(msg.messageID).append(", ");

            sb.append("Message Hash: ").append(msg.messageHash).append(", ");

            sb.append("Recipient: ").append(msg.recipient).append(", ");

            sb.append("Message: ").append(msg.message).append("\n");

        }

        return sb.toString();

    }


    public static int returnTotalMessages() {

        return sentMessages.size();

    }


    public static void displaySenderRecipient() {

        System.out.println("Sender and Recipient of all sent messages:");

        for (Message msg : sentMessages) {

            System.out.println("Sender: " + msg.messageID + ", Recipient: " + msg.recipient);

        }

    }


    public static void displayLongestMessage() {

        if (sentMessages.isEmpty()) {

            System.out.println("No sent messages.");

            return;

        }

        Message longest = sentMessages.get(0);

        for (Message msg : sentMessages) {

            if (msg.message.length() > longest.message.length()) {

                longest = msg;

            }

        }

        System.out.println("Longest sent message: " + longest.message);

    }


    public static void searchByMessageID(String id) {

        for (Message msg : sentMessages) {

            if (msg.messageID.equals(id)) {

                System.out.println("Message found:");

                System.out.println("Recipient: " + msg.recipient);

                System.out.println("Message: " + msg.message);

                return;

            }

        }

        System.out.println("Message ID not found.");

    }


    public static void searchByRecipient(String recipient) {

        boolean found = false;

        for (Message msg : sentMessages) {

            if (msg.recipient.equals(recipient)) {

                System.out.println("Message to " + recipient + ": " + msg.message);

                found = true;

            }

        }

        if (!found) {

            System.out.println("No messages found for recipient: " + recipient);

        }

    }


    public static void deleteMessageByHash(String hash) {

        for (int i = 0; i < sentMessages.size(); i++) {

            if (sentMessages.get(i).messageHash.equals(hash)) {

                System.out.println("Message \"" + sentMessages.get(i).message + "\" successfully deleted.");

                sentMessages.remove(i);

                return;

            }

        }

        System.out.println("Message hash not found.");

    }


    public static void displayReport() {

        System.out.println("Report of all sent messages:");

        for (Message msg : sentMessages) {

            System.out.println("Message Hash: " + msg.messageHash);

            System.out.println("Recipient: " + msg.recipient);

            System.out.println("Message: " + msg.message);

            System.out.println("-------------------------");

        }

    }


    public String getMessageID() {

        return messageID;

    }


    public String getRecipient() {

        return recipient;

    }


    public String getMessage() {

        return message;

    }


    public String getMessageHash() {

        return messageHash;

    }

}





