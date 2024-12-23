package com.Prash.Request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
        private String otp;
        private String password;
}
