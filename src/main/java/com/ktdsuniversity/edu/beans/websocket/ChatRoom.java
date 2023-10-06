package com.ktdsuniversity.edu.beans.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

public class ChatRoom {

	private Map<String, List<ChatUser>> sessions;
	private Gson gson;
	public ChatRoom() {
		sessions = new HashMap<>();
		gson = new Gson();
	}
	
	public String getMyRoomName(WebSocketSession session) {
		return sessions.entrySet()
						.stream()
						.filter(entry -> {
							return entry.getValue().contains(session);
						})
						.map(entry -> entry.getKey())
						.findFirst().orElse("");
	}
	
	public ChatUser getChatUser(WebSocketSession session) {
		return sessions.entrySet()
						.stream()
						.flatMap(entry -> entry.getValue().stream())
						.filter(user -> user.getSession() == session)
						.findFirst()
						.orElse(null);
	}
	
	public void enter(WebSocketSession session, Message receiveMessage) {
		String roomName = receiveMessage.getRoomName();
		String userName = receiveMessage.getUserName();
		String userEmail = receiveMessage.getUserEmail();
		if (!sessions.containsKey(roomName)) {
			sessions.put(roomName, new ArrayList<>());
		}
		
		ChatUser chatUser = new ChatUser(session, userName, userEmail);
		sessions.get(roomName).add(chatUser);
	}
	
	public void leave(WebSocketSession session) {
		String roomName = this.getMyRoomName(session);
		ChatUser user = this.getChatUser(session);
		
		if (this.sessions.containsKey(roomName)) {
			this.sessions.get(roomName).remove(user);
			
			if (this.sessions.get(roomName).size() == 0) {
				this.sessions.remove(roomName);
			}
		}
	}
	
	public void sendToMe(WebSocketSession me, Message message) {
		TextMessage textMessage = new TextMessage(gson.toJson(message));
		try {
			me.sendMessage(textMessage);
		} catch (IOException e) {}
	}
	
	public void sendAll(WebSocketSession me, Message message) {
		TextMessage textMessage = new TextMessage(gson.toJson(message));
		sessions.get(message.getRoomName())
				.parallelStream()
				.map(user -> user.getSession())
				.filter(session -> session.isOpen() && !session.getId().equals(me.getId()))
				.forEach(session -> {
					try {
						session.sendMessage(textMessage);
					} catch (IOException e) {}
				});
	}
	
}
