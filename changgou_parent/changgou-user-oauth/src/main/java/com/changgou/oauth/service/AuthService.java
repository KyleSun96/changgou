package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * @Program: ChangGou
 * @InterfaceName: AuthService
 * @Description:
 * @Author: KyleSun
 **/
public interface AuthService {

    AuthToken login(String username, String password, String clientId, String clientSecret);
}
