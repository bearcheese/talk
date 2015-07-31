package com.bearmaster.talk.services;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.RosterEntry;

public interface ChatService {

    public abstract void initConnection();

    public abstract void login(String username, String password) throws XMPPException, SmackException, IOException;

    public abstract Collection<RosterEntry> getRosterEntries();

}