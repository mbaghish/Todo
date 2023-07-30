package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	
	static void createAndShowGUI(String FILE_PATH, final String loggedInUser) {
        JFrame frame = new JFrame("ToDo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setForeground(Color.gray);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        // Center the frame on the screen after creating and showing it
        frame.setLocationRelativeTo(null);
        JTextArea taskListTextArea = new JTextArea();
        taskListTextArea.setEditable(false);
        taskListTextArea.setBackground(new Color(30, 30, 30)); // Set background color to gray
        taskListTextArea.setForeground(Color.WHITE); // Set text color to white
        JScrollPane scrollPane = new JScrollPane(taskListTextArea);

        JButton addTaskButton = new JButton("Add Task");
        JButton viewListButton = new JButton("View List");
        JButton editTaskButton = new JButton("Edit Task");
        JButton markDoneButton = new JButton("Mark Task as Done");
        JButton deleteTaskButton = new JButton("Delete Task");
        JButton searchButton = new JButton("Search");
        
        JTextField searchTextField = new PlaceholderTextField("search a task");
        searchTextField.setPreferredSize(new Dimension(200, 25));
        JButton exitButton = new JButton("Exit");
        JButton externKeyButton = new JButton("?");
        String[] externKeyHolder = new String[1];
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTaskButton);
        buttonPanel.add(viewListButton);
        buttonPanel.add(editTaskButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(deleteTaskButton);
       
        buttonPanel.add(searchTextField);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(externKeyButton);
        
        frame.add(scrollPane);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        //String externKey;
        Main.tasks = new ArrayList<>();
        FilesIO.loadTasksFromFile(Main.tasks, FILE_PATH);

        // Create an empty list to hold users
        List<User> listOfUsers = new ArrayList<>();

        // Load users from file if the file exists
        try {
            UserIO.loadUsersFromFile(listOfUsers);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        externKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iterate through the list of users to see if we have a match
                for (User user : listOfUsers) {
                    if (user.getUsername().equals(loggedInUser)) {
                        externKeyHolder[0] = user.getExternkey();
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "Your extern key is: " + externKeyHolder[0]);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchTextField.getText().trim();
                if (!searchTerm.isEmpty()) {
                    ArrayList<Task> filteredTasks = new ArrayList<>();
                    for (Task task : Main.tasks) {
                        if (task.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                            filteredTasks.add(task);
                            //break;
                        }
                    }
                    displayTasks(filteredTasks);
                } else {
                    // Show all tasks if the search term is empty
                    displayTasks(Main.tasks);
                }
            }

            private void displayTasks(ArrayList<Task> tasks) {
                StringBuilder sb = new StringBuilder();
                sb.append("----- ToDo List -----\n\n");
                if (tasks.isEmpty()) {
                    sb.append("No tasks in the list.\n");
                } else {
                    int taskNumber = 1;
                    for (Task task : tasks) {
                        sb.append(taskNumber).append(". ").append(task.getName()).append(" - ")
                                .append(task.getDescription()).append(" - ").append(task.isDone() ? "Done" : "Not Done")
                                .append("\n");
                        taskNumber++;
                    }
                }
                taskListTextArea.setText(sb.toString());
            }
            
        });
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter the task name:");
                if (name == null) {
                	return;
                } else if (name == "") {
                	return;
                }
                String description = JOptionPane.showInputDialog(frame, "Enter the task description:");
                if (description == null) {
                	description = "?";
                }
                Main.tasks.add(new Task(name, description, false));
                FilesIO.saveTasksToFile(FILE_PATH);
            }
        });

        viewListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 StringBuilder sb = new StringBuilder();
                 sb.append("----- ToDo List -----\n\n");
                 if (Main.tasks.isEmpty()) {
                     sb.append("No tasks in the list.\n");
                 } else {
                     int taskNumber = 1;
                     for (Task task : Main.tasks) {
                         sb.append(taskNumber).append(". ").append(task.getName()).append(" - ")
                                 .append(task.getDescription()).append(" - ").append(task.isDone() ? "Done" : "Not Done")
                                 .append("\n");
                         taskNumber++;
                     }
                 }
                 taskListTextArea.setText(sb.toString());
               
            }
        });

        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the task number to edit:");
                int editTaskNumber = Integer.parseInt(input);

                if (editTaskNumber >= 1 && editTaskNumber <= Main.tasks.size()) {
                    Task taskToEdit = Main.tasks.get(editTaskNumber - 1);
                    String newName = JOptionPane.showInputDialog(frame, "Enter the new task name:", taskToEdit.getName());
                    taskToEdit.setName(newName);
                    String newDescription = JOptionPane.showInputDialog(frame, "Enter the new task description:",
                            taskToEdit.getDescription());
                    taskToEdit.setDescription(newDescription);
                    FilesIO.saveTasksToFile(FILE_PATH);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid task number.");
                }
            }
        });

        markDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the task number to mark as done:");
                int taskNumber = Integer.parseInt(input);

                if (taskNumber >= 1 && taskNumber <= Main.tasks.size()) {
                    Task taskToMarkAsDone = Main.tasks.get(taskNumber - 1);
                    taskToMarkAsDone.setDone(true);
                    FilesIO.saveTasksToFile(FILE_PATH);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid task number.");
                }
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the task number to delete:");
                int deleteTaskNumber = Integer.parseInt(input);

                if (deleteTaskNumber >= 1 && deleteTaskNumber <= Main.tasks.size()) {
                	Main.tasks.remove(deleteTaskNumber - 1);
                	FilesIO.saveTasksToFile(FILE_PATH);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid task number.");
                }
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Save the tasks to the file before exiting
                	FilesIO.saveTasksToFile(FILE_PATH);
                     try {
     					Thread.sleep(500);
     				} catch (InterruptedException e1) {
     					e1.printStackTrace();
     				}
                    System.out.println("Exiting the Program!");
                    System.exit(0); // Exit the program
                }
            }
        });

        frame.setVisible(true);
    }
	
	
	
	
	
	 static void createAndShowReadOnlyGUI(String FILE_PATH) {
	        JFrame frame = new JFrame("ToDo List (Read-Only)");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(700, 600);
	        frame.getContentPane().setBackground(new Color(30, 30, 30));
	        frame.setForeground(Color.gray);
	        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        frame.setLocationRelativeTo(null);

	        JTextArea taskListTextArea = new JTextArea();
	        taskListTextArea.setEditable(false);
	        taskListTextArea.setBackground(new Color(30, 30, 30));
	        taskListTextArea.setForeground(Color.WHITE);
	        JScrollPane scrollPane = new JScrollPane(taskListTextArea);

	        JButton viewListButton = new JButton("View List");
	        JButton markDoneButton = new JButton("Mark Task as Done");
	        JButton exitButton = new JButton("Exit");

	        JTextField searchTextField = new JTextField();
	        searchTextField.setPreferredSize(new Dimension(200, 25));

	        JButton searchButton = new JButton("Search");
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(viewListButton);
	        buttonPanel.add(markDoneButton);
	        buttonPanel.add(searchTextField);
	        buttonPanel.add(searchButton);
	        buttonPanel.add(exitButton);

	        frame.add(scrollPane);
	        frame.add(buttonPanel, BorderLayout.SOUTH);

	        Main.tasks = new ArrayList<>();
	        FilesIO.loadTasksFromFile(Main.tasks, FILE_PATH);

	        searchButton.addActionListener(new ActionListener() {
	        	 @Override
	             public void actionPerformed(ActionEvent e) {
	                 String searchTerm = searchTextField.getText().trim();
	                 if (!searchTerm.isEmpty()) {
	                     ArrayList<Task> filteredTasks = new ArrayList<>();
	                     for (Task task : Main.tasks) {
	                         if (task.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
	                             filteredTasks.add(task);
	                             //break;
	                         }
	                     }
	                     displayTasks(filteredTasks);
	                 } else {
	                     // Show all tasks if the search term is empty
	                     displayTasks(Main.tasks);
	                 }
	             }

	             private void displayTasks(ArrayList<Task> tasks) {
	                 StringBuilder sb = new StringBuilder();
	                 sb.append("----- ToDo List -----\n\n");
	                 if (tasks.isEmpty()) {
	                     sb.append("No tasks in the list.\n");
	                 } else {
	                     int taskNumber = 1;
	                     for (Task task : tasks) {
	                         sb.append(taskNumber).append(". ").append(task.getName()).append(" - ")
	                                 .append(task.getDescription()).append(" - ").append(task.isDone() ? "Done" : "Not Done")
	                                 .append("\n");
	                         taskNumber++;
	                     }
	                 }
	                 taskListTextArea.setText(sb.toString());
	             }
	             
	         });

	        viewListButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                StringBuilder sb = new StringBuilder();
	                sb.append("----- ToDo List -----\n\n");
	                if (Main.tasks.isEmpty()) {
	                    sb.append("No tasks in the list.\n");
	                } else {
	                    int taskNumber = 1;
	                    for (Task task : Main.tasks) {
	                        sb.append(taskNumber).append(". ").append(task.getName()).append(" - ")
	                                .append(task.getDescription()).append(" - ").append(task.isDone() ? "Done" : "Not Done")
	                                .append("\n");
	                        taskNumber++;
	                    }
	                }
	                taskListTextArea.setText(sb.toString());
	            }
	        });

	        markDoneButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String input = JOptionPane.showInputDialog(frame, "Enter the task number to mark as done:");
	                int taskNumber = Integer.parseInt(input);

	                if (taskNumber >= 1 && taskNumber <= Main.tasks.size()) {
	                    Task taskToMarkAsDone = Main.tasks.get(taskNumber - 1);
	                    taskToMarkAsDone.setDone(true);
	                    FilesIO.saveTasksToFile(FILE_PATH);
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Invalid task number.");
	                }
	            }
	        });

	        exitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
	                if (response == JOptionPane.YES_OPTION) {
	                    FilesIO.saveTasksToFile(FILE_PATH);
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e1) {
	                        e1.printStackTrace();
	                    }
	                    System.out.println("Exiting the Program!");
	                    System.exit(0);
	                }
	            }
	        });

	        frame.setVisible(true);
	    }
}
