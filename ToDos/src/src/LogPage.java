package src;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogPage {
	

    static void startLoggin() {

        String username;
        String password;
        String externkey = null;
        User loggedInUser = null;

        // Create an empty list to hold users
        List<User> listOfUsers = new ArrayList<>();

        // Load users from file if the file exists
        try {
            UserIO.loadUsersFromFile(listOfUsers);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        UIManager.put("TextField.foreground", Color.black);
        UIManager.put("Label.foreground", Color.WHITE);
        
        UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
        UIManager.put("OptionPane.background", new Color(30, 30, 30));
        UIManager.put("Panel.background", new Color(30, 30, 30));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(30, 30, 30)));
        UIManager.put("Button.select", new Color(80, 80, 80));

        // Ask the user if they want to create a new user or log in
        String[] options = { "Log in as admin","Log in as extern", "Create a new user" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "What do you want to do?",
                "ToDo List App",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CANCEL_OPTION) {// Logic for "Create a new user" option
            // Get input for creating a new user
            JPanel newUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            newUserPanel.add(new JLabel("Please type your new username:"), gbc);

            gbc.gridy++;
            JTextField usernameField = new JTextField(15);
            //usernameField.setForeground(Color.WHITE); // Set text color to white
            newUserPanel.add(usernameField, gbc);

            gbc.gridy++;
            newUserPanel.add(new JLabel("Please type your new password:"), gbc);

            gbc.gridy++;
            JPasswordField passwordField = new JPasswordField(15);
            newUserPanel.add(passwordField, gbc);

            int newUserChoice = JOptionPane.showConfirmDialog(
                    null,
                    newUserPanel,
                    "Create New User",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (newUserChoice == JOptionPane.OK_OPTION) {
                username = usernameField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                password = new String(passwordChars);

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
                    return;
                }

                // Check if the username already exists
                if (UserIO.isUsernameTaken(listOfUsers, username)) {
                    JOptionPane.showMessageDialog(null, "Username is already taken. Please choose a different username.");
                    return;
                }
                int randomNumber = generateRandomNumber(1000, 9999);
                System.out.println("please note, your extern key is: " + randomNumber);
                externkey= randomNumber+"";
                JOptionPane.showMessageDialog(null, "please note, your extern key is: " + randomNumber);
                listOfUsers.add(new User(username, password,externkey));

                // Save the updated list of users to the file
                try {
                    UserIO.saveUsersToFile(listOfUsers);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Splash.showSplashScreenStart("User " + username + " has been saved!", 1000);

            } else {
                // User canceled creating a new user
                return;
            }

        } else if (choice == JOptionPane.YES_OPTION) { // Logic for "Log in as admin" option
            // Get input for logging in
            JPanel loginPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            loginPanel.add(new JLabel("Please type your username:"), gbc);

            gbc.gridy++;
            JTextField usernameField = new JTextField(15);
            loginPanel.add(usernameField, gbc);

            gbc.gridy++;
            loginPanel.add(new JLabel("Please type your password:"), gbc);

            gbc.gridy++;
            JPasswordField passwordField = new JPasswordField(15);
            loginPanel.add(passwordField, gbc);

            int loginChoice = JOptionPane.showConfirmDialog(
                    null,
                    loginPanel,
                    "Login",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (loginChoice == JOptionPane.OK_OPTION) {
                username = usernameField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                password = new String(passwordChars);

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
                    return;
                }

                // Iterate through the list of users to see if we have a match
                for (User user : listOfUsers) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        loggedInUser = user;
                        break;
                    }
                }

                // If loggedInUser was changed from null, the login was successful
                if (loggedInUser != null) {
                    String FILE_PATH = loggedInUser.getUsername() + "File.txt";
                    File myObj = new File(FILE_PATH);
                    try {
                        if (myObj.createNewFile()) {
                            System.out.println("File created: " + myObj.getName());
                        } else {
                            System.out.println(myObj.getName() + " already exists.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Splash.showSplashScreenEnd("Hello \"" + loggedInUser.getUsername() + "\"", 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    GUI.createAndShowGUI(FILE_PATH,loggedInUser.getUsername());
                    System.out.println("User \"" + loggedInUser.getUsername() + "\" logged in!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username/password combination!");
                }
            } else {
                return;// User canceled login
            }


        } else if(choice == JOptionPane.NO_OPTION) {// Logic for "Log in as extern" option
        	 // Get input for logging in
            JPanel loginPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            loginPanel.add(new JLabel("Please type the username:"), gbc);

            gbc.gridy++;
            JTextField usernameField = new JTextField(15);
            loginPanel.add(usernameField, gbc);

            gbc.gridy++;
            loginPanel.add(new JLabel("Please type extern key:"), gbc);

            gbc.gridy++;
            JPasswordField passwordField = new JPasswordField(15);
            loginPanel.add(passwordField, gbc);

            int loginChoice = JOptionPane.showConfirmDialog(
                    null,
                    loginPanel,
                    "Login",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (loginChoice == JOptionPane.OK_OPTION) {
                username = usernameField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                password = new String(passwordChars);

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and extern key cannot be empty.");
                    return;
                }

                // Iterate through the list of users to see if we have a match
                for (User user : listOfUsers) {
                    if (user.getUsername().equals(username) && user.getExternkey().equals(password)) {//name and extern key
                        loggedInUser = user;
                        break;
                    }
                }

                // If loggedInUser was changed from null, the login was successful
                if (loggedInUser != null) {
                    String FILE_PATH = loggedInUser.getUsername() + "File.txt";
                    File myObj = new File(FILE_PATH);
                    try {
                        if (myObj.createNewFile()) {
                            System.out.println("File created: " + myObj.getName());
                        } else {
                            System.out.println(myObj.getName() + " already exists.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Splash.showSplashScreenEnd("wellcome to \"" + loggedInUser.getUsername() + "\"s Todo List!", 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    GUI.createAndShowReadOnlyGUI(FILE_PATH);
                    System.out.println("An extern logged in!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username/password combination!");
                }
            } else {
                // User canceled login
                return;
        }
        }
        
        else {
            Splash.showSplashScreenEnd("Exiting the Program!", 1000);
            System.out.println("User left the Program!");
        }
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
