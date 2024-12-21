package com.Prash.service;

import com.Prash.Domain.VerificationType;
import com.Prash.model.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long UserId) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo, User user);
    User updatePassword(User user, String newPassword);

}
