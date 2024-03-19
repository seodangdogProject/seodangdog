package com.ssafy.seodangdogbe.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserExp is a Querydsl query type for UserExp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserExp extends EntityPathBase<UserExp> {

    private static final long serialVersionUID = 1816517968L;

    public static final QUserExp userExp = new QUserExp("userExp");

    public final NumberPath<Integer> inferenceExp = createNumber("inferenceExp", Integer.class);

    public final NumberPath<Integer> judgementExp = createNumber("judgementExp", Integer.class);

    public final NumberPath<Integer> newsExp = createNumber("newsExp", Integer.class);

    public final NumberPath<Integer> summaryExp = createNumber("summaryExp", Integer.class);

    public final NumberPath<Integer> userExpSeq = createNumber("userExpSeq", Integer.class);

    public final NumberPath<Integer> wordExp = createNumber("wordExp", Integer.class);

    public final NumberPath<Integer> wordGameExp = createNumber("wordGameExp", Integer.class);

    public QUserExp(String variable) {
        super(UserExp.class, forVariable(variable));
    }

    public QUserExp(Path<? extends UserExp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserExp(PathMetadata metadata) {
        super(UserExp.class, metadata);
    }

}

