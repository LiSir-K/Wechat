package com.wechat.main.entity.msg.strategy;


import com.wechat.main.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 点击菜单
 */
public class ClickMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您点击了菜单");
    }
}
