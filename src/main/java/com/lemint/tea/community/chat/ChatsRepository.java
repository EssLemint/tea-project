package com.lemint.tea.community.chat;

import com.lemint.tea.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatsRepository extends JpaRepository<Chats, Long>, ChatsRepositoryCustom {
}
