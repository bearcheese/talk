package com.bearmaster.talk;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class Main {

	public static void main(String[] args) throws SmackException, IOException,
			XMPPException, InterruptedException {

		SmackConfiguration.DEBUG = true;
		// Create a connection to the jabber.org server on a specific port.
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration
				.builder().setUsernameAndPassword(args[0], args[1])
				.setServiceName("gmail.com").setHost("talk.google.com")
				.setPort(5222).build();

		AbstractXMPPConnection connection = new XMPPTCPConnection(config);
		connection.connect();

		connection.login();
		
		Presence presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.available);
		connection.sendStanza(presence);

		Roster roster = Roster.getInstanceFor(connection);
		System.out.println("Roster loaded: " + roster.isLoaded());
		
		while (!roster.isLoaded()) {
			System.out.println("waiting...");
			Thread.sleep(1000L);
		}
		
		Collection<RosterEntry> entries = roster.getEntries();
		System.out.println("Roster count: " + entries.size());
		for (RosterEntry entry : entries) {
			System.out.println(entry);
		}

		connection.disconnect();

	}

	private static void chat(XMPPConnection connection) throws NotConnectedException {
		// Assume we've created an XMPPConnection name "connection"._
		ChatManager chatmanager = ChatManager.getInstanceFor(connection);
		Chat newChat = chatmanager.createChat("mpety3@gmail.com",
				new ChatMessageListener() {

					@Override
					public void processMessage(Chat chat, Message message) {
						System.out.println("Received message: " + message);

					}
				});

		newChat.sendMessage("Howdy! This is Smack JABBER lib...");

		System.out.println("Message sent...");
	}
}
