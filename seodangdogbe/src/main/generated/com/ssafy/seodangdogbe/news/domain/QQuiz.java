package com.ssafy.seodangdogbe.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuiz is a Querydsl query type for Quiz
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QQuiz extends BeanPath<Quiz> {

    private static final long serialVersionUID = 227325727L;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final NumberPath<Integer> answer = createNumber("answer", Integer.class);

    public final ListPath<String, StringPath> content = this.<String, StringPath>createList("content", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath example = createString("example");

    public QQuiz(String variable) {
        super(Quiz.class, forVariable(variable));
    }

    public QQuiz(Path<? extends Quiz> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiz(PathMetadata metadata) {
        super(Quiz.class, metadata);
    }

}

