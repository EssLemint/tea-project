package com.lemint.tea.community.attachmapping;

import com.lemint.tea.entity.BoardAttachMapping;

import java.util.List;

public interface AttachMappingCustom {
  List<BoardAttachMapping> findAttachMappingByBoardId(Long id);
}
