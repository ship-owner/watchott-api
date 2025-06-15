package com.watchott.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "authoritie")
    private String authoritie;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "refresh_token")
    private String refreshToken;
}
