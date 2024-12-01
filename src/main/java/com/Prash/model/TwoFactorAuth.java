package com.Prash.model;

import com.Prash.Domain.VerificationType;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
public class TwoFactorAuth {
    private boolean isEnable = false;
    private VerificationType sendTo;
}
