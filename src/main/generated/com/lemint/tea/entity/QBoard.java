package com.lemint.tea.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 982855903L;

    public static final QBoard board = new QBoard("board");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<BoardAttachMapping, QBoardAttachMapping> attachList = this.<BoardAttachMapping, QBoardAttachMapping>createList("attachList", BoardAttachMapping.class, QBoardAttachMapping.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final NumberPath<Long> createBy = createNumber("createBy", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modifiedBy = createNumber("modifiedBy", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    //inherited
    public final BooleanPath useFlag = _super.useFlag;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

