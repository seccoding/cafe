package com.ktdsuniversity.edu.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloBootController {

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello Spring Boot Controller", HttpStatus.OK);
	}
	
	@GetMapping("/hello2")
	public ResponseEntity<String> hello2() {
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("<head><title>Hello, Boot!</title></head>");
		html.append("<body>");
		html.append("<div>안녕하세요?</div>");
		html.append("<div>Spring Boot에서 응답되었습니다.</div>");
		html.append("</body>");
		html.append("</html>");
		return new ResponseEntity<>(html.toString(), HttpStatus.OK);
	}
	
	@GetMapping("/hello3")
	public String helloJsp() {
		return "helloboot";
	}
	
	@GetMapping("/hello4")
	public ModelAndView helloModelAndView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("helloboot");
		view.addObject("myname", "Spring Boot~!");
		return view;
	}
	
	@GetMapping("/hello5")
	public String helloModel(Model model) {
		model.addAttribute("myname", "Cafe Demo");
		return "helloboot";
	}
	
}
