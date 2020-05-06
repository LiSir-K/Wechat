package com.wechat.main.entity.msg.chain;

import com.wechat.main.util.poker.MakePoker;
import com.wechat.main.util.wechat.SendUtil;

import java.util.Map;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.msg.chain
 * @Date: Create in 17:39 2020/5/6
 */
public class ZhajhTextChain extends AbstractTextChain {

    public ZhajhTextChain() {
        super("zjh", "炸金花");
    }

    public ZhajhTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        String poker = MakePoker.makePoker();
        return SendUtil.sendTextMsg(requestMap,poker);
    }
}
