package com.wechat.main.service;

import com.wechat.main.entity.token.AccessToken;

import javax.servlet.http.HttpServletRequest;

public interface WeChatService {
    String processRequest(HttpServletRequest request);

    AccessToken getAccessToken();

    String setMenu();

}
