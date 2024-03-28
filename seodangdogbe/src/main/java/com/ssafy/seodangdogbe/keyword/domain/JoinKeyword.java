package com.ssafy.seodangdogbe.keyword.domain;

<<<<<<< HEAD

=======
import jakarta.persistence.Column;
>>>>>>> 20d6739a7a8646ef8a7fde4e7fbcb10f7bb59cea
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinKeyword {

    @Id
    private int joinKeywordSeq;

    @Column(unique = true, nullable = false)
    private String keyword;

}
