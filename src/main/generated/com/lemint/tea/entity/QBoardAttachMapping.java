package com.lemint.tea.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardAttachMapping is a Querydsl query type for BoardAttachMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardAttachMapping extends EntityPathBase<BoardAttachMapping> {

    private static final long serialVersionUID = -405160918L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardAttachMapping boardAttachMapping = new QBoardAttachMapping("boardAttachMapping");

    public final QAttach attach;

    public final QBoard board;

    public final QBoardAttachMappingEmbeddedId id;

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QBoardAttachMapping(String variable) {
        this(BoardAttachMapping.class, forVariable(variable), INITS);
    }

    public QBoardAttachMapping(Path<? extends BoardAttachMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardAttachMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardAttachMapping(PathMetadata metadata, PathInits inits) {
        this(BoardAttachMapping.class, metadata, inits);
    }

    public QBoardAttachMapping(Class<? extends BoardAttachMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attach = inits.isInitialized("attach") ? new QAttach(forProperty("attach")) : null;
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
        this.id = inits.isInitialized("id") ? new QBoardAttachMappingEmbeddedId(forProperty("id")) : null;
    }

}

