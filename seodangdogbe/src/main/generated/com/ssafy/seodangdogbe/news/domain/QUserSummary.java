package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSummary is a Querydsl query type for UserSummary
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserSummary extends BeanPath<UserSummary> {

    private static final long serialVersionUID = 934272337L;

    public static final QUserSummary userSummary = new QUserSummary("userSummary");

    public final ListPath<String, StringPath> userKeyword = this.<String, StringPath>createList("userKeyword", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath userSummaryContent = createString("userSummaryContent");

    public QUserSummary(String variable) {
        super(UserSummary.class, forVariable(variable));
    }

    public QUserSummary(Path<? extends UserSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserSummary(PathMetadata metadata) {
        super(UserSummary.class, metadata);
    }

}

