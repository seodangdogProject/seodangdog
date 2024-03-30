package com.ssafy.seodangdogbe.keyword.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserKeyword is a Querydsl query type for UserKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserKeyword extends EntityPathBase<UserKeyword> {

    private static final long serialVersionUID = -1196430316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserKeyword userKeyword = new QUserKeyword("userKeyword");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final QKeyword keyword;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.ssafy.seodangdogbe.user.domain.QUser user;

    public final NumberPath<Long> userKeywordSeq = createNumber("userKeywordSeq", Long.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUserKeyword(String variable) {
        this(UserKeyword.class, forVariable(variable), INITS);
    }

    public QUserKeyword(Path<? extends UserKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserKeyword(PathMetadata metadata, PathInits inits) {
        this(UserKeyword.class, metadata, inits);
    }

    public QUserKeyword(Class<? extends UserKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new QKeyword(forProperty("keyword")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.seodangdogbe.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

