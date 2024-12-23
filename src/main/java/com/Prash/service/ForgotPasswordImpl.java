package com.Prash.service;

import com.Prash.Domain.VerificationType;
import com.Prash.Repository.ForgotPasswordRepository;
import com.Prash.model.ForgotPasswordToken;
import com.Prash.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordImpl implements  ForgotPasswordService{
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user,
                                           String id, String otp,
                                           VerificationType verificationType,
                                           String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setOtp(otp);
        token.setId(id);
        return forgotPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);

        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long UserId) {
        return forgotPasswordRepository.findByUserId(UserId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRepository.delete(token);
    }
}
