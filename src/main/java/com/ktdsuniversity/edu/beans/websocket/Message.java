package com.ktdsuniversity.edu.beans.websocket;

public class Message {

	private String roomName;
	private String sendType;
	private String userName;
	private String userEmail;
	private boolean sendToMe;
	private String message;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isSendToMe() {
		return sendToMe;
	}

	public void setSendToMe(boolean sendToMe) {
		this.sendToMe = sendToMe;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
