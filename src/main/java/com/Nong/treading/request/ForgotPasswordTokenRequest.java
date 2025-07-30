package com.Nong.treading.request;

import com.Nong.treading.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
   private String sendTo;
    private VerificationType verificationType;

}
