package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FilesIO {
	static void loadTasksFromFile(ArrayList<Task> tasks, String FILE_PATH) {

		 try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		            // Parse the line and create Task objects
		            String[] taskData = line.split(",");
		            String name = taskData[0];
		            String description = taskData[1];
		            boolean isDone = Boolean.parseBoolean(taskData[2]);
		            tasks.add(new Task(name, description, isDone));
		        }
		        System.out.println("Tasks loaded from file: " + FILE_PATH);
		    } catch (FileNotFoundException e) {
		        System.out.println("File not found: " + FILE_PATH);
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.out.println("Error loading tasks from file: " + FILE_PATH);
		    }
		}


   static void saveTasksToFile(String FILE_PATH) {
   	 try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
   	        for (Task task : Main.tasks) {
   	            // Convert Task object to a line of text
   	            String line = task.getName() + "," + task.getDescription() + "," + task.isDone() + "\n";
   	            bw.write(line);
   	        }
   	        System.out.println("Tasks saved to file: " + FILE_PATH);
   	    } catch (IOException e) {
   	        e.printStackTrace();
   	        System.out.println("Error saving tasks to file: " + FILE_PATH);
   	    }
   }

}
