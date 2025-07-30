
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



import com.formdev.flatlaf.FlatLightLaf;



public class toDB extends javax.swing.JFrame {
    private javax.swing.JTextArea ocrTextArea;

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainWindow.class.getName());

   
    public toDB() {
        initComponents();
            
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        offloadToDB = new javax.swing.JButton();
        groupFiles = new javax.swing.JButton();
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

        offloadToDB.setBackground(new java.awt.Color(255, 204, 153));
        offloadToDB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        offloadToDB.setText("Offload to Database");
        offloadToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offloadToDBActionPerformed(evt);
            }
        });

        groupFiles.setBackground(new java.awt.Color(255, 204, 153));
        groupFiles.setText("Group Files");
        groupFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupFilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(offloadToDB, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(groupFiles)))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(groupFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offloadToDB)
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

    private void offloadToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offloadToDBActionPerformed
        
    }//GEN-LAST:event_offloadToDBActionPerformed

    private void groupFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupFilesActionPerformed
       JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setDialogTitle("Select Folder to Group Similar Files");

    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File directory = chooser.getSelectedFile();

        // Create progress dialog
        JDialog progressDialog = new JDialog(this, "Processing Files", true);
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

        // Start background worker
        new SwingWorker<Map<String, List<String>>, ProgressUpdate>() {
            private int fileCount = 0;
            private Map<String, List<String>> baseNameMap = new HashMap<>();

            @Override
            protected Map<String, List<String>> doInBackground() throws Exception {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
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

                    progressDialog.dispose(); // Close progress dialog automatically

                    if (!result.isEmpty()) {
                        showGroupingResults(directory, result);
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

// Updated base name extractor for grouping
private String extractBaseName(String filename) {
    String name = filename.replaceFirst("\\.[^.]+$", "");
    name = name.replaceFirst("\\s*\\d.*$", "");
    return name.trim().replaceAll("[-_]+$", "").trim();
}

// Show the grouped results in a JTable dialog
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
    }//GEN-LAST:event_groupFilesActionPerformed

    

    
    
    
    
    
    
    
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
    private javax.swing.JButton databaseBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton groupFiles;
    private javax.swing.JButton historyBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton ocrPass;
    private javax.swing.JButton offloadToDB;
    private javax.swing.JButton screenshotsBtn;
    private javax.swing.JPanel sidePanel;
    // End of variables declaration//GEN-END:variables
}