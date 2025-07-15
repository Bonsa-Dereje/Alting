package org.seleniumScrapper;

import javax.swing.*;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();


        JFrame alting = new JFrame("ALTING");
        alting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alting.setSize(500, 700);
        
        JPanel mainWindow = new JPanel();
        GridBagConstraints position = new GridBagConstraints();
        position.fill = GridBagConstraints.HORIZONTAL;
        position.weightx = 1.0;


        JLabel apptitle = new JLabel("ALTING");
        apptitle.setFont(new Font("Roboto", Font.BOLD, 20));
        position.gridx = 0;
        position.gridy = 0;
        mainWindow.add(apptitle, position);

        JLabel appDesc = new JLabel("Search Automation Tool");
        apptitle.setFont(new Font("Roboto", Font.BOLD, 14));
        position.gridx = 0;
        position.gridy = 1;
        mainWindow.add(appDesc, position);


        // Delay so you can focus another window first
        Thread.sleep(3000);

        // Press Alt
        robot.keyPress(KeyEvent.VK_ALT);
        Thread.sleep(100); // slight delay to register modifier
 
        // Press Tab
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        // Optional: hold Alt for a moment before releasing
        Thread.sleep(500);

        // Release Alt
        robot.keyRelease(KeyEvent.VK_ALT);


        alting.add(mainWindow);
        alting.setVisible(true);
        alting.setLocationRelativeTo(null); // Center the window on the screen
        
    }
}
