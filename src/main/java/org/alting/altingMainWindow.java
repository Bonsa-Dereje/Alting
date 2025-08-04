
package org.alting;

import javax.swing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;

import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




import java.nio.file.Files;




import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JTextArea;


import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingWorker;


import java.awt.datatransfer.StringSelection;





public class altingMainWindow extends javax.swing.JFrame {
    
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(altingMainWindow.class.getName());
    
     public static File sessionMasterDirectory;
     public static File sessionImgDirectory;
     public static File sessionOCRDirectory;
     File selectedDir = null;
     File outPutFolder = null;
     public boolean isDirSelected;
    
    private JTextArea logsDisp;
    private JButton refreshBtn;
    private JButton chooseDirBtn;
    private File imageSaveDirectory;
    
    private JProgressBar ocrProgressBar;
    private JTextArea ocrOutputArea;
    
    
    

   
    public altingMainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        appName = new javax.swing.JLabel();
        collegeSearchBtn = new javax.swing.JButton();
        ocrParseBtn = new javax.swing.JButton();
        consolidateBtn = new javax.swing.JButton();
        groupBtn = new javax.swing.JButton();
        exportBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dirSelectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(232, 227, 224));

        appName.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 12)); // NOI18N
        appName.setText("ALTING");

        collegeSearchBtn.setBackground(new java.awt.Color(255, 204, 153));
        collegeSearchBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        collegeSearchBtn.setText("College Search");
        collegeSearchBtn.setIconTextGap(2);
        collegeSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collegeSearchBtnActionPerformed(evt);
            }
        });

        ocrParseBtn.setBackground(new java.awt.Color(255, 204, 153));
        ocrParseBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ocrParseBtn.setText("OCR Parse");
        ocrParseBtn.setIconTextGap(2);
        ocrParseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ocrParseBtnActionPerformed(evt);
            }
        });

        consolidateBtn.setBackground(new java.awt.Color(255, 204, 153));
        consolidateBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        consolidateBtn.setText("Consolidate");
        consolidateBtn.setIconTextGap(2);
        consolidateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consolidateBtnActionPerformed(evt);
            }
        });

        groupBtn.setBackground(new java.awt.Color(255, 204, 153));
        groupBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        groupBtn.setText("Group");
        groupBtn.setIconTextGap(2);
        groupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupBtnActionPerformed(evt);
            }
        });

        exportBtn.setBackground(new java.awt.Color(255, 204, 153));
        exportBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        exportBtn.setText("Export");
        exportBtn.setIconTextGap(2);
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel1.setText("Developed by Boni");

        dirSelectBtn.setBackground(new java.awt.Color(255, 204, 153));
        dirSelectBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        dirSelectBtn.setText("Select a Directory");
        dirSelectBtn.setIconTextGap(2);
        dirSelectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirSelectBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(appName)
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(collegeSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ocrParseBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(consolidateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dirSelectBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(appName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dirSelectBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(collegeSearchBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ocrParseBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(groupBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consolidateBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            
    private void collegeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collegeSearchBtnActionPerformed
        if(isDirSelected == true){ 
            JOptionPane.showMessageDialog(this, "Make Sure Your Browser Is 1 alt + tab Away");
        String collegeUrlDatabase = "https://mally.stanford.edu/~sr/universities.html";
        File imageSaveDirectory = null;
        
        
        imageSaveDirectory = new File(sessionMasterDirectory, "imgDataset");
            
            sessionImgDirectory = imageSaveDirectory;
            if (!imageSaveDirectory.exists()) {
                imageSaveDirectory.mkdirs();
            }
       
        
        
        
        
        
        
        
        
        System.out.println(imageSaveDirectory);
        try {
            Document doc = Jsoup.connect(collegeUrlDatabase).get();
            Elements listItems = doc.select("li");
            int i = 0;

            for (Element li : listItems) {
                String fetchCollegeName = li.text().split("\\(")[0].trim();
                Element fetchCollegeLink = li.selectFirst("a[href]");
                String href = (fetchCollegeLink != null) ? fetchCollegeLink.attr("href") : "No Link";

                String[] rednSearchList =
                        {"Where is " + fetchCollegeName + " located",
                         "Is " + fetchCollegeName + " private",
                         fetchCollegeName + " acceptance rate",
                         "is the SAT required in " + fetchCollegeName,
                         "Does " + fetchCollegeName + " accept the CommonApp",
                         "Does " + fetchCollegeName + " accept quest bridge",
                         "Does " + fetchCollegeName + " have its own application site",
                         "Does " + fetchCollegeName + " require TOEFL for international students",
                         fetchCollegeName + " CSS profile code",
                         fetchCollegeName + " average SAT score",
                         fetchCollegeName + " average ACT score",
                         fetchCollegeName + " application fee",
                         "What is the yearly tuition of " + fetchCollegeName,
                         "Does " + fetchCollegeName + " offer application fee waivers",
                         "Eligibility requirements for international stundets for " + fetchCollegeName,
                         fetchCollegeName + " regular decision deadline",
                         fetchCollegeName + " early decision deadline",
                         fetchCollegeName + " early action deadline",
                         "is " + fetchCollegeName + "restrictive early action",
                         fetchCollegeName + " rolling admission deadline",
                         "Does " + fetchCollegeName + " offer grants",
                         "Does " + fetchCollegeName + " offer scholarships",
                         "Does " + fetchCollegeName + " offer merit scholarships",
                         "Does " + fetchCollegeName + " have a work study program",
                         "Does " + fetchCollegeName + " offer financial aid to international students",
                         "Does " + fetchCollegeName + " offer loans",
                         fetchCollegeName + " financial aid office's email address"};

                try {
                    Robot altingSim = new Robot();

                    if (i == 0) {
                        altingSim.keyPress(KeyEvent.VK_ALT);
                        altingSim.keyPress(KeyEvent.VK_TAB);
                        altingSim.keyRelease(KeyEvent.VK_TAB);
                        Thread.sleep(1000);
                        altingSim.keyPress(KeyEvent.VK_TAB);
                        altingSim.keyRelease(KeyEvent.VK_TAB);
                        Thread.sleep(3000);
                        altingSim.keyRelease(KeyEvent.VK_ALT);
                        Thread.sleep(3000);
                        altingSim.keyPress(KeyEvent.VK_CONTROL);
                        altingSim.keyPress(KeyEvent.VK_L);
                        altingSim.keyRelease(KeyEvent.VK_L);
                        altingSim.keyRelease(KeyEvent.VK_CONTROL);
                        Thread.sleep(3000);
                    }

                    for (int collegeIndex = 0; collegeIndex <= 24; collegeIndex++) {
                        String queryToType = rednSearchList[collegeIndex].replace("&", " and ");

                        for (char c : queryToType.toCharArray()) {
                            boolean upperCase = Character.isUpperCase(c);
                            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                            if (KeyEvent.CHAR_UNDEFINED == keyCode) continue;
                            if (upperCase) altingSim.keyPress(KeyEvent.VK_SHIFT);
                            altingSim.keyPress(keyCode);
                            altingSim.keyRelease(keyCode);
                            if (upperCase) altingSim.keyRelease(KeyEvent.VK_SHIFT);
                            if (upperCase) Thread.sleep(300);
                            Thread.sleep(50);
                        }

                        altingSim.keyPress(KeyEvent.VK_ENTER);
                        altingSim.keyRelease(KeyEvent.VK_ENTER);
                        Thread.sleep(5000);

                        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                        BufferedImage screenshot = altingSim.createScreenCapture(screenRect);
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String formattedDate = today.format(formatter);

                        try {
                            String fileName = fetchCollegeName + " " + collegeIndex + " .png";
                            File outputfile = new File(imageSaveDirectory, fileName);
                            
                            ImageIO.write(screenshot, "png", outputfile);
                            while (!outputfile.exists()) {
                                Thread.sleep(500);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String filePath = new File(sessionMasterDirectory, "log " + formattedDate + ".txt").getAbsolutePath();
                        try (PrintWriter logger = new PrintWriter(new FileWriter(filePath, true))) {
                            if (i == 0 && collegeIndex == 0) {
                                logger.println("Runtime logs  of " + formattedDate + " extr");
                            }
                            if (collegeIndex == 0) {
                                logger.print(i + " - For " + fetchCollegeName + " --rednSearchList-- ");
                            }
                            if (collegeIndex == 24) {
                                logger.println();
                            }
                            logger.print("[ " + collegeIndex + " ] ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Thread.sleep(3000);
                        altingSim.keyPress(KeyEvent.VK_CONTROL);
                        altingSim.keyPress(KeyEvent.VK_L);
                        Thread.sleep(500);
                        altingSim.keyRelease(KeyEvent.VK_L);
                        altingSim.keyRelease(KeyEvent.VK_CONTROL);
                        Thread.sleep(1000);
                        altingSim.keyPress(KeyEvent.VK_CONTROL);
                        altingSim.keyPress(KeyEvent.VK_A);
                        Thread.sleep(350);
                        altingSim.keyRelease(KeyEvent.VK_A);
                        altingSim.keyRelease(KeyEvent.VK_CONTROL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (i % 3 == 0) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                
                
                i++;
                if (i == 1) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }else{
            JOptionPane.showMessageDialog(this, "Parent Directory Not Selected");
        }
        
    }//GEN-LAST:event_collegeSearchBtnActionPerformed
        

   
    private void ocrParseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ocrParseBtnActionPerformed
         if (!isDirSelected) {
        JOptionPane.showMessageDialog(this, "Parent Directory Not Selected");
        return;
        }

        File inputFolder = altingMainWindow.sessionImgDirectory;
        File outputFolder = new File(sessionMasterDirectory, "ocrOutput");
        sessionOCRDirectory = outputFolder;

        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        File[] files = inputFolder.listFiles((dir, name) -> 
            name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")
        );

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "No image files found in the folder.");
            return;
        }

        // Create UI elements for live updates
        JDialog progressDialog = new JDialog(this, "OCR Progress", true);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.setSize(500, 300);
        progressDialog.setLocationRelativeTo(this);

        JProgressBar progressBar = new JProgressBar(0, files.length);
        progressBar.setStringPainted(true);

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        progressDialog.add(progressBar, BorderLayout.NORTH);
        progressDialog.add(scrollPane, BorderLayout.CENTER);

        // Run OCR in background
        SwingWorker<Void, String> ocrWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                int count = 0;
                for (File file : files) {
                    String fileName = file.getName();
                    String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                    String outputFileName = nameWithoutExt + "_OCRed";

                    File outputFile = new File(outputFolder, outputFileName);

                    try {
                        ProcessBuilder pb = new ProcessBuilder(
                            "tesseract",
                            file.getAbsolutePath(),
                            outputFile.getAbsolutePath()
                        );
                        pb.redirectErrorStream(true);
                        Process process = pb.start();
                        int exitCode = process.waitFor();

                        if (exitCode == 0) {
                            publish("Processed: " + fileName);
                        } else {
                            publish("Failed: " + fileName);
                        }
                    } catch (Exception e) {
                        publish("⚠️ Error with " + fileName + ": " + e.getMessage());
                    }

                    count++;
                    setProgress(count);
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    logArea.append(message + "\n");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                }
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                JOptionPane.showMessageDialog(null, "OCR processing complete.");
            }
        };

        ocrWorker.addPropertyChangeListener(evt1 -> {
            if ("progress".equals(evt1.getPropertyName())) {
                progressBar.setValue((Integer) evt1.getNewValue());
            }
        });

        ocrWorker.execute();

        // Show the dialog AFTER starting the worker so it won't block the EDT
        SwingUtilities.invokeLater(() -> progressDialog.setVisible(true));
    }//GEN-LAST:event_ocrParseBtnActionPerformed

    private void consolidateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consolidateBtnActionPerformed
        if(isDirSelected == true){
        File groupedFolder = new File(sessionMasterDirectory, "groupedParsed");

        
        File destinationFolder = new File(sessionMasterDirectory, "consolidatedParseFiles");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Begin consolidation
        File[] collegeFolders = groupedFolder.listFiles(File::isDirectory);
        if (collegeFolders == null || collegeFolders.length == 0) {
            JOptionPane.showMessageDialog(this, "No subfolders found in the selected grouped folder.");
            return;
        }

        // Progress dialog setup
        JDialog progressDialog = new JDialog(this, "Consolidating Files", true);
        progressDialog.setSize(400, 150);
        progressDialog.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("Starting...");
        JProgressBar progressBar = new JProgressBar(0, collegeFolders.length);
        progressBar.setStringPainted(true);

        progressDialog.add(statusLabel, BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);
        progressDialog.setLocationRelativeTo(this);

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int count = 0;

                for (File collegeFolder : collegeFolders) {
                    count++;
                    publish("Reading: " + collegeFolder.getName());

                    StringBuilder masterText = new StringBuilder();
                    File[] txtFiles = collegeFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

                    if (txtFiles == null || txtFiles.length == 0) {
                        publish("No .txt files in " + collegeFolder.getName() + ", skipping.");
                        progressBar.setValue(count);
                        continue;
                    }

                    for (File txtFile : txtFiles) {
                        try {
                            List<String> lines = Files.readAllLines(txtFile.toPath());
                            for (String line : lines) {
                                masterText.append(line).append(System.lineSeparator());
                            }
                            masterText.append(System.lineSeparator());
                        } catch (IOException e) {
                            publish("Failed to read " + txtFile.getName() + ": " + e.getMessage());
                        }
                    }

                    File outputFile = new File(destinationFolder, collegeFolder.getName() + " masterParsed.txt");

                    try {
                        Files.write(outputFile.toPath(), masterText.toString().getBytes());
                        publish("✅ Saved: " + outputFile.getName());
                    } catch (IOException e) {
                        publish("❌ Failed to write " + outputFile.getName() + ": " + e.getMessage());
                    }

                    progressBar.setValue(count);
                }

                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                String latest = chunks.get(chunks.size() - 1);
                statusLabel.setText(latest);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                JOptionPane.showMessageDialog(
                    altingMainWindow.this,
                    "Consolidated files are successfully saved in:\n" + destinationFolder.getAbsolutePath(),
                    "Done",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
        }else{
          JOptionPane.showMessageDialog(this, "Parent Directory Not Selected");
          
        }
        
    }//GEN-LAST:event_consolidateBtnActionPerformed

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBtnActionPerformed
                try {
            Robot robot = new Robot();
            int i = 0;

            if (i == 0) {
                // ALT + TAB to switch to browser
                robot.keyPress(KeyEvent.VK_ALT);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                Thread.sleep(3000);
                robot.keyRelease(KeyEvent.VK_ALT);
                Thread.sleep(3000);

                // CTRL + L to focus the URL bar
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(3000);

                // Type URL
                String url = "chat.deepseek.com";
                for (char ch : url.toCharArray()) {
                    typeChar(robot, ch);
                }

                // Press Enter after typing URL
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

                // Wait 5 seconds before typing SQL message
                Thread.sleep(9000);

                // Full SQL message
                String longMessage = """
                    I'll be sending you txt files, you'll extract info that will be entered to this database later. Create a JSON file that will later be used to enter the info to my sql database. 

                    create database collegeInfo;
                    use collegeInfo;

                    create table collegeData
                        (collegeName varchar(250),
                        country varchar(15),
                        stateLocation varchar(50),
                        privateCollege varchar(3),
                        acceptanceRate decimal(3,2),
                        satReq varchar(30),
                        commonAppReq varchar(30),
                        questBridge varchar(30) NULL,
                        proprieteryApp varchar(30) NULL,
                        proprieteryAppLink varchar(100) NOT NULL,
                        toeflReq varchar(100) NOT NULL,
                        cssProfileCode int PRIMARY KEY,
                        avgSAT int,
                        avgACT int,
                        appFee int,
                        tuition int,
                        appFeeWaiver varchar(20),
                        intlEligiblityReq varchar(50),
                        fafsaForm varbinary(MAX),
                        lastUpdated datetime DEFAULT getdate());

                    select *From collegeData;
                    drop table collegeData;
                    alter table collegeData 
                        add lastUpdated datetime DEFAULT getdate();
                    alter table collegeData
                        drop column finAidOfficeEmail;
                    ALTER TABLE collegeData
                    ADD  finAidOfficeEmail VARCHAR(20);

                    create table financialAid
                        (cssProfileCode int PRIMARY KEY,
                        grantsAV varchar(3) NOT NULL,
                            grantsFullInfo TEXT,
                        scholarshipsAV varchar(3) NOT NULL,
                            scholarshipFullInfo TEXT,
                        meritScholarshipsAV varchar(3) NOT NULL,
                            meritScholarshipFullInfo TEXT,
                        workStudyAV varchar(3),
                            workStudyFullInfo TEXT,
                        intlFinancialAidAV varchar(3),
                            intlFinancialAidFullInfo TEXT,
                        loansAV varchar(3),
                            loansFullInfo TEXT,
                        finAidOfficeEmail varchar(50),
                        CONSTRAINT cssCodeReference FOREIGN KEY (cssProfileCode) 
                            REFERENCES collegeData(cssProfileCode));

                    drop table financialAid;

                    create table deadlines
                        (cssProfileCode int PRIMARY KEY,
                        regularDecision varchar(15),
                        earlyDecision varchar(15),
                        earlyAction varchar(15),
                        restrictiveEarlyAction varchar(15),
                        rollingAdmission varchar(15),
                        CONSTRAINT cssReference FOREIGN KEY (cssProfileCode) 
                            REFERENCES collegeData(cssProfileCode));

                    drop table deadlines;
                    """;

                for (char ch : longMessage.toCharArray()) {
                    if (ch == '\n') {
                        // Use Shift + Enter for newline
                        robot.keyPress(KeyEvent.VK_SHIFT);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    } else if (ch == ' ') {
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                        Thread.sleep(100); // space delay
                    } else {
                        typeChar(robot, ch);

                        // Delay for capital letters
                        if (Character.isUpperCase(ch)) {
                            Thread.sleep(100);
                        }
                    }

                    // General delay
                    Thread.sleep(5);
                }

                // Final ENTER to send the message
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_exportBtnActionPerformed

    
    
        private void typeChar(Robot robot, char character) {
        try {
            boolean upperCase = Character.isUpperCase(character);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);

            if (keyCode == KeyEvent.VK_UNDEFINED) return;

            if (upperCase || isSpecialShiftChar(character)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase || isSpecialShiftChar(character)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        } catch (IllegalArgumentException e) {
            if (character == '\n') {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } else if (character == ' ') {
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
        }
    }

    // SHIFT-needed characters
    private boolean isSpecialShiftChar(char ch) {
        return "~!@#$%^&*()_+{}|:\"<>?".indexOf(ch) >= 0;
    }
    
    
    
    private void groupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupBtnActionPerformed
            if(isDirSelected == true){
                System.out.println("you good :)");
                }else{
            JOptionPane.showMessageDialog(this, "Parent Directory Not Selected");
            }
            
                
            
                File directory = new File(sessionMasterDirectory, "ocrOutput");
                if (!directory.exists()) directory.mkdirs();

                // Create scanning progress dialog
                JDialog progressDialog = new JDialog(this, "Scanning Files", true);
                progressDialog.setSize(400, 200);
                progressDialog.setLayout(new BorderLayout());

                JLabel statusLabel = new JLabel("Starting file scan...");
                JTextArea livePreview = new JTextArea();
                livePreview.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(livePreview);

                JProgressBar progressBar = new JProgressBar();
                progressBar.setStringPainted(true);

                JPanel progressPanel = new JPanel(new BorderLayout());
                progressPanel.add(statusLabel, BorderLayout.NORTH);
                progressPanel.add(progressBar, BorderLayout.CENTER);

                progressDialog.add(progressPanel, BorderLayout.NORTH);
                progressDialog.add(scrollPane, BorderLayout.CENTER);
                progressDialog.setLocationRelativeTo(this);

                new SwingWorker<Map<String, List<String>>, ProgressUpdate>() {
                    private int fileCount = 0;
                    private Map<String, List<String>> baseNameMap = new HashMap<>();

                    @Override
                    protected Map<String, List<String>> doInBackground() throws Exception {
                        File[] files = directory.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                                    String filename = file.getName();
                                    String baseName = extractBaseName(filename);

                                    baseNameMap.computeIfAbsent(baseName, k -> new ArrayList<>()).add(filename);
                                    fileCount++;

                                    publish(new ProgressUpdate(
                                        String.format("%d. %s → %s", fileCount, filename, baseName),
                                        fileCount,
                                        files.length
                                    ));
                                    if (fileCount % 50 == 0) Thread.sleep(2000);
                                }
                                Thread.sleep(20);
                            }
                        }
                        return baseNameMap;
                    }

                    @Override
                    protected void process(List<ProgressUpdate> chunks) {
                        for (ProgressUpdate update : chunks) {
                            livePreview.append(update.message + "\n");
                            livePreview.setCaretPosition(livePreview.getDocument().getLength());

                            progressBar.setMaximum(update.total);
                            progressBar.setValue(update.progress);
                            statusLabel.setText(String.format("Processed %d/%d files", update.progress, update.total));
                        }
                    }

                    @Override
                    protected void done() {
                        try {
                            Map<String, List<String>> result = get();
                            result.values().removeIf(list -> list.size() <= 1);

                            progressDialog.dispose();

                            if (!result.isEmpty()) {
                                showGroupingResults(directory, result);

                                File groupedRoot = new File(sessionMasterDirectory, "groupedParsed");

                                    if (!groupedRoot.exists()) groupedRoot.mkdirs();

                                    // Prepare for copy progress
                                    List<File[]> filesToCopy = new ArrayList<>();
                                    for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                                        String groupName = entry.getKey();
                                        List<String> matchedFiles = entry.getValue();

                                        File groupFolder = new File(groupedRoot, groupName);
                                        if (!groupFolder.exists()) groupFolder.mkdirs();

                                        for (String fileName : matchedFiles) {
                                            File srcFile = new File(directory, fileName);
                                            File destFile = new File(groupFolder, fileName);
                                            filesToCopy.add(new File[]{srcFile, destFile});
                                        }
                                    }

                                    // Copy progress dialog
                                    JDialog copyDialog = new JDialog(altingMainWindow.this, "Copying Files", true);
                                    copyDialog.setSize(400, 150);
                                    copyDialog.setLayout(new BorderLayout());

                                    JLabel copyStatus = new JLabel("Preparing to copy...");
                                    JProgressBar copyBar = new JProgressBar(0, filesToCopy.size());
                                    copyBar.setStringPainted(true);

                                    copyDialog.add(copyStatus, BorderLayout.NORTH);
                                    copyDialog.add(copyBar, BorderLayout.CENTER);
                                    copyDialog.setLocationRelativeTo(altingMainWindow.this);

                                    new SwingWorker<Void, String>() {
                                        @Override
                                        protected Void doInBackground() throws Exception {
                                            int count = 0;
                                            for (File[] pair : filesToCopy) {
                                                File src = pair[0];
                                                File dest = pair[1];
                                                count++;

                                                try {
                                                    Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                                } catch (IOException e) {
                                                    System.err.println("Failed to copy " + src.getName() + ": " + e.getMessage());
                                                }

                                                publish(String.format("Copying %d/%d: %s", count, filesToCopy.size(), src.getName()));
                                                copyBar.setValue(count);
                                                Thread.sleep(10); // Optional slow-down to observe progress
                                            }
                                            return null;
                                        }

                                        @Override
                                        protected void process(List<String> chunks) {
                                            String latest = chunks.get(chunks.size() - 1);
                                            copyStatus.setText(latest);
                                        }

                                        @Override
                                        protected void done() {
                                            copyDialog.dispose();
                                            JOptionPane.showMessageDialog(
                                                altingMainWindow.this,
                                                "Grouped files copied to:\n" + groupedRoot.getAbsolutePath(),
                                                "Copy Complete",
                                                JOptionPane.INFORMATION_MESSAGE
                                            );
                                        }
                                    }.execute();

                                    copyDialog.setVisible(true);
                                }

                           
                            
                        } catch (Exception e) {
                            progressDialog.dispose();
                            JOptionPane.showMessageDialog(
                                altingMainWindow.this,
                                "Error processing files: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }.execute();

                progressDialog.setVisible(true);
            
        }

        // Helper class for progress updates
        private static class ProgressUpdate {
            final String message;
            final int progress;
            final int total;

            ProgressUpdate(String message, int progress, int total) {
                this.message = message;
                this.progress = progress;
                this.total = total;
            }
        }

        // Extracts base name for grouping
        private String extractBaseName(String filename) {
            String name = filename.replaceFirst("\\.[^.]+$", "");
            name = name.replaceFirst("\\s*\\d.*$", "");
            return name.trim().replaceAll("[-_]+$", "").trim();
        }

        // Show grouped result in JTable
        private void showGroupingResults(File directory, Map<String, List<String>> groups) {
            String[] columnNames = {"Group Name", "Matches Found"};
            Object[][] data = new Object[groups.size()][2];

            int i = 0;
            for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
                data[i][0] = entry.getKey();
                data[i][1] = entry.getValue().size();
                i++;
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Grouped Files in: " + directory.getAbsolutePath(),
                JOptionPane.INFORMATION_MESSAGE
            );
     
    }//GEN-LAST:event_groupBtnActionPerformed

    private void dirSelectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dirSelectBtnActionPerformed
        
        JOptionPane.showMessageDialog(this, "Select the master parent directory");
        JFileChooser saveChooser = new JFileChooser();
        saveChooser.setDialogTitle("Directory location");
        saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = saveChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = saveChooser.getSelectedFile();
            
            sessionMasterDirectory = selectedDir;
            isDirSelected = true;
            
            imageSaveDirectory = new File(sessionMasterDirectory, "imgDataset");
            
            sessionImgDirectory = imageSaveDirectory;
         JOptionPane.showMessageDialog(this, sessionMasterDirectory + " selected");
        } else {
            JOptionPane.showMessageDialog(this, "No directory selected. Exiting.");
            return;
        }
    }//GEN-LAST:event_dirSelectBtnActionPerformed
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
         try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (UnsupportedLookAndFeelException ex) {
        logger.log(java.util.logging.Level.SEVERE, null, ex);
    }

         
    java.awt.EventQueue.invokeLater(() -> new altingMainWindow().setVisible(true));
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appName;
    private javax.swing.JButton collegeSearchBtn;
    private javax.swing.JButton consolidateBtn;
    private javax.swing.JButton dirSelectBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton groupBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton ocrParseBtn;
    // End of variables declaration//GEN-END:variables
}