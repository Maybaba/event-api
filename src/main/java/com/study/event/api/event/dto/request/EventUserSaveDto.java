package com.study.event.api.event.dto.request;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EventUserSaveDto {
    //서버에서도 not blank 등의 유효값 검증하기
    private String email;
    private String password;
}
