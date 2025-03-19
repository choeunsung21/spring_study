package com.gn.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService service;
	
	@GetMapping("/login")
	public String loginView() {
		return "member/login";
	}
	
	@GetMapping("/signup")
	public String createMemberView() {
		return "member/create";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public Map<String, String> createMemberApi(MemberDto dto) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "회원가입 중 오류가 발생했습니다.");
		
		MemberDto resultDto = service.createMember(dto);
		
		if(resultDto != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "회원가입이 완료되었습니다.");
		}
		
		return resultMap;
	}
}
