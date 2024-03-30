package com.ssafy.seodangdogbe.log.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKeywordWeightLog is a Querydsl query type for KeywordWeightLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeywordWeightLog extends EntityPathBase<KeywordWeightLog> {

    private static final long serialVersionUID = -2069493272L;

    public static final QKeywordWeightLog keywordWeightLog = new QKeywordWeightLog("keywordWeightLog");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> difference = createNumber("difference", Integer.class);

    public final NumberPath<Long> keywordSeq = createNumber("keywordSeq", Long.class);

    public final NumberPath<Long> keywordWeightLogSeq = createNumber("keywordWeightLogSeq", Long.class);

    public final NumberPath<Long> newsSeq = createNumber("newsSeq", Long.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final NumberPath<Integer> userSeq = createNumber("userSeq", Integer.class);

    public QKeywordWeightLog(String variable) {
        super(KeywordWeightLog.class, forVariable(variable));
    }

    public QKeywordWeightLog(Path<? extends KeywordWeightLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeywordWeightLog(PathMetadata metadata) {
        super(KeywordWeightLog.class, metadata);
    }

}

