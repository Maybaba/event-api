package com.study.event.api.event.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_email_verification")
public class EmailVerification {

    @Id
    @GeneratedValue(generator = "uuid-fenerator")
    @GenericGenerator(strategy = "uuid2", name = "uuid-generator")
    @Column(name = "verification_id")
    private String id; //회원계정 아님, 랜덤문자 PK

    @Column(nullable = false)
    private String verificationCode; //인증코드

    @Column(nullable = false)
    private LocalDateTime expiryDate; //인증만료시간

    @OneToOne
    @JoinColumn(name = "event_user_id", referencedColumnName = "ev_user_id")
    private EventUser eventUser;

    /*
    alter table tbl_email_verification
    ADD CONSTRAINT sdf21;lfk;
    foreign key (event_user_id)
    references tbl_event_user (ev_user_id)
     */
}
