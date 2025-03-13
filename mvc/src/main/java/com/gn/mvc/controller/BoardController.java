package com.gn.mvc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
	
	@GetMapping("/board/create")
	public String createBoardView() {
		return "board/create";
	}
	
//	@PostMapping("/board")
//	@ResponseBody
//	public Map<String, String> createBoardApi(
////			@RequestParam("board_title") String boardTitle,
////			@RequestParam("board_content") String boardContent
////			@RequestParam Map<String,String> param
//			) {
//		
//	}

}
