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
import com.jgoodies.forms.factories.Paddings;

public class ChatFrame extends JFrame {

    private static final long serialVersionUID = -7668869257901589264L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatFrame.class);

    private Friend friend;

    private JFrame mainFrame;

    private JTextPane chatLog;

    private JTextArea input;
    
    private String lastInputCameFrom = "";

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
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(chatLog.getBackground());
        
        panel.add(chatLog, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(chatLog);

        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setFont(input.getFont().deriveFont(12f));

        FormBuilder formBuilder = FormBuilder.create().columns("fill:150dlu:grow")
                .rows("fill:200dlu:grow, 5dlu, pref").padding(Paddings.DIALOG).add(scrollPane).xy(1, 1).add(input).xy(1, 3);

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
            if (!lastInputCameFrom.equals(name)) {
            	document.insertString(document.getLength(), " ", document.getStyle("icon"));
            }
            document.insertString(document.getLength(), name + ": ", document.getStyle("bold"));
            document.insertString(document.getLength(), s + '\n', defaultStyle);
        } catch (BadLocationException e) {
            LOGGER.error("Cannot append text '{}' to the chat log", s, e);
        }
        lastInputCameFrom = name;
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
        ImageIcon unknownProfile = createImageIcon("/com/bearmaster/talk/gui/resources/user.png", "no profile pic");
        if (unknownProfile != null) {
            StyleConstants.setIcon(s, unknownProfile);
        }
    }

    //TODO use central resource manager
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = ChatFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            LOGGER.warn("Couldn't find image file: {}", path);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Friend dummy = new Friend("dummy", "Frank", null);
                ChatFrame frame = new ChatFrame(null, dummy);
                frame.addInputFieldKeyListener(new ParrotKeyListener(frame, 3));
            }
        });
    }

    private static class ParrotKeyListener extends KeyAdapter {

        private ChatFrame frame;
        
        private int freq;
        
        private int counter;
        
        private StringBuilder builder;

        public ParrotKeyListener(ChatFrame frame, int freq) {
            this.frame = frame;
            this.freq = freq;
            this.builder = new StringBuilder();
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (!event.isShiftDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
                String message = frame.appendInputTextToChat();
                builder.append(message);
                builder.append(" ");
                counter++;
                
                if (counter % freq == 0) {
                	frame.appendFriendMessageToChat("Parrot says: " + builder.toString());
                	builder.setLength(0);
                	counter = 0;
                }
            }

        }

    }
}
