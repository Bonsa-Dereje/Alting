package org.seleniumScrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JFrame urlSiteScrapper = new JFrame("URL Site Scraper");
        urlSiteScrapper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        urlSiteScrapper.setSize(700, 600);
        urlSiteScrapper.setLocationRelativeTo(null);

        JPanel mainWindow = new JPanel(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        position.insets = new Insets(5, 5, 5, 5);

        JLabel appTitle = new JLabel("Site Scraper");
        appTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        position.gridx = 0;
        position.gridy = 0;
        position.gridwidth = 2;
        mainWindow.add(appTitle, position);
        position.gridwidth = 1;

        JLabel urlLabel = new JLabel("URL:");
        position.gridx = 0;
        position.gridy = 1;
        mainWindow.add(urlLabel, position);

        JTextField urlInput = new JTextField(25);
        position.gridx = 1;
        position.gridy = 1;
        mainWindow.add(urlInput, position);

        JLabel keywordLabel = new JLabel("Keywords (comma-separated):");
        position.gridx = 0;
        position.gridy = 2;
        mainWindow.add(keywordLabel, position);

        JTextField keywordInput = new JTextField(25);
        position.gridx = 1;
        position.gridy = 2;
        mainWindow.add(keywordInput, position);

        JButton scrapeButton = new JButton("Scrape");
        position.gridx = 0;
        position.gridy = 3;
        position.gridwidth = 2;
        scrapeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainWindow.add(scrapeButton, position);
        position.gridwidth = 1;

        JTextArea resultArea = new JTextArea(20, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        position.gridx = 0;
        position.gridy = 4;
        position.gridwidth = 2;
        mainWindow.add(scrollPane, position);

        scrapeButton.addActionListener((ActionEvent e) -> {
            String url = urlInput.getText().trim();

            if (url.isEmpty()) {
                JOptionPane.showMessageDialog(urlSiteScrapper, "Please enter a URL.");
                return;
            }

            try {
                Document mainDoc = Jsoup.connect(url).get();
                Document databaseDoc = Jsoup.connect("https://mally.stanford.edu/~sr/universities.html").get();

                Set<String> namesFromMainUrl = new HashSet<>();
                Elements anchors = mainDoc.select("a");
                for (Element a : anchors) {
                    String name = a.text().trim().toLowerCase();
                    if (!name.isEmpty()) {
                        namesFromMainUrl.add(name);
                    }
                }

                StringBuilder result = new StringBuilder();
                result.append("Matches found on database:\n\n");

                Elements dbLinks = databaseDoc.select("a");
                for (Element dbLink : dbLinks) {
                    String dbText = dbLink.text().trim().toLowerCase();
                    if (namesFromMainUrl.contains(dbText)) {
                        result.append("  → ").append(dbLink.text())
                              .append(" → ").append(dbLink.absUrl("href"))
                              .append("\n");
                    }
                }

                resultArea.setText(result.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        urlSiteScrapper.add(mainWindow);
        urlSiteScrapper.setVisible(true);
    }
}
