package com.ssafy.seodangdogbe.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBadge is a Querydsl query type for UserBadge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBadge extends EntityPathBase<UserBadge> {

    private static final long serialVersionUID = 1913581078L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBadge userBadge = new QUserBadge("userBadge");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    public final QBadge badge;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QUser user;

    public final NumberPath<Integer> userBadgeSeq = createNumber("userBadgeSeq", Integer.class);

    public QUserBadge(String variable) {
        this(UserBadge.class, forVariable(variable), INITS);
    }

    public QUserBadge(Path<? extends UserBadge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBadge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBadge(PathMetadata metadata, PathInits inits) {
        this(UserBadge.class, metadata, inits);
    }

    public QUserBadge(Class<? extends UserBadge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badge = inits.isInitialized("badge") ? new QBadge(forProperty("badge")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

