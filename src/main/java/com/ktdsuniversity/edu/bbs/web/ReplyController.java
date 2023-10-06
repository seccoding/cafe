package com.ktdsuniversity.edu.bbs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.bbs.service.ReplyService;

@RestController
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
}
