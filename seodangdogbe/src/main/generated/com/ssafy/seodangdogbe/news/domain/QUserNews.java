package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserNews is a Querydsl query type for UserNews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserNews extends EntityPathBase<UserNews> {

    private static final long serialVersionUID = -1431884536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserNews userNews = new QUserNews("userNews");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<Integer, NumberPath<Integer>> highlightList = this.<Integer, NumberPath<Integer>>createList("highlightList", Integer.class, NumberPath.class, PathInits.DIRECT2);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final BooleanPath isSolved = createBoolean("isSolved");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QNews news;

    public final com.ssafy.seodangdogbe.user.domain.QUser user;

    public final ListPath<Integer, NumberPath<Integer>> userAnswerList = this.<Integer, NumberPath<Integer>>createList("userAnswerList", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> userNewsSeq = createNumber("userNewsSeq", Long.class);

    public final SimplePath<UserSummary> userSummary = createSimple("userSummary", UserSummary.class);

    public final ListPath<Integer, NumberPath<Integer>> wordList = this.<Integer, NumberPath<Integer>>createList("wordList", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public QUserNews(String variable) {
        this(UserNews.class, forVariable(variable), INITS);
    }

    public QUserNews(Path<? extends UserNews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserNews(PathMetadata metadata, PathInits inits) {
        this(UserNews.class, metadata, inits);
    }

    public QUserNews(Class<? extends UserNews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new QNews(forProperty("news"), inits.get("news")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.seodangdogbe.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

