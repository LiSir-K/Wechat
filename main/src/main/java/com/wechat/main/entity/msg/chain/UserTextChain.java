package com.wechat.main.entity.msg.chain;

import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.Map;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.msg.chain
 * @Date: Create in 18:25 2020/5/7
 */
public class UserTextChain extends AbstractTextChain {

    @Override
    public String sendMsg(Map<String,String> requestMap) {
        if (requestMap.get(WeChatConstant.CONTENT).contains("修改名称")) {
            return send(requestMap);
        }
        return next != null ? next.sendMsg(requestMap) : new TuringTextChain().send(requestMap);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        String context = requestMap.get(WeChatConstant.CONTENT);
        String[] split = context.split(":");
        if(split.length != 2){
            return SendUtil.sendTextMsg(requestMap,"修改名称格式错误,请按照格式修改(例: 修改名称:张三)");
        }
        UserMapper userMapper = MapperUtil.getInstance().getUserMapper();
        User user = userMapper.getUserByOpenId(openId);
        String pickName = split[1];
        int i = userMapper.updatePickName(pickName,user.getId());
        if (i ==1){
            return SendUtil.sendTextMsg(requestMap,"修改成功");
        }
        return SendUtil.sendTextMsg(requestMap,"修改失败");
    }
}
