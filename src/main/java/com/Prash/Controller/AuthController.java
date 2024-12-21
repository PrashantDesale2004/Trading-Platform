package com.Prash.Controller;

import com.Prash.Config.JwtProvider;
import com.Prash.Repository.UserRepository;
import com.Prash.Response.AuthResponse;
import com.Prash.model.TwoFactorOTP;
import com.Prash.model.User;
import com.Prash.service.CustomerUserDetailsService;
import com.Prash.service.EmailService;
import com.Prash.service.TwoFactorOtpService;
import com.Prash.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TwoFactorOtpService twoFactorOtpService;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>  register(@RequestBody User user) throws Exception {


        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist != null){
            throw new Exception("user already used with another account");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());

        User saveUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res =new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>  login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authuser = userRepository.findByEmail(userName);

        if(user.getTwoFactorAuth().isEnable()){
            AuthResponse res =new AuthResponse();
            res.setMessage("Two factor Auth is enabled");
            res.setTwoFactorAuthEnable(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authuser.getId());
            if(oldTwoFactorOTP != null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.
                    createTwoFactOtp(authuser, otp, jwt);
            emailService.sendVerificationOtpEmail(userName, otp);


            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }


        AuthResponse res =new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails  = customerUserDetailsService.loadUserByUsername(userName);
        if(userDetails == null){
            throw new BadCredentialsException("invalid Username");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }


    public ResponseEntity<AuthResponse> verifySigingOtp(@PathVariable String otp,
                                                        @RequestParam String id ) throws Exception {
        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor Authentication Verified");
            res.setTwoFactorAuthEnable(true);
            res.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("Invalid Otp");
    }
}
