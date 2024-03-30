package com.ssafy.seodangdogbe.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBadge is a Querydsl query type for Badge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBadge extends EntityPathBase<Badge> {

    private static final long serialVersionUID = -92171743L;

    public static final QBadge badge = new QBadge("badge");

    public final StringPath badgeCondition = createString("badgeCondition");

    public final StringPath badgeDescription = createString("badgeDescription");

    public final StringPath badgeImgUrl = createString("badgeImgUrl");

    public final StringPath badgeName = createString("badgeName");

    public final NumberPath<Integer> badgeSeq = createNumber("badgeSeq", Integer.class);

    public QBadge(String variable) {
        super(Badge.class, forVariable(variable));
    }

    public QBadge(Path<? extends Badge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBadge(PathMetadata metadata) {
        super(Badge.class, metadata);
    }

}

