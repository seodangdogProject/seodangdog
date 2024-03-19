package com.ssafy.seodangdogbe.log.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMediaWeightLog is a Querydsl query type for MediaWeightLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMediaWeightLog extends EntityPathBase<MediaWeightLog> {

    private static final long serialVersionUID = 351504269L;

    public static final QMediaWeightLog mediaWeightLog = new QMediaWeightLog("mediaWeightLog");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> difference = createNumber("difference", Integer.class);

    public final NumberPath<Long> keywordSeq = createNumber("keywordSeq", Long.class);

    public final NumberPath<Long> mediaWeightLogSeq = createNumber("mediaWeightLogSeq", Long.class);

    public final NumberPath<Long> newsSeq = createNumber("newsSeq", Long.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final NumberPath<Integer> userSeq = createNumber("userSeq", Integer.class);

    public QMediaWeightLog(String variable) {
        super(MediaWeightLog.class, forVariable(variable));
    }

    public QMediaWeightLog(Path<? extends MediaWeightLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMediaWeightLog(PathMetadata metadata) {
        super(MediaWeightLog.class, metadata);
    }

}

