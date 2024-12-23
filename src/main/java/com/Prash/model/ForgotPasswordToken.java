package com.Prash.model;

import com.Prash.Domain.VerificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String sendTo;

}
