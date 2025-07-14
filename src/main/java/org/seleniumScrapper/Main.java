package org.seleniumScrapper;

import com.microsoft.playwright.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static final int MAX_FIELDS = 10;
    static final List<JTextField> collegeFields = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("College Financial Aid & Deadline Checker");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(inputPanel);

        // Add initial 3 fields
        for (int i = 0; i < 3; i++) {
            addTextField(inputPanel);
        }

        JButton addFieldBtn = new JButton("+ Add College");
        addFieldBtn.addActionListener((ActionEvent e) -> {
            if (collegeFields.size() < MAX_FIELDS) {
                addTextField(inputPanel);
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });

        JButton searchBtn = new JButton("Search Google");

        JTextArea resultArea = new JTextArea(15, 60);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(addFieldBtn, BorderLayout.WEST);
        topPanel.add(searchBtn, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(resultScroll, BorderLayout.SOUTH);

        searchBtn.addActionListener((ActionEvent e) -> {
            resultArea.setText("");
            for (JTextField field : collegeFields) {
                String college = field.getText().trim();
                if (!college.isEmpty()) {
                    performSearch(college, resultArea);
                }
            }
        });

        frame.setVisible(true);
    }

    static void addTextField(JPanel panel) {
        JTextField field = new JTextField();
        collegeFields.add(field);
        panel.add(field);
    }

    static void performSearch(String collegeName, JTextArea resultArea) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();

            // 1. Search for Full Financial Aid
            String queryAid = "Does " + collegeName + " offer full financial aid?";
            page.navigate("https://www.google.com/search?q=" + URLEncoder.encode(queryAid, "UTF-8"));
            page.waitForTimeout(3000);
            String aidResult = page.locator("div").first().textContent();

            // Screenshot of Aid
            String aidScreenshotPath = "screenshot_" + collegeName.replaceAll(" ", "_") + "_aid.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(new File(aidScreenshotPath).toPath()));

            // 2. Search for Deadline
            String queryDeadline = collegeName + " admission deadline";
            page.navigate("https://www.google.com/search?q=" + URLEncoder.encode(queryDeadline, "UTF-8"));
            page.waitForTimeout(3000);
            String deadlineResult = page.locator("div").first().textContent();

            // Screenshot of Deadline
            String deadlineScreenshotPath = "screenshot_" + collegeName.replaceAll(" ", "_") + "_deadline.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(new File(deadlineScreenshotPath).toPath()));

            // Output to GUI
            resultArea.append("📚 " + collegeName + "\n");
            resultArea.append(" - Full Aid? → " + truncate(aidResult) + "\n");
            resultArea.append(" - Deadline  → " + truncate(deadlineResult) + "\n");
            resultArea.append(" - Screenshots saved:\n");
            resultArea.append("    → " + aidScreenshotPath + "\n");
            resultArea.append("    → " + deadlineScreenshotPath + "\n\n");

            browser.close();
        } catch (Exception e) {
            resultArea.append("❌ Error with " + collegeName + ": " + e.getMessage() + "\n");
        }
    }

    static String truncate(String text) {
        if (text == null) return "(no result)";
        return text.length() > 150 ? text.substring(0, 150) + "..." : text;
    }
}
