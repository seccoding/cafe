package com.ktdsuniversity.edu.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.validategroup.MemberLoginGroup;
import com.ktdsuniversity.edu.member.vo.validategroup.MemberRegistGroup;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member/regist")
	public String viewRegistMemberPage() {
		return "member/memberregist";
	}
	
	@PostMapping("/member/regist")
	public ModelAndView doRegistMember(@Validated(MemberRegistGroup.class) @ModelAttribute MemberVO memberVO
									, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("member/memberregist");
			modelAndView.addObject("memberVO", memberVO);
			return modelAndView;
		}
		
		boolean isSuccess = memberService.createNewMember(memberVO);
		if (isSuccess) {
			modelAndView.setViewName("redirect:/member/login");
			return modelAndView;
		}
		
		modelAndView.setViewName("member/memberregist");
		modelAndView.addObject("memberVO", memberVO);
		return modelAndView;
	}
	
	@ResponseBody // 응답데이터를 JSON으로 변환하여 브라우저에게 전송한다.
	@GetMapping("/member/regist/available")
	public Map<String, Object> checkAvailableEmail(
			@RequestParam String email) {
		
		boolean isAvailableEmail = memberService.checkAvailableEmail(email);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("email", email);
		responseMap.put("available", isAvailableEmail);
		
		// Map을 반환하면 @ResponesBody에 의해 JSON으로 변환되어 응답된다.
		return responseMap;
		
	}
	
	@GetMapping("/member/login")
	public String viewLoginPage() {
		return "member/memberlogin";
	}
	
	@PostMapping("/member/login")
	public ModelAndView doLogin(@Validated(MemberLoginGroup.class) @ModelAttribute MemberVO memberVO
			                  , BindingResult bindingResult
			                  , HttpSession session
			                  , HttpServletRequest request
                              , @RequestParam(required = false, defaultValue = "/board/list") String next) {
		
		ModelAndView modelAndView = new ModelAndView();
		// 접근 IP 받아와서 할당.
		memberVO.setLatestAccessIp(request.getRemoteAddr());
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("member/memberlogin");
			modelAndView.addObject("memberVO", memberVO);
			return modelAndView;
		}
		
		MemberVO member = memberService.getMember(memberVO);
		session.setAttribute("_LOGIN_USER_", member);
		
		modelAndView.setViewName("redirect:" + next);
		return modelAndView;
	}
	
	@GetMapping("/member/logout")
	public String doLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/board/list";
	}
	
	@GetMapping("/member/delete-me")
	public String doDeleteMe(
			// JSP sessionScope._LOGIN_USER_ 와 같은 기능을 함.
			@SessionAttribute("_LOGIN_USER_") MemberVO memberVO
		  , HttpSession session) {
		
		boolean isSuccess = memberService.deleteMe(memberVO.getEmail());
		if (!isSuccess) {
			return "redirect:/member/fail-delete-me";
		}
		
		session.invalidate();
		return "redirect:/member/success-delete-me";
	}
	
	@GetMapping("/member/{result}-delete-me")
	public String viewDeleteMePage(@PathVariable String result) {
		
		result = result.toLowerCase();
		if ( !result.equals("fail") && !result.equals("success") ) {
			// result의 값이 fail, success가 아니면 404페이지 보여주기
			return "error/404";
		}
		
		return "member/" + result + "deleteme";
		
	}
}







