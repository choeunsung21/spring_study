package com.gn.mvc.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gn.mvc.dto.ChatMsgDto;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final Map<Long, WebSocketSession> userSessions = new HashMap<Long, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String senderNo = session.getUri().getQuery().split("=")[1];
		userSessions.put(Long.parseLong(senderNo), session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMsgDto dto = objectMapper.readValue(message.getPayload(),ChatMsgDto.class);
		
		WebSocketSession receiverSession = userSessions.get(dto.getReceiver_no());
		if(receiverSession!=null && receiverSession.isOpen()) {
			receiverSession.sendMessage(new TextMessage(dto.getMsg_content()));
		}
		
		WebSocketSession senderSession = userSessions.get(dto.getSender_no());
		if(senderSession!=null && senderSession.isOpen()) {
			senderSession.sendMessage(new TextMessage(dto.getMsg_content()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String userNo = session.getUri().getQuery().split("=")[1];
		userSessions.remove(Long.parseLong(userNo));
	}

}
