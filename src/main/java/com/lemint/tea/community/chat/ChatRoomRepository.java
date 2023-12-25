package com.lemint.tea.community.chat;

import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
}
