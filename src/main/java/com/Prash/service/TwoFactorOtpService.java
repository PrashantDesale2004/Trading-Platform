package com.Prash.service;
import com.Prash.model.TwoFactorOTP;
import com.Prash.model.User;

public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

}
