package com.lemint.tea.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttach is a Querydsl query type for Attach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttach extends EntityPathBase<Attach> {

    private static final long serialVersionUID = 380300140L;

    public static final QAttach attach = new QAttach("attach");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath contentType = createString("contentType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath path = createString("path");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    //inherited
    public final BooleanPath useFlag = _super.useFlag;

    public QAttach(String variable) {
        super(Attach.class, forVariable(variable));
    }

    public QAttach(Path<? extends Attach> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttach(PathMetadata metadata) {
        super(Attach.class, metadata);
    }

}

