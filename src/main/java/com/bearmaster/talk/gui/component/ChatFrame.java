package com.bearmaster.talk.gui.component;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bearmaster.talk.model.Friend;
import com.jgoodies.forms.builder.FormBuilder;

public class ChatFrame extends JFrame {

    private static final long serialVersionUID = -7668869257901589264L;

    private static final String CHAT_LOG_FORMAT = "%s: %s\n";

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
        
        addStylesToDocument(chatLog.getStyledDocument());
        
        
        
        JPanel noWrapPanel = new JPanel(new BorderLayout());
        
        
        JScrollPane scrollPane = new JScrollPane(noWrapPanel);
        scrollPane.setViewportView(noWrapPanel);
        noWrapPanel.add(chatLog);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        FormBuilder formBuilder = FormBuilder.create().columns("fill:max(150dlu;pref):grow")
                .rows("fill:200dlu:grow, bottom:pref").add(chatLog).xy(1, 1).add(input).xy(1, 2);

        getContentPane().add(formBuilder.build());

        setLocationRelativeTo(mainFrame);

        pack();

        setVisible(true);

    }

    public void addInputFieldKeyListener(KeyListener listener) {
        input.addKeyListener(listener);
    }

    public String appendInputTextToChat() {
        String message = input.getText().trim();
        addText("Me", message);
        input.setText("");

        return message;
    }

    public void appendFriendMessageToChat(String msg) {
        addText(friend.getName(), msg);
    }

    protected void addText(String name, String s) {
        StyledDocument document = chatLog.getStyledDocument();
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        try {
            document.insertString(document.getLength(), " ", document.getStyle("icon"));
            document.insertString(document.getLength(), name + ": ", document.getStyle("bold"));
            document.insertString(document.getLength(), s + '\n', defaultStyle);
        } catch (BadLocationException e) {
            LOGGER.error("Cannot append text '{}' to the chat log", s, e);
        }
    }

    protected void addStylesToDocument(StyledDocument doc) {
        // Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("icon", regular);
        //StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        ImageIcon unknownProfile = createImageIcon("/com/bearmaster/talk/gui/resources/user.png", "no profile pic");
        if (unknownProfile != null) {
            StyleConstants.setIcon(s, unknownProfile);
        }

        /*
         * s = doc.addStyle("button", regular); StyleConstants.setAlignment(s,
         * StyleConstants.ALIGN_CENTER); ImageIcon soundIcon =
         * createImageIcon("images/sound.gif", "sound icon"); JButton button =
         * new JButton(); if (soundIcon != null) { button.setIcon(soundIcon); }
         * else { button.setText("BEEP"); }
         * button.setCursor(Cursor.getDefaultCursor()); button.setMargin(new
         * Insets(0,0,0,0)); button.setActionCommand(buttonString);
         * button.addActionListener(this); StyleConstants.setComponent(s,
         * button);
         */
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = ChatFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Friend dummy = new Friend("dummy", "Frank", null);
                ChatFrame frame = new ChatFrame(null, dummy);
                frame.addInputFieldKeyListener(new ParrotKeyListener(frame));
            }
        });
    }

    private static class ParrotKeyListener extends KeyAdapter {

        private ChatFrame frame;

        public ParrotKeyListener(ChatFrame frame) {
            this.frame = frame;
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (!event.isShiftDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
                String message = frame.appendInputTextToChat();
                frame.appendFriendMessageToChat("Parrot says: " + message);
            }

        }

    }
}
