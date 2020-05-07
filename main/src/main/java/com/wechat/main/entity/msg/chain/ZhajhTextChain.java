package com.wechat.main.entity.msg.chain;

import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.poker.MakePoker;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.Map;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.msg.chain
 * @Date: Create in 17:39 2020/5/6
 */
public class ZhajhTextChain extends AbstractTextChain {

    public ZhajhTextChain() {
        super("zjh", "炸金花","拖拉机");
    }

    public ZhajhTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        UserMapper userMapper = MapperUtil.getInstance().getUserMapper();
        User user = userMapper.getUserByOpenId(openId);
        if(user == null){
            User newUser = new User();
            newUser.setOpenId(openId);
            int addUser = userMapper.addUser(newUser);
            if(addUser != 1){
                return SendUtil.sendTextMsg(requestMap,"出错了>_<");
            }
        }
        String poker = MakePoker.makePoker();
        return SendUtil.sendTextMsg(requestMap,poker);
    }
}
