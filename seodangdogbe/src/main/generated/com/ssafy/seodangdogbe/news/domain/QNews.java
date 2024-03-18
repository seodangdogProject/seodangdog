package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 227221405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNews news = new QNews("news");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    public final NumberPath<Integer> countSolve = createNumber("countSolve", Integer.class);

    public final NumberPath<Integer> countView = createNumber("countView", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final ListPath<KeywordNews, QKeywordNews> keywordNewsList = this.<KeywordNews, QKeywordNews>createList("keywordNewsList", KeywordNews.class, QKeywordNews.class, PathInits.DIRECT2);

    public final com.ssafy.seodangdogbe.media.domain.QMedia media;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath newsAccessId = createString("newsAccessId");

    public final DateTimePath<java.time.LocalDateTime> newsCreatedAt = createDateTime("newsCreatedAt", java.time.LocalDateTime.class);

    public final StringPath newsDescription = createString("newsDescription");

    public final StringPath newsImgUrl = createString("newsImgUrl");

    public final NumberPath<Long> newsSeq = createNumber("newsSeq", Long.class);

    public final StringPath newsTitle = createString("newsTitle");

    public QNews(String variable) {
        this(News.class, forVariable(variable), INITS);
    }

    public QNews(Path<? extends News> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNews(PathMetadata metadata, PathInits inits) {
        this(News.class, metadata, inits);
    }

    public QNews(Class<? extends News> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.media = inits.isInitialized("media") ? new com.ssafy.seodangdogbe.media.domain.QMedia(forProperty("media")) : null;
    }

}

