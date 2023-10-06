package com.ktdsuniversity.edu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	@GetMapping("/chat")
	public String viewChatPage() {
		return "chat/chat";
	}
	
}

