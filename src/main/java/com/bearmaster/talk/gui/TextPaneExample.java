package com.bearmaster.talk.gui;

import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
 
public class TextPaneExample extends javax.swing.JFrame {
    private StyledDocument chatDoc;
    private SimpleAttributeSet selfNameStyle;
    private SimpleAttributeSet messageNameStyle;
 
    public TextPaneExample() {
        initComponents();
 
        selfNameStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(selfNameStyle, Color.RED);
        StyleConstants.setBold(selfNameStyle, true);
 
        messageNameStyle = new SimpleAttributeSet();
 
        chatDoc = chatTextPane.getStyledDocument();
    }
 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
 
        sendField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        chatScrollPane = new javax.swing.JScrollPane();
        chatTextPane = new javax.swing.JTextPane();
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
 
        sendField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFieldActionPerformed(evt);
            }
        });
 
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
 
        chatTextPane.setEditable(false);
        chatScrollPane.setViewportView(chatTextPane);
 
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chatScrollPane)
                    .add(layout.createSequentialGroup()
                        .add(sendField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sendButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(chatScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(sendField)
                    .add(sendButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
 
        pack();
    }// </editor-fold>
 
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        sendMessage();
    }
 
    private void sendFieldActionPerformed(java.awt.event.ActionEvent evt) {
        sendMessage();
    }
 
    private void sendMessage() {
        String txt = sendField.getText();
        if (!txt.equals("")) {
            try {
                chatDoc.insertString(chatDoc.getLength(), "You: ", selfNameStyle);
                chatDoc.insertString(chatDoc.getLength(), txt + "\n", messageNameStyle);
            } catch(Exception e) {
                System.err.println(e);
            }                
            sendField.setText("");
        }
    }
 
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TextPaneExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TextPaneExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TextPaneExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TextPaneExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TextPaneExample().setVisible(true);
            }
        });
    }
 
    // Variables declaration - do not modify
    private javax.swing.JScrollPane chatScrollPane;
    private javax.swing.JTextPane chatTextPane;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField sendField;
    // End of variables declaration
 
}