package com.Prash.service;

import com.Prash.Domain.VerificationType;
import com.Prash.model.ForgotPasswordToken;
import com.Prash.model.User;
import org.aspectj.apache.bcel.generic.FieldGenOrMethodGen;

public interface ForgotPasswordService {
        ForgotPasswordToken createToken(User user,
                                        String id, String otp,
                                        VerificationType verificationType,
                                        String sendTo);

        ForgotPasswordToken findById(String id);
        ForgotPasswordToken findByUser(Long UserId);
        void deleteToken(ForgotPasswordToken token);
}
