package com.Prash.Repository;

import com.Prash.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String > {
    ForgotPasswordToken findByUserId(Long userId);
}
