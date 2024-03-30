package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeywordNews is a Querydsl query type for KeywordNews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeywordNews extends EntityPathBase<KeywordNews> {

    private static final long serialVersionUID = 1795052626L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeywordNews keywordNews = new QKeywordNews("keywordNews");

    public final com.ssafy.seodangdogbe.common.QBaseTimeEntity _super = new com.ssafy.seodangdogbe.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final com.ssafy.seodangdogbe.keyword.domain.QKeyword keyword;

    public final NumberPath<Long> keywordNewsSeq = createNumber("keywordNewsSeq", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QNews news;

    public QKeywordNews(String variable) {
        this(KeywordNews.class, forVariable(variable), INITS);
    }

    public QKeywordNews(Path<? extends KeywordNews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeywordNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeywordNews(PathMetadata metadata, PathInits inits) {
        this(KeywordNews.class, metadata, inits);
    }

    public QKeywordNews(Class<? extends KeywordNews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new com.ssafy.seodangdogbe.keyword.domain.QKeyword(forProperty("keyword")) : null;
        this.news = inits.isInitialized("news") ? new QNews(forProperty("news"), inits.get("news")) : null;
    }

}

