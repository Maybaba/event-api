package com.study.event.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return " 서버 구동 되는지 확인! 잘 돌아갑니다...? ";
    }
}
