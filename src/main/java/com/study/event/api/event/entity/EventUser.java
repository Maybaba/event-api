package com.study.event.api.event.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "eventList")
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_event_user")
public class EventUser {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(strategy = "uuid2", name = "uuid-generator")
    @Column(name = "ev_user_id")
    private String id; //회원계정 아님, 랜덤문자 PK

    @Column(name = "ev_user_email", nullable = false, unique = true)
    private String email; //회원 계정

    //NN을 하지 않는 이유? 로그인한 회원, 인증번호만 받고 회원가입을 오나료하지 않은 사람 처리
    @Column(length = 500)
    private String password;

    //임포트 안하고 만ㄴ든거 사용
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.COMMON; //권한

    private LocalDateTime createAt; //회원가입 시간

    @OneToMany(mappedBy = "eventUser", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Event> eventList = new ArrayList<>();

    //이메일 인증을 완료했는지 여부
    //엔터티에 boolean 타입을 사용하면 실제 DB엔 0,1로 저장됨에 주의!
    //true : 1 false : 0
    @Setter
    private boolean emailVerified;

    public void confirm(String password) {
        this.password = password;
        this.createAt = LocalDateTime.now();
    }

    public void promoteToPremium() {

    }
}
