package com.lemint.tea.community.attachmapping;

import com.lemint.tea.entity.BoardAttachMapping;
import com.lemint.tea.entity.BoardAttachMappingEmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachMappingRepository extends JpaRepository<BoardAttachMapping, BoardAttachMappingEmbeddedId> {
}
