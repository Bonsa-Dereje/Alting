
package org.alting;

// Existing imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// New imports for live updates
import javax.swing.SwingWorker;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



import java.awt.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileWriter;







import com.formdev.flatlaf.FlatLightLaf;



public class toDB extends javax.swing.JFrame {
    private javax.swing.JTextArea ocrTextArea;
    private JProgressBar progressBar;
    private JTextArea logArea;
    private JLabel statusLabel;


    
    private void createMasterParsedFiles(File groupedFolder) {
    if (!groupedFolder.exists() || !groupedFolder.isDirectory()) {
        JOptionPane.showMessageDialog(this, "Invalid 'grouped' folder selected.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    File[] collegeFolders = groupedFolder.listFiles(File::isDirectory);
    if (collegeFolders == null) return;

    for (File collegeFolder : collegeFolders) {
        StringBuilder masterText = new StringBuilder();

        File[] txtFiles = collegeFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (txtFiles == null || txtFiles.length == 0) continue;

        for (File txtFile : txtFiles) {
            try {
                List<String> lines = Files.readAllLines(txtFile.toPath());
                for (String line : lines) {
                    masterText.append(line).append(System.lineSeparator());
                }
                masterText.append(System.lineSeparator()); // spacing between files
            } catch (IOException e) {
                System.err.println("Failed to read " + txtFile.getName() + ": " + e.getMessage());
            }
        }

        String collegeName = collegeFolder.getName().trim();
        File outputFile = new File(collegeFolder, collegeName + " masterParsed.txt");

        try {
            Files.write(outputFile.toPath(), masterText.toString().getBytes());
            System.out.println("Written: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write " + outputFile.getName() + ": " + e.getMessage());
        }
    }

    JOptionPane.showMessageDialog(this, "All masterParsed files created successfully!", "Done", JOptionPane.INFORMATION_MESSAGE);
}


    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainWindow.class.getName());

   
    public toDB() {
        initComponents();
            
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        offloadToDBbtn = new javax.swing.JButton();
        groupFilesBtn = new javax.swing.JButton();
        consolidateBtn = new javax.swing.JButton();
        sidePanel = new javax.swing.JPanel();
        appName = new javax.swing.JLabel();
        collegeSearch = new javax.swing.JButton();
        historyBtn = new javax.swing.JButton();
        ocrPass = new javax.swing.JButton();
        screenshotsBtn = new javax.swing.JButton();
        databaseBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        exportBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 248, 249));
        jPanel1.setForeground(new java.awt.Color(248, 248, 248));

