package com.Nong.treading.service;

import com.Nong.treading.domain.VerificationType;
import com.Nong.treading.modal.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long UserId) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo, User user);
    User updatePassword(User user, String newPassword);
}
