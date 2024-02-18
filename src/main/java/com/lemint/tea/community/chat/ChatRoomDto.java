package com.lemint.tea.community.chat;

import com.lemint.tea.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Data
public class ChatRoomDto implements Serializable {

  private static final long serialVersionUID = 6494678977089006639L;

  private String roomId;

}
