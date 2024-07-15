package com.study.event.api.event.repository;

import com.study.event.api.event.entity.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventUserRepository extends JpaRepository<EventUser, String> {

    //querymethod로 직접 쿼리 jqpl 만들기

    //이름 규칙 중요, 꼭 엔터티 필드명과 똑같이 만들어야 자동으로 쿼리 생성해줌
// query method로 Jpql 생성
    boolean existsByEmail(String email);

    Optional<EventUser> findByEmail(String email);
}
