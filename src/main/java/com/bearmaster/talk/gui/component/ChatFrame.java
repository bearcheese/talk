package com.bearmaster.talk.gui.component;

import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bearmaster.talk.model.Friend;
import com.jgoodies.forms.builder.FormBuilder;

public class ChatFrame extends JFrame {

    private static final long serialVersionUID = -7668869257901589264L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatFrame.class);

    private Friend friend;
    
    private JFrame mainFrame;
    
    private JTextPane chatLog;

    private JTextArea input;

    public ChatFrame(JFrame mainFrame, Friend friend) {
        this.friend = friend;
        this.mainFrame = mainFrame;
        initFrame();
    }

    private void initFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Chat window - " + friend.getName());

        chatLog = new JTextPane();
        chatLog.setEditable(false);

        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        FormBuilder formBuilder = FormBuilder.create()
                .columns("fill:max(150dlu;pref):grow")
                .rows("fill:200dlu:grow, bottom:pref")
                .debug(true)
                .add(chatLog).xy(1, 1)
                .add(input).xy(1, 2);
        
        getContentPane().add(formBuilder.build());
        
        setLocationRelativeTo(mainFrame);
        
        pack();
        
        setVisible(true);

    }
    
    public void addInputFieldKeyListener(KeyListener listener) {
        input.addKeyListener(listener);
    }
    
    public String appendInputTextToChat() {
        String message = input.getText();
        addText(message);
        input.setText("");
        
        return message;
    }
    
    public void appendFriendMessageToChat(String msg) {
        addText(msg);
    }
    
    protected void addText(String s) {
        StyledDocument document = chatLog.getStyledDocument();
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        try {
            document.insertString(document.getLength(), s, defaultStyle);
        } catch (BadLocationException e) {
            LOGGER.error("Cannot append text '{}' to the chat log", s, e);
        }
    }

}
