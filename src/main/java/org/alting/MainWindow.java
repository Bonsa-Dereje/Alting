
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



public class MainWindow extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainWindow.class.getName());

   
    public MainWindow() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        extractBtn = new javax.swing.JButton();
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

        extractBtn.setBackground(new java.awt.Color(255, 204, 153));
        extractBtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        extractBtn.setText("Extract");
        extractBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extractBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(extractBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(extractBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
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
                  ocrPass ocrPass = new ocrPass();
         ocrPass.setVisible(true);
         this.dispose();
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

    private void extractBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extractBtnActionPerformed
        //String userInput = searchSubjectInput.getText();
        
            String collegeUrlDatabase = "https://mally.stanford.edu/~sr/universities.html";
    
    try{
        Document doc = Jsoup.connect(collegeUrlDatabase).get();
        Elements listItems = doc.select("li");
        //boolean found = false;
        int i = 0;
        
        
        for(Element li: listItems){
            
                String fetchCollegeName = li.text().split("\\(")[0].trim();
                Element fetchCollegeLink = li.selectFirst("a[href]");
                String href = (fetchCollegeLink != null) ? fetchCollegeLink.attr("href") : "No Link";
                
                
                System.out.println(i+" - Name: " + fetchCollegeName);
                System.out.println("Link " + href);
                System.out.println("--------------------");
                
         
                String[] rednSearchList = 
                     {"Where is " + fetchCollegeName + " located",
                        "Is " + fetchCollegeName + " private",
                        fetchCollegeName + " acceptance rate",
                        "is the SAT required in " + fetchCollegeName,
                        "Does " + fetchCollegeName + " accept the CommonApp",
                        "Does " + fetchCollegeName + " accept quest bridge",
                        "Does " + fetchCollegeName + " have its own application site",
                        //exctr link smh
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
                        //follow link and extr
                        "Does " + fetchCollegeName + " offer scholarships",
                        //follow link and extr
                        "Does " + fetchCollegeName + " offer merit scholarships",
                        //follow link and extr
                        "Does " + fetchCollegeName + " have a work study program",
                        //follow link and extr
                        "Does " + fetchCollegeName + " offer financial aid to international students",
                        //follow link and extr IS IT FULL, if not how much 
                        "Does " + fetchCollegeName + " offer loans",
                        //follow link and extr
                        fetchCollegeName + " financial aid office's email address"
                        
                    };
    //exctr link smh
                    try{
                    Robot altingSim = new Robot();
                    
                    if(i==0){
                    
                    altingSim.keyPress(KeyEvent.VK_ALT);
                    altingSim.keyPress(KeyEvent.VK_TAB);
                    altingSim.keyRelease(KeyEvent.VK_TAB);
                    try {
                        Thread.sleep(1000); // Wait for 1.5 seconds
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                        
                    //altingSim.keyPress(KeyEvent.VK_TAB);
                    //altingSim.keyRelease(KeyEvent.VK_TAB);
                    
                    //altingSim.keyPress(KeyEvent.VK_TAB);
                    //altingSim.keyRelease(KeyEvent.VK_TAB);
                    
                    altingSim.keyPress(KeyEvent.VK_TAB);
                    altingSim.keyRelease(KeyEvent.VK_TAB);
                    
                    try {
                        Thread.sleep(3000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    
                    altingSim.keyRelease(KeyEvent.VK_ALT);
                    
                    try {
                        Thread.sleep(3000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }

                    //altingSim.keyRelease(KeyEvent.VK_ALT);
                    
                    altingSim.keyPress(KeyEvent.VK_CONTROL);
                    altingSim.keyPress(KeyEvent.VK_L);
                    altingSim.keyRelease(KeyEvent.VK_L);
                    altingSim.keyRelease(KeyEvent.VK_CONTROL);
                    
                    //altingSim.keyPress(KeyEvent.VK_TAB);
                    //altingSim.keyRelease(KeyEvent.VK_TAB);
                    
                    /*
                    altingSim.keyPress(KeyEvent.VK_CONTROL);
                    altingSim.keyPress(KeyEvent.VK_L);
                    altingSim.keyRelease(KeyEvent.VK_L);
                    altingSim.keyRelease(KeyEvent.VK_CONTROL);
                    */
                    
                    
                    try {
                        Thread.sleep(3000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    
                    }
                    
                    if(i>=0){
                    
                    for( int collegeIndex = 0; collegeIndex <=24; collegeIndex++){
                        
                        
                        
                        
                    
                    String queryToType = rednSearchList[collegeIndex];

// Type the characters of the query one by one
                String updatedQuery = queryToType.replace("&", " and ");
                
                for (char c : updatedQuery.toCharArray()) {
                    boolean upperCase = Character.isUpperCase(c);
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                       if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                continue;
                }

                if (upperCase) altingSim.keyPress(KeyEvent.VK_SHIFT);

                  altingSim.keyPress(keyCode);
                altingSim.keyRelease(keyCode);

                if (upperCase) altingSim.keyRelease(KeyEvent.VK_SHIFT);
                if (upperCase) try {
                    Thread.sleep(300); // Simulate human typing delay
                } catch (InterruptedException e) {
                 e.printStackTrace();
                }

                 try {
                    Thread.sleep(50); // Simulate human typing delay
                } catch (InterruptedException e) {
                 e.printStackTrace();
                }
            }
                
                altingSim.keyPress(KeyEvent.VK_ENTER);
                altingSim.keyRelease(KeyEvent.VK_ENTER);
                
                try {
                     Thread.sleep(5000); 
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }

                    
                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage screenshot = altingSim.createScreenCapture(screenRect);

                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String formattedDate = today.format(formatter);
                try {
                    String fileName = fetchCollegeName +" "+ collegeIndex + " .png";
                    //File outputfile = new File(fileName);
                    String directoryPath = "D:\\altingData\\imgDataset";
                    File dir = new File(directoryPath);
                    
                    File outputfile = new File(dir, fileName);
                    ImageIO.write(screenshot, "png", outputfile);
                     System.out.println("Screenshot saved: " + outputfile.getAbsolutePath());
                     
                  while (!outputfile.exists()) {
                    try {
                        Thread.sleep(500); 
                    } catch (InterruptedException e) {
                         e.printStackTrace();
                        }
                    }    
                } catch (IOException e) {
                     e.printStackTrace();
                    }
                 String filePath = "D:\\altingData\\log " +formattedDate +".txt";
                    try (PrintWriter logger = new PrintWriter(new FileWriter(filePath, true))) {
                        if(i==0){
                            if(collegeIndex==0){
                           logger.println("Runtime logs  of " + formattedDate + " extr");  
                        }
                        }
                        //logger.println("-------------------------");
                        if(collegeIndex == 0){
                            logger.print(i+" - For " + fetchCollegeName + " --rednSearchList-- ");
                        }
                        if(collegeIndex == 24){
                            logger.println();
                        }
                       
                        logger.print("[ " + collegeIndex + " ] ");
                        //logger.println(" --- Screenshot saved: " + outputfile.getAbsolutePath());
                        //logger.println();
                        System.out.println("✅ Log written.");
                    } catch (IOException e) {
                        System.out.println("❌ Error writing log: " + e.getMessage());
                        e.printStackTrace();
                    }
                
                
               try {
                        Thread.sleep(3000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }

                
                
               try {
                        Thread.sleep(3000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }

                    //altingSim.keyRelease(KeyEvent.VK_ALT);
                    
                    altingSim.keyPress(KeyEvent.VK_CONTROL);
                    altingSim.keyPress(KeyEvent.VK_L);
                    try {
                        Thread.sleep(500); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    altingSim.keyRelease(KeyEvent.VK_L);
                    altingSim.keyRelease(KeyEvent.VK_CONTROL);
                    
                    try {
                        Thread.sleep(1000); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    
                    altingSim.keyPress(KeyEvent.VK_CONTROL);
                    altingSim.keyPress(KeyEvent.VK_A);
                    try {
                        Thread.sleep(350); 
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    altingSim.keyRelease(KeyEvent.VK_A);
                    altingSim.keyRelease(KeyEvent.VK_CONTROL);
                    }
                    
                    }
                    
                    
                    }catch(AWTException e){
                        e.printStackTrace();
                    }
                    
                    
    
    
            if(i%3 == 0){
                try {
                    Thread.sleep(5); // Wait 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                    
                    
    
    
                
                i++;
                if(i==1100){
                    break;}
            }
                
        }       catch(IOException e){
               System.out.println("Error");
}
                   
    }//GEN-LAST:event_extractBtnActionPerformed
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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

    java.awt.EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appName;
    private javax.swing.JButton collegeSearch;
    private javax.swing.JButton databaseBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton extractBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton ocrPass;
    private javax.swing.JButton screenshotsBtn;
    private javax.swing.JPanel sidePanel;
    // End of variables declaration//GEN-END:variables
}