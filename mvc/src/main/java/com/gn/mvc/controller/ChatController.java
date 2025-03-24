package com.gn.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gn.mvc.entity.ChatMsg;
import com.gn.mvc.entity.ChatRoom;
import com.gn.mvc.service.ChatMsgService;
import com.gn.mvc.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatRoomService chatRoomService;
	private final ChatMsgService chatMsgService;
	
	@GetMapping("/{id}/detail")
	public String selectChatRoomOne(@PathVariable("id") Long id, Model model) {
		ChatRoom chatRoom = chatRoomService.selectChatRoomOne(id);
		model.addAttribute("chatRoom", chatRoom);
		
//		List<ChatMsg> msgList = 채팅메시지 서비스 만들기;
		List<ChatMsg> msgList = chatMsgService.selectChatMsgAll(id);
//		화면에 채팅 메시지 전달하기(key를 msgList로);
		model.addAttribute("msgList", msgList);
		System.out.println(msgList);
		
		return "chat/detail";
	}
	
	@GetMapping("/list")
	public String selectChatRoomAll(Model model) {
		List<ChatRoom> resultList = chatRoomService.selectChatRoomAll();
		model.addAttribute("chatRoomList",resultList);
		return "chat/list";
	}
}
