package com.wechat.main.controller;

import com.wechat.main.entity.token.AccessToken;
import com.wechat.main.service.WeChatService;
import com.wechat.main.util.wechat.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class WeChatController {


    @Autowired
    private WeChatService weChatService;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     *
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "wechat")
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        log.info("signature:{},timestamp:{},nonce:{},echostr:{}",signature,timestamp,nonce,echostr);
        return SignatureUtil.check(signature, timestamp, nonce) ? echostr : null;
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping(value = "wechat")
    public String processMsg(HttpServletRequest request) {
        // 调用核心服务类接收处理请求
        return weChatService.processRequest(request);
    }

    /**
     * 获取AccessToken
     *
     * @return
     */
    @RequestMapping(value = "getAccessToken")
    public AccessToken getAccessToken(){
        AccessToken accessToken = weChatService.getAccessToken();
        return accessToken;
    }

    /**
     * 设置菜单
     */
    @RequestMapping(value = "setMenu")
    public String setMenu(){
        return weChatService.setMenu();
    }
}