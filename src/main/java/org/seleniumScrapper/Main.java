// ✅ Enhanced to extract Acceptance Rate and location + Common App/Test Optional/TOEFL checks

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String[] keywords = keywordInput.getText().toLowerCase().split(",");

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
                outerLoop:
                for (Element dbLink : dbLinks) {
                    String dbText = dbLink.text().trim().toLowerCase();
                    if (namesFromMainUrl.contains(dbText)) {
                        for (String keyword : keywords) {
                            keyword = keyword.trim();
                            if (!keyword.isEmpty() && dbText.contains(keyword)) {
                                result.append("  → ").append(dbLink.text())
                                      .append(" → ").append(dbLink.absUrl("href"))
                                      .append("\n");

                                for (Element anchor : anchors) {
                                    if (anchor.text().trim().toLowerCase().equals(dbText) && anchor.hasAttr("href")) {
                                        String clickedUrl = anchor.absUrl("href");

                                        try {
                                            Document clickedDoc = Jsoup.connect(clickedUrl).get();
                                            Elements allElements = clickedDoc.getAllElements();

                                            // ACCEPTANCE RATE
                                            for (int i = 0; i < allElements.size(); i++) {
                                                Element el = allElements.get(i);
                                                if (el.text().toLowerCase().contains("acceptance rate")) {
                                                    for (int j = i + 1; j < allElements.size(); j++) {
                                                        String text = allElements.get(j).text();
                                                        Matcher matcher = Pattern.compile("(\\d{1,3})\\s*%").matcher(text);
                                                        if (matcher.find()) {
                                                            result.append("     ↳ Acceptance rate: ").append(matcher.group(1)).append("%\n");
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }

                                            // LOCATION
                                            Elements divs = clickedDoc.select("div.row.mb-3");
                                            for (Element div : divs) {
                                                if (div.select(".fe-map-pin").size() > 0) {
                                                    Element locationDiv = div.select(".col.pl-0").first();
                                                    if (locationDiv != null) {
                                                        result.append("     ↳ Location: ").append(locationDiv.text().trim()).append("\n");
                                                        break;
                                                    }
                                                }
                                            }

                                            // TICK/UNCHECK FEATURES
                                            String bodyText = clickedDoc.text().toLowerCase();
                                            result.append("     ↳ Accepts Common App: ")
                                                  .append(bodyText.contains("accepts common app") ? "✔" : "✘")
                                                  .append("\n");

                                            result.append("     ↳ Test Optional: ")
                                                  .append(bodyText.contains("test optional") ? "✔" : "✘")
                                                  .append("\n");

                                            result.append("     ↳ TOEFL Required (Intl): ")
                                                  .append(bodyText.contains("toefl required") ? "✔" : "✘")
                                                  .append("\n\n");

                                        } catch (Exception ignored) {
                                            result.append("     ↳ Failed to open or parse embedded link.\n\n");
                                        }

                                        break;
                                    }
                                }

                                continue outerLoop;
                            }
                        }
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
        System.out.println("this is just to fix git");
        System.out.println("one more time");
    }
}