        offloadToDBbtn.setBackground(new java.awt.Color(255, 204, 153));
        offloadToDBbtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        offloadToDBbtn.setText("Offload to Database");
        offloadToDBbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offloadToDBbtnActionPerformed(evt);
            }
        });

        groupFilesBtn.setBackground(new java.awt.Color(255, 204, 153));
        groupFilesBtn.setText("Group Files");
        groupFilesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupFilesBtnActionPerformed(evt);
            }
        });

        consolidateBtn.setBackground(new java.awt.Color(255, 204, 153));
        consolidateBtn.setText("Consolidate");
        consolidateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consolidateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(consolidateBtn)
                            .addComponent(groupFilesBtn)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(offloadToDBbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(groupFilesBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consolidateBtn)
                .addGap(12, 12, 12)
                .addComponent(offloadToDBbtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidePanel.setBackground(new java.awt.Color(232, 227, 224));
        sidePanel.setForeground(new java.awt.Color(194, 175, 76));

        appName.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 12)); // NOI18N
        appName.setText("ALTING");

        collegeSearch.setBackground(new java.awt.Color(255, 204, 153));
        collegeSearch.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        collegeSearch.setText("College Search");
        collegeSearch.setIconTextGap(2);
        collegeSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collegeSearchActionPerformed(evt);
            }
        });

        historyBtn.setBackground(new java.awt.Color(255, 204, 153));
        historyBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        historyBtn.setText("Logs");
        historyBtn.setIconTextGap(2);
        historyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyBtnActionPerformed(evt);
            }
        });

        ocrPass.setBackground(new java.awt.Color(255, 204, 153));
        ocrPass.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ocrPass.setText("OCR Pass");
        ocrPass.setIconTextGap(2);
        ocrPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ocrPassActionPerformed(evt);
            }
        });

        screenshotsBtn.setBackground(new java.awt.Color(255, 204, 153));
        screenshotsBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        screenshotsBtn.setText("Screenshots");
        screenshotsBtn.setIconTextGap(2);
        screenshotsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                screenshotsBtnActionPerformed(evt);
            }
        });

        databaseBtn.setBackground(new java.awt.Color(255, 204, 153));
        databaseBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        databaseBtn.setText("Extracted Database");
        databaseBtn.setIconTextGap(2);
        databaseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel1.setText("Developed by Boni");

        exportBtn.setBackground(new java.awt.Color(255, 204, 153));
        exportBtn.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        exportBtn.setText("Export");
        exportBtn.setIconTextGap(2);
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(appName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(historyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(collegeSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ocrPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(screenshotsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(databaseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(exportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(appName, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(collegeSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(historyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(screenshotsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ocrPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(databaseBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            
    private void collegeSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collegeSearchActionPerformed
        
        
        
    }//GEN-LAST:event_collegeSearchActionPerformed

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
       
        LogsWindow logsWindow = new LogsWindow();
        logsWindow.setVisible(true);
         this.dispose();
      
    }//GEN-LAST:event_historyBtnActionPerformed

    private void ocrPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ocrPassActionPerformed
         
    }//GEN-LAST:event_ocrPassActionPerformed

    private void screenshotsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_screenshotsBtnActionPerformed
      
    
    }//GEN-LAST:event_screenshotsBtnActionPerformed

    private void databaseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_databaseBtnActionPerformed

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportBtnActionPerformed

    private void offloadToDBbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offloadToDBbtnActionPerformed
        
    }//GEN-LAST:event_offloadToDBbtnActionPerformed

    private void groupFilesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupFilesBtnActionPerformed
      JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setDialogTitle("Select Folder to Group Similar Files");

    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File directory = chooser.getSelectedFile();

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

                        // Ask where to save grouped files
                        JFileChooser saveChooser = new JFileChooser();
                        saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        saveChooser.setDialogTitle("Select Destination for Grouped Files");

                        if (saveChooser.showSaveDialog(toDB.this) == JFileChooser.APPROVE_OPTION) {
                            File destination = saveChooser.getSelectedFile();
                            File groupedRoot = new File(destination, "groupedParsed");

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
                            JDialog copyDialog = new JDialog(toDB.this, "Copying Files", true);
                            copyDialog.setSize(400, 150);
                            copyDialog.setLayout(new BorderLayout());

                            JLabel copyStatus = new JLabel("Preparing to copy...");
                            JProgressBar copyBar = new JProgressBar(0, filesToCopy.size());
                            copyBar.setStringPainted(true);

                            copyDialog.add(copyStatus, BorderLayout.NORTH);
                            copyDialog.add(copyBar, BorderLayout.CENTER);
                            copyDialog.setLocationRelativeTo(toDB.this);

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
                                        toDB.this,
                                        "Grouped files copied to:\n" + groupedRoot.getAbsolutePath(),
                                        "Copy Complete",
                                        JOptionPane.INFORMATION_MESSAGE
                                    );
                                }
                            }.execute();

                            copyDialog.setVisible(true);
                        }

                    } else {
                        JOptionPane.showMessageDialog(
                            toDB.this,
                            String.format("Processed %d files but found no matching groups", fileCount),
                            "No Matches Found",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception e) {
                    progressDialog.dispose();
                    JOptionPane.showMessageDialog(
                        toDB.this,
                        "Error processing files: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();

        progressDialog.setVisible(true);
    }
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
    }//GEN-LAST:event_groupFilesBtnActionPerformed

    private void consolidateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consolidateBtnActionPerformed
        JOptionPane.showMessageDialog(this,
        "Step 1: Select the folder that contains grouped college subfolders.\n" +
        "Each subfolder should have .txt files to be consolidated.",
        "Choose Grouped Folder",
        JOptionPane.INFORMATION_MESSAGE);

        JFileChooser sourceChooser = new JFileChooser();
        sourceChooser.setDialogTitle("Select the 'grouped' folder");
        sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int sourceResult = sourceChooser.showOpenDialog(this);
        if (sourceResult != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No grouped folder selected.");
            return;
        }
        File groupedFolder = sourceChooser.getSelectedFile();

        // Step 2: Prompt to select destination root
        JOptionPane.showMessageDialog(this,
            "Step 2: Choose where the consolidated files should be saved.\n" ,
            
            "Choose Destination Folder",
            JOptionPane.INFORMATION_MESSAGE);

        JFileChooser destChooser = new JFileChooser();
        destChooser.setDialogTitle("Select destination folder");
        destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int destResult = destChooser.showOpenDialog(this);
        if (destResult != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No destination folder selected.");
            return;
        }

        // Create subfolder named "consolidateParseFiles" inside the selected destination
        File destinationFolder = new File(destChooser.getSelectedFile(), "consolidatedParseFiles");
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
                    toDB.this,
                    "🎉 All masterParsed files saved successfully in:\n" + destinationFolder.getAbsolutePath(),
                    "Done",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }//GEN-LAST:event_consolidateBtnActionPerformed

    

    
    
    
    
    
    
    
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

    java.awt.EventQueue.invokeLater(() -> new ocrPass().setVisible(true));
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appName;
    private javax.swing.JButton collegeSearch;
    private javax.swing.JButton consolidateBtn;
    private javax.swing.JButton databaseBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton groupFilesBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton ocrPass;
    private javax.swing.JButton offloadToDBbtn;
    private javax.swing.JButton screenshotsBtn;
    private javax.swing.JPanel sidePanel;
    // End of variables declaration//GEN-END:variables
}