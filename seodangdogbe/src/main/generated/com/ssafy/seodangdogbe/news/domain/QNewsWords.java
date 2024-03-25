package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewsWords is a Querydsl query type for NewsWords
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNewsWords extends BeanPath<NewsWords> {

    private static final long serialVersionUID = -1468578548L;

    public static final QNewsWords newsWords = new QNewsWords("newsWords");

    public QNewsWords(String variable) {
        super(NewsWords.class, forVariable(variable));
    }

    public QNewsWords(Path<? extends NewsWords> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewsWords(PathMetadata metadata) {
        super(NewsWords.class, metadata);
    }

}

