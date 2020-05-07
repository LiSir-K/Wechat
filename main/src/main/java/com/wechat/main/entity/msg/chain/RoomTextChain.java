package com.wechat.main.entity.msg.chain;

import com.wechat.main.entity.game.Room;
import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.RoomMapper;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.Map;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.msg.chain
 * @Date: Create in 15:15 2020/5/7
 */
public class RoomTextChain extends AbstractTextChain {

    public RoomTextChain() {
        super("room", "房间","创建房间");
    }

    public RoomTextChain(String... keywords) {
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
                return SendUtil.sendTextMsg(requestMap,"创建用户失败");
            }
        }
        RoomMapper roomMapper = MapperUtil.getInstance().getRoomMapper();
        Room room = roomMapper.getRoomByOpenId(openId);
        Room newRoom = new Room();
        if(room == null){
            String pickName = user.getPickName();
            if(pickName != null && !pickName.equals("")){
                newRoom.setRoomName(pickName+"的房间");
            } else {
                newRoom.setRoomName("房间"+String.valueOf(System.currentTimeMillis() / 1000));
            }
            newRoom.setOpenId(openId);
            newRoom.setDelFlag("0");
            roomMapper.addRoom(newRoom);
            room = roomMapper.getRoomByOpenId(openId);
        } else {
            return SendUtil.sendTextMsg(requestMap,"您已经创建好房间了,房间号是"+room.getId()+",房间名字是"+room.getRoomName());
        }
        return SendUtil.sendTextMsg(requestMap,"房间创建完成,房间号是"+room.getId()+",房间名字是"+room.getRoomName());
    }
}
