package com.lemint.tea.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardAttachMappingEmbeddedId is a Querydsl query type for BoardAttachMappingEmbeddedId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBoardAttachMappingEmbeddedId extends BeanPath<BoardAttachMappingEmbeddedId> {

    private static final long serialVersionUID = 1504744719L;

    public static final QBoardAttachMappingEmbeddedId boardAttachMappingEmbeddedId = new QBoardAttachMappingEmbeddedId("boardAttachMappingEmbeddedId");

    public final NumberPath<Long> attachId = createNumber("attachId", Long.class);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public QBoardAttachMappingEmbeddedId(String variable) {
        super(BoardAttachMappingEmbeddedId.class, forVariable(variable));
    }

    public QBoardAttachMappingEmbeddedId(Path<? extends BoardAttachMappingEmbeddedId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardAttachMappingEmbeddedId(PathMetadata metadata) {
        super(BoardAttachMappingEmbeddedId.class, metadata);
    }

}

