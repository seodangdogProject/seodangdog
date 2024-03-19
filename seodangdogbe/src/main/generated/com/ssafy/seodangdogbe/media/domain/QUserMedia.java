package com.ssafy.seodangdogbe.media.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserMedia is a Querydsl query type for UserMedia
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMedia extends EntityPathBase<UserMedia> {

    private static final long serialVersionUID = -577967372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserMedia userMedia = new QUserMedia("userMedia");

    public final QMedia media;

    public final com.ssafy.seodangdogbe.user.domain.QUser user;

    public final NumberPath<Long> userMediaSeq = createNumber("userMediaSeq", Long.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUserMedia(String variable) {
        this(UserMedia.class, forVariable(variable), INITS);
    }

    public QUserMedia(Path<? extends UserMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserMedia(PathMetadata metadata, PathInits inits) {
        this(UserMedia.class, metadata, inits);
    }

    public QUserMedia(Class<? extends UserMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.media = inits.isInitialized("media") ? new QMedia(forProperty("media")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.seodangdogbe.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

