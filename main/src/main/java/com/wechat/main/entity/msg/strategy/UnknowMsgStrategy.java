package com.wechat.main.entity.msg.strategy;

import com.wechat.main.util.wechat.SendUtil;

import java.util.Map;

public class UnknowMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "不知道你在干嘛");
    }
}
