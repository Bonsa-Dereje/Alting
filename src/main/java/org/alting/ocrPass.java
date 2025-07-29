
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

import java.awt.Desktop;



import com.formdev.flatlaf.FlatLightLaf;



public class ocrPass extends javax.swing.JFrame {
    private javax.swing.JTextArea ocrTextArea;

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainWindow.class.getName());

   
    public ocrPass() {
        initComponents();
        ocrTextArea = new javax.swing.JTextArea();
        ocrTextArea.setEditable(false);
        ocrTextArea.setLineWrap(true);
        ocrTextArea.setWrapStyleWord(true);
        ocrTextArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        ocrTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));

        // Link the JTextArea to the existing JScrollPane (ocrLiveRoll)
        ocrLiveRoll.setViewportView(ocrTextArea);

        
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ocrLiveRoll = new javax.swing.JScrollPane();
        tessOCR = new javax.swing.JButton();
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

        tessOCR.setBackground(new java.awt.Color(255, 204, 153));
        tessOCR.setText("Passthrough Tesseract");
        tessOCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tessOCRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(tessOCR)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ocrLiveRoll, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tessOCR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ocrLiveRoll)
                .addContainerGap())
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        try {
        File folder = new File("D:\\altingData\\imgDataset");
        if (folder.exists() && folder.isDirectory()) {
            Desktop.getDesktop().open(folder);
        } else {
            JOptionPane.showMessageDialog(this, "Folder does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Could not open folder.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    
    }//GEN-LAST:event_screenshotsBtnActionPerformed

    private void databaseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_databaseBtnActionPerformed

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportBtnActionPerformed

    private void tessOCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tessOCRActionPerformed
        new Thread(() -> {
        File inputFolder = new File("D:\\altingData\\imgDataset\\");
        File outputFolder = new File("D:\\altingData\\ocrOutput\\");

        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        File[] files = inputFolder.listFiles();

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "No image files found.");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName().toLowerCase();

                // Check image file extensions
                if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                    File outputFile = new File(outputFolder, nameWithoutExt + "_OCRed");

                    try {
                        ProcessBuilder pb = new ProcessBuilder(
                            "tesseract",
                            file.getAbsolutePath(),
                            outputFile.getAbsolutePath() // Tesseract will auto-append .txt
                        );

                        pb.redirectErrorStream(true);
                        Process process = pb.start();
                        int exitCode = process.waitFor();

                        if (exitCode == 0) {
                            System.out.println("OCR complete: " + fileName);
                        } else {
                            System.err.println("Failed to OCR: " + fileName);
                        }
                    } catch (Exception e) {
                        System.err.println("Error with file " + fileName + ": " + e.getMessage());
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(null, "OCR process completed.");
    }).start();
    }//GEN-LAST:event_tessOCRActionPerformed

    

    
    
    
    
    
    
    
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
    private javax.swing.JButton historyBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane ocrLiveRoll;
    private javax.swing.JButton ocrPass;
    private javax.swing.JButton screenshotsBtn;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JButton tessOCR;
    // End of variables declaration//GEN-END:variables
}