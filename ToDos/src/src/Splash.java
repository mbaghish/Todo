package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Splash {
	 //Method to show the splash screen
    public static void showSplashScreenStart(String message, int duration) {
        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true); // Remove title bar and borders
        splashFrame.setLayout(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
     // Set the font color of the label to white
        label.setForeground(Color.WHITE);
        splashFrame.add(label, BorderLayout.CENTER);

        // Set the preferred size of the splash screen
        splashFrame.setPreferredSize(new Dimension(500, 300));

        // Center the splash screen on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - splashFrame.getPreferredSize().width) / 2;
        int y = (screenSize.height - splashFrame.getPreferredSize().height) / 2;
        splashFrame.setLocation(x, y);

     // Set the background color of the splash screen to dark grey
        splashFrame.getContentPane().setBackground(new Color(49, 53, 53));
        splashFrame.pack();
        splashFrame.setVisible(true);

        // Create a thread to close the splash screen after the specified duration
        Thread splashThread = new Thread(() -> {
            try {
                Thread.sleep(duration);
                splashFrame.dispose(); // Close the splash screen after the specified duration
                
                // Start the main program after the splash screen is closed
                LogPage.startLoggin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        splashThread.start();
    }

    
    
    
    //Method to show the splash screen
    public static void showSplashScreenEnd(String message, int duration) {
        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true); // Remove title bar and borders
        splashFrame.setLayout(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
     // Set the font color of the label to white
        label.setForeground(Color.WHITE);
        splashFrame.add(label, BorderLayout.CENTER);

        // Set the preferred size of the splash screen
        splashFrame.setPreferredSize(new Dimension(500, 300));

        // Center the splash screen on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - splashFrame.getPreferredSize().width) / 2;
        int y = (screenSize.height - splashFrame.getPreferredSize().height) / 2;
        splashFrame.setLocation(x, y);

     // Set the background color of the splash screen to dark grey
        splashFrame.getContentPane().setBackground(new Color(49, 53, 53));
        splashFrame.pack();
        splashFrame.setVisible(true);

        // Create a thread to close the splash screen after the specified duration
        Thread splashThread = new Thread(() -> {
            try {
                Thread.sleep(duration);
                splashFrame.dispose(); // Close the splash screen after the specified duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        splashThread.start();
    }
}
