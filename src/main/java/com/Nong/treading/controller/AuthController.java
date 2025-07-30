package com.Nong.treading.controller;

import com.Nong.treading.config.JwtProvider;
import com.Nong.treading.modal.TwoFactorOTP;
import com.Nong.treading.modal.User;
import com.Nong.treading.repository.UserRepository;
import com.Nong.treading.response.AuthResponse;
import com.Nong.treading.service.CustomerUserDetailsService;
import com.Nong.treading.service.EmailService;
import com.Nong.treading.service.TwoFactorOtpService;
import com.Nong.treading.utils.OtpUtils;
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
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private UserRepository userRepository;
//-------Sign Up ---------
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExits = userRepository.findByEmail(user.getEmail());
        if (isEmailExits != null) {
            throw new Exception("Email already exists");
        }
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setTwoFactorAuth(user.getTwoFactorAuth());
        newUser.setRole(user.getRole());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser  = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
          res.setJwt(jwt);
          res.setStatus(true);
          res.setMessage("Register successfully!!^^");

        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }
    //-------Sign Inn ---------
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(userName);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setJwt(jwt);
            res.setMessage("Two factor auth enabled!!^^");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOTP != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService
                    .createTwoFactorOtp(user,otp,jwt);

            emailService.sendVerificationOtpEmail(userName,otp);
            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);


        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successfully!!^^");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);

        if (userDetails==null){
            throw new BadCredentialsException("invalid userName");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigingOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {
        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication verified!!^^");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        throw new Exception("invalid otp");

    }

}
