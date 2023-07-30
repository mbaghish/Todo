package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserIO {
	 //Method to load users from file into the list
    static void loadUsersFromFile(List<User> listOfUsers) throws IOException {
    File file = new File("users.txt");
    if (file.exists()) {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(":");
            if (userData.length == 3) {
                String username = userData[0];
                String password = userData[1];
                String externkey = userData[2];
                listOfUsers.add(new User(username, password,  externkey));
            }
        }
        reader.close();
    } else {
        // Create the file if it doesn't exist
        file.createNewFile();
    }
    }

    //Method to save users to file from the list
    static void saveUsersToFile(List<User> listOfUsers) throws IOException {
    File file = new File("users.txt");
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    for (User user : listOfUsers) {
        writer.write(user.getUsername() + ":" + user.getPassword() +":" + user.getExternkey() + "\n");
    }
    writer.close();
    }

    //Method to check if a username is already taken
    static boolean isUsernameTaken(List<User> listOfUsers, String username) {
    for (User user : listOfUsers) {
        if (user.getUsername().equals(username)) {
            return true;
        }
    }
    return false;
    }

	
}
