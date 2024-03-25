package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRatings is a Querydsl query type for Ratings
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRatings extends EntityPathBase<Ratings> {

    private static final long serialVersionUID = -578010772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRatings ratings = new QRatings("ratings");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QNews news;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final NumberPath<Long> ratingSeq = createNumber("ratingSeq", Long.class);

    public final com.ssafy.seodangdogbe.user.domain.QUser user;

    public QRatings(String variable) {
        this(Ratings.class, forVariable(variable), INITS);
    }

    public QRatings(Path<? extends Ratings> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRatings(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRatings(PathMetadata metadata, PathInits inits) {
        this(Ratings.class, metadata, inits);
    }

    public QRatings(Class<? extends Ratings> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new QNews(forProperty("news"), inits.get("news")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.seodangdogbe.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

