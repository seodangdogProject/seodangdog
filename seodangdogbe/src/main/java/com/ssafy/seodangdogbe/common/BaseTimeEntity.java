package com.ssafy.seodangdogbe.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass   //JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 createDate, modifiedDate를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class)  //해당 클래스에 Auditing 기능을 포함
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ColumnDefault("false")
//    @Setter // isDelete 업데이트 추가(createdAt, modifiedAt은 자동 업데이트)
    private boolean isDelete;
//    @Builder.Default
//    private boolean isDelete = false;
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}