package com.lemint.tea.community.chat;

import com.lemint.tea.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Objects;

import static com.lemint.tea.enums.Role.SecRoles.ANONYMOUS;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ChatService chatService;


  @GetMapping("")
  public String enterPage() {
    return "chat/room";
  }

  @Secured({ANONYMOUS})
  @MessageMapping("/send/message")
  public void sendMessage(@Payload ChatMessageDto dto) {
    log.debug("SEND_MESSAGE");

    dto.setMessage(dto.getMessage());
    //바로 socket 통신을 바로 치는게 아니라 중간의 redis에게 넘겨서 진행하는 방식으로 변경
    //messagingTemplate.convertAndSend("/sub/chatroom/detail/" + dto.getChatRoomId(), dto);

    //websocket에서 발생한 메세지를 redis로 발행(publish)
    String roomId = String.valueOf(dto.getChatRoomId());
  }

  @Secured({ANONYMOUS})
  @MessageMapping("/enter/user")
  public void enterUser(@Payload ChatMessageDto dto, SimpMessageHeaderAccessor headerAccessor) {
    log.debug("ENTER_USER");

    //채팅방 존재 유무
    ChatRoom room = chatService.getChatRoomByIds(dto.getSenderId(), dto.getReceiveId());
    //없을 시 생성, 입장
    if (Objects.isNull(room)) {
      room =  chatService.createNewChatRoom(dto);
    }

    //채팅방 인원 추가
    chatService.addUserCount(dto.getChatRoomId());

    headerAccessor.getSessionAttributes().put("senderId", room.getSenderId());
    headerAccessor.getSessionAttributes().put("chatRoomId", room.getId());

    String name = chatService.getUserNameById(String.valueOf(room.getSenderId()));

    dto.setMessage(name + "님이 입장합니다.");
    redisTemplate.convertAndSend("/sub/chatroom/detail/" + room.getId(), dto);
  }

  @Secured({ANONYMOUS})
  @EventListener
  public void webSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    String senderId = (String) headerAccessor.getSessionAttributes().get("senderId");
    String chatRoomId = (String) headerAccessor.getSessionAttributes().get("chatRoomId");

    chatService.minusUserCount(chatRoomId);
    String name = chatService.getUserNameById(senderId);
    chatService.deleteUserFromChatRoom(chatRoomId, senderId); //사용자 접근을 어떻게 제한하는 로직을 진행해야하는지....

    if (!Objects.isNull(name)) {
      log.info("User Disconnected : " + name);

      ChatMessageDto chatMessageDto = ChatMessageDto.builder()
          .chatRoomId(Long.parseLong(chatRoomId))
          .senderId(Long.parseLong(senderId))
          .message(name + "퇴장하셨습니다.")
          .build();

      redisTemplate.convertAndSend("/sub/chatroom/detail/" + chatRoomId, chatMessageDto);
    }
  }
}
