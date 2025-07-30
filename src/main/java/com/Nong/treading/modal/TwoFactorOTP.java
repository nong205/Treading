package com.Nong.treading.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TwoFactorOTP {

    @Id
    private String id;

    private String otp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwt;
}
