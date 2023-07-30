package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReadOnlyGUI {
	
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
        JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");
        JTextField searchTextField = new PlaceholderTextField("search a task");
        searchTextField.setPreferredSize(new Dimension(200, 25));
        
        JPanel buttonPanel = new JPanel();
      
        buttonPanel.add(viewListButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(searchTextField);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);

        frame.add(scrollPane);
        //frame.add(buttonPanel, BorderLayout.SOUTH);
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
}
