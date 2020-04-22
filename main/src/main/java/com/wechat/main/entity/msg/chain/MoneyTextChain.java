package com.wechat.main.entity.msg.chain;



import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.Map;

public class MoneyTextChain extends AbstractTextChain {

    public MoneyTextChain() {
        super("金钱", "金币", "money");
    }

    public MoneyTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        UserMapper userMapper = mapperUtil.getUserMapper();
        User user = userMapper.getUserByOpenId(requestMap.get(WeChatConstant.FROM_USER_NAME));
        return SendUtil.sendTextMsg(requestMap, "你剩余金钱：" + user.getMoney());
    }
}
