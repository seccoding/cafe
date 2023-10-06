package com.ktdsuniversity.edu.beans.websocket;

import org.springframework.web.socket.WebSocketSession;

public class ChatUser {

	private WebSocketSession session;
	private String userName;
	private String userEmail;
	
	public ChatUser(WebSocketSession session, String userName, String userEmail) {
		this.session = session;
		this.userName = userName;
		this.userEmail = userEmail;
	}
	
	public WebSocketSession getSession() {
		return session;
	}

	public String getUserName() {
		return userName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	@Override
	public boolean equals(Object obj) {
		WebSocketSession otherSession = (WebSocketSession) obj;
		return this.session.getId().equals(otherSession.getId());
	}

}
