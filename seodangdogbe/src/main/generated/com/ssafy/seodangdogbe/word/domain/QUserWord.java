package com.ssafy.seodangdogbe.word.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserWord is a Querydsl query type for UserWord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserWord extends EntityPathBase<UserWord> {

    private static final long serialVersionUID = 1194743670L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserWord userWord = new QUserWord("userWord");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.ssafy.seodangdogbe.user.domain.QUser user;

    public final StringPath word = createString("word");

    public final NumberPath<Long> wordSeq = createNumber("wordSeq", Long.class);

    public QUserWord(String variable) {
        this(UserWord.class, forVariable(variable), INITS);
    }

    public QUserWord(Path<? extends UserWord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserWord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserWord(PathMetadata metadata, PathInits inits) {
        this(UserWord.class, metadata, inits);
    }

    public QUserWord(Class<? extends UserWord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.ssafy.seodangdogbe.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

