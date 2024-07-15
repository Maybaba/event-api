package com.study.event.api.event.controller;

import com.study.event.api.event.dto.request.EventUserSaveDto;
import com.study.event.api.event.dto.request.LoginRequestDto;
import com.study.event.api.event.service.EventUserService;
import com.study.event.api.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:3000"}) //조건이 없으면 모든 곳에서 오는 요청을 허가한다.
public class EventUserController {

    private final EventUserService eventUserService;

    // 이메일 중복확인 API
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(String email) {
        boolean isDuplicate = eventUserService.checkEmailDuplicate(email);

        //중복된 이메일이 아니면 코드 전송
        if(!isDuplicate) {
            eventUserService.sendVerificationEmail(email);
        }

        return ResponseEntity.ok().body(isDuplicate);
    }

    //인증 코드 검증 API
    @GetMapping("/code")
    public ResponseEntity<?> verifyCode(String email, String code) {
        log.info("{}'s verify code is [ {} ]", email, code);

       boolean isMatch = eventUserService.isMatchCode(email, code);

       return ResponseEntity.ok().body(isMatch);
    }

    //회원가입 마무리 처리
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody EventUserSaveDto dto) {

        log.info("save User Info - {}", dto);

        try {
            eventUserService.confirmSignUp(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body("saved success");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDto dto) {

        try {
            eventUserService.authenticate(dto);
            return ResponseEntity.ok().body("login success");
        } catch (LoginFailException e) {
            //서비스에서 예외발생 (로그인실패)
            String errorMessage = e.getMessage();
            return ResponseEntity.status(422).body(errorMessage);
        }
    }

}
