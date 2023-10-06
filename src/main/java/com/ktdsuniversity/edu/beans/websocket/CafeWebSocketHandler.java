package com.ktdsuniversity.edu.beans.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@Component
public class CafeWebSocketHandler extends TextWebSocketHandler {

	private ChatRoom chatRoom;
	private Gson gson;
	
	public CafeWebSocketHandler() {
		chatRoom = new ChatRoom();
		gson = new Gson();
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Message receiveMessage = gson.fromJson(message.getPayload(), Message.class);
		
		String sendType = receiveMessage.getSendType().toLowerCase(); 
		if (sendType.equals(ChatType.ENTER)) {
			chatRoom.enter(session, receiveMessage);
			chatRoom.sendAll(session, receiveMessage);
		}
		else if (sendType.equals(ChatType.ALL)) {
			chatRoom.sendAll(session, receiveMessage);
			
			receiveMessage.setSendToMe(true);
			chatRoom.sendToMe(session, receiveMessage);
		}
		else if (sendType.equals(ChatType.LEAVE)) {
			chatRoom.leave(session);
			chatRoom.sendAll(session, receiveMessage);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		chatRoom.leave(session);
	}
	
}
