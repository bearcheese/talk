package com.bearmaster.talk.gui.component;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jivesoftware.smack.roster.RosterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jgoodies.forms.builder.FormBuilder;
import com.jgoodies.forms.factories.Paddings;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class FriendListPanel {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendListPanel.class);

    private List<RosterEntry> rosterEntries;

    private JPanel friendListPanel;

    private JScrollPane scrollPanel;
    
    private boolean initialised = false;

    private void initComponents() {

        FormBuilder friendListPanelBuilder = FormBuilder.create().columns("left:pref:grow").rows("")
                .padding(Paddings.DIALOG);

        LOGGER.debug("About to render {} entries", rosterEntries.size());
        
        int i = 1;
        
        for (RosterEntry entry : rosterEntries) {
            JLabel label = new JLabel(entry.getName());
            friendListPanelBuilder.appendRows("pref");
            friendListPanelBuilder.add(label).xy(1, i++);
        }

        friendListPanel = friendListPanelBuilder.build();
        friendListPanel.setName("friendListPanel");

        scrollPanel = new JScrollPane(friendListPanel);
        
        initialised = true;
    }

    public JComponent getComponent() {
        if (!initialised) {
            initComponents();
        }
        return scrollPanel;
    }
    
    public void setRosterEntries(List<RosterEntry> rosterEntries) {
        this.rosterEntries = rosterEntries;
    }

}
