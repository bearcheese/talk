package com.bearmaster.talk.gui.component;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jivesoftware.smack.roster.RosterEntry;

public class FriendListPanel extends JPanel {
    
    private static final long serialVersionUID = 7462253130356162059L;
    
    private List<RosterEntry> rosterEntries;
    
    public FriendListPanel(List<RosterEntry> rosterEntries) {
        this.rosterEntries = rosterEntries;
        setName("friendListPanel");
        initComponents();
    }

    private void initComponents() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);
        
        for (RosterEntry entry : rosterEntries) {
            JLabel label = new JLabel(entry.getName());
            label.setAlignmentY(CENTER_ALIGNMENT);
            add(label);
        }
    }
    
}
