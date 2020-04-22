package com.wechat.main.entity.msg.strategy;

import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.Map;

/**
 * 事件消息 - 关注
 */
public class SubscribeMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        UserMapper userMapper = mapperUtil.getUserMapper();
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        if (userMapper.getUserCountByOpenId(openId) < 1) {
            User user = new User(openId, 10.0,
                    Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            try {
                userMapper.addUser(user);
            } catch (Exception e) {
                return SendUtil.sendTextMsg(requestMap, "回来了老弟？");
            }
        } else {
            return SendUtil.sendTextMsg(requestMap, "回来了老弟？");
        }
        return SendUtil.sendTextMsg(requestMap, "谢谢您的关注！获得10金钱！");
    }
}
