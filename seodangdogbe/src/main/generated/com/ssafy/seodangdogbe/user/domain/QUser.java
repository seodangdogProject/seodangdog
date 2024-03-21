package com.ssafy.seodangdogbe.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1383083405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    public final QBadge badge;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final ListPath<UserBadge, QUserBadge> userBadges = this.<UserBadge, QUserBadge>createList("userBadges", UserBadge.class, QUserBadge.class, PathInits.DIRECT2);

    public final QUserExp userExp;

    public final StringPath userId = createString("userId");

    public final ListPath<com.ssafy.seodangdogbe.keyword.domain.UserKeyword, com.ssafy.seodangdogbe.keyword.domain.QUserKeyword> userKeywords = this.<com.ssafy.seodangdogbe.keyword.domain.UserKeyword, com.ssafy.seodangdogbe.keyword.domain.QUserKeyword>createList("userKeywords", com.ssafy.seodangdogbe.keyword.domain.UserKeyword.class, com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.seodangdogbe.media.domain.UserMedia, com.ssafy.seodangdogbe.media.domain.QUserMedia> userMediaList = this.<com.ssafy.seodangdogbe.media.domain.UserMedia, com.ssafy.seodangdogbe.media.domain.QUserMedia>createList("userMediaList", com.ssafy.seodangdogbe.media.domain.UserMedia.class, com.ssafy.seodangdogbe.media.domain.QUserMedia.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.seodangdogbe.news.domain.UserNews, com.ssafy.seodangdogbe.news.domain.QUserNews> userNewsList = this.<com.ssafy.seodangdogbe.news.domain.UserNews, com.ssafy.seodangdogbe.news.domain.QUserNews>createList("userNewsList", com.ssafy.seodangdogbe.news.domain.UserNews.class, com.ssafy.seodangdogbe.news.domain.QUserNews.class, PathInits.DIRECT2);

    public final NumberPath<Integer> userSeq = createNumber("userSeq", Integer.class);

    public final ListPath<UserWord, QUserWord> userWords = this.<UserWord, QUserWord>createList("userWords", UserWord.class, QUserWord.class, PathInits.DIRECT2);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badge = inits.isInitialized("badge") ? new QBadge(forProperty("badge")) : null;
        this.userExp = inits.isInitialized("userExp") ? new QUserExp(forProperty("userExp")) : null;
    }

}

