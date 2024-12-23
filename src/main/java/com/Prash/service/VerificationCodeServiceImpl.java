package com.Prash.service;

import com.Prash.Domain.VerificationType;
import com.Prash.Repository.VerificationCodeRepository;
import com.Prash.model.User;
import com.Prash.model.VerificationCode;
import com.Prash.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
       VerificationCode verificationCode1 = new VerificationCode();
       verificationCode1.setOtp(OtpUtils.generateOTP());
       verificationCode1.setVerificationType(verificationType);
       verificationCode1.setUser(user);
        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long Id) throws Exception {
        Optional<VerificationCode> verificationCode =
                verificationCodeRepository.findById(Id);

        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("Verification Code Not Found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
            verificationCodeRepository.delete(verificationCode);
    }
}
