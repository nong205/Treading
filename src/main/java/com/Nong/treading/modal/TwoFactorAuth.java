package com.Nong.treading.modal;

import com.Nong.treading.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;
    private VerificationType sendTo;




}
