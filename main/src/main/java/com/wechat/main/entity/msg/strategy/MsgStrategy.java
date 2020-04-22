package com.wechat.main.entity.msg.strategy;

import java.util.Map;

public interface MsgStrategy {
    String execute(Map<String, String> requestMap);
}
