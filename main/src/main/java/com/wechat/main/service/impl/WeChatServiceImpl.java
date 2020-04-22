package com.wechat.main.service.impl;

import com.wechat.main.entity.msg.strategy.MsgStrategy;
import com.wechat.main.entity.msg.strategy.MsgStrategyContext;
import com.wechat.main.entity.msg.strategy.MsgStrategyFactory;
import com.wechat.main.service.WeChatService;
import com.wechat.main.util.wechat.XmlUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    public String processRequest(HttpServletRequest request) {
        // 默认返回的文本消息内容
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = XmlUtil.xmlToMap(request);
            // 工厂类生产策略
            MsgStrategy msgStrategy = MsgStrategyFactory.getReceiveMsg(requestMap);
            // 将策略交给执行策略的上下文
            MsgStrategyContext context = new MsgStrategyContext(msgStrategy);
            return context.execute(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
