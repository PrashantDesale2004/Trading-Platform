package com.Prash.service;

import com.Prash.Domain.VerificationType;
import com.Prash.model.User;
import com.Prash.model.VerificationCode;

public interface VerificationCodeService  {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long Id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId);

//    Boolean verifyOtp (String Otp, VerificationCode verificationCode);
    void deleteVerificationCodeById(VerificationCode verificationCode);


}
