package com.study.event.api.event.repository;

import com.study.event.api.event.entity.EmailVerification;
import com.study.event.api.event.entity.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {

    //유저 정보를 통해 인증정보를 탐색
    // 정 불안하다 싶으면 쿼리 DSL로 하면 된ㄷ.
    Optional<EmailVerification> findByEventUser(EventUser eventUser);

}
