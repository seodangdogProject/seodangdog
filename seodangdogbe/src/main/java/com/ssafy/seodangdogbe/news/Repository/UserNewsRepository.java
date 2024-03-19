package com.ssafy.seodangdogbe.news.Repository;

import com.ssafy.seodangdogbe.news.domain.UserNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewsRepository extends JpaRepository<UserNews, Long> {

}
