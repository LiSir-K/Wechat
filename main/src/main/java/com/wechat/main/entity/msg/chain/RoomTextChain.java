package com.wechat.main.entity.msg.chain;

import com.wechat.main.entity.game.Players;
import com.wechat.main.entity.game.Room;
import com.wechat.main.entity.mysql.User;
import com.wechat.main.mapper.PlayersMapper;
import com.wechat.main.mapper.RoomMapper;
import com.wechat.main.mapper.UserMapper;
import com.wechat.main.util.date.DateUtil;
import com.wechat.main.util.poker.MakePoker;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;
import com.wechat.main.util.wechat.WeChatConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.msg.chain
 * @Date: Create in 15:15 2020/5/7
 */
public class RoomTextChain extends AbstractTextChain {

    @Override
    public String sendMsg(Map<String,String> requestMap) {
        if ("创建房间".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return send(requestMap);
        } else if ("我的房间".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return sendMyRoom(requestMap);
        } else if ("开牌".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return sendOpenPoker(requestMap);
        } else if ("发牌".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return sendPoker(requestMap);
        } else if (requestMap.get(WeChatConstant.CONTENT).contains("加入房间")) {
            return sendJoinRoom(requestMap);
        } else if ("房间列表".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return sendRoomList(requestMap);
        }
        return next != null ? next.sendMsg(requestMap) : new TuringTextChain().send(requestMap);
    }

    protected String sendPoker(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        RoomMapper roomMapper = MapperUtil.getInstance().getRoomMapper();
        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        Room room = roomMapper.getRoomByOpenId(openId);
        int flag = 0 ;
        if (room.getIsSendPoker().equals("1") ) {
            return SendUtil.sendTextMsg(requestMap,"您已经发过牌了,请先开牌再发牌");
        }
        if (room != null){
           this.sendPoker(requestMap,room);
           room.setIsSendPoker("1");
           flag = roomMapper.updateIsSendPoker(room);
        } else {
            Players player = playersMapper.getPlayersBuOpenId(openId);
            if (player.getPoker() != null) {
                return SendUtil.sendTextMsg(requestMap,"发牌成功,您的牌是:\n"+player.getPoker());
            } else {
                Room roomById = roomMapper.getRoomById(player.getRoomId());
                this.sendPoker(requestMap,roomById);
                room.setIsSendPoker("1");
                flag = roomMapper.updateIsSendPoker(room);
            }
        }
        if (flag != 1 ){
            return SendUtil.sendTextMsg(requestMap,"发牌失败");
        }
        Players player = playersMapper.getPlayersBuOpenId(openId);
        return SendUtil.sendTextMsg(requestMap,"发牌成功,您的牌是:\n"+player.getPoker());
    }

    protected String sendJoinRoom(Map<String, String> requestMap) {
        String content = requestMap.get(WeChatConstant.CONTENT);
        String[] split = content.split(":");
        if(split.length != 2){
            return SendUtil.sendTextMsg(requestMap,"加入房间格式错误,请按照格式修改(例: 加入房间:1)");
        }
        String id = split[1];
        RoomMapper roomMapper = MapperUtil.getInstance().getRoomMapper();
        Room room = roomMapper.getRoomById(id);

        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        Players players = new Players();
        players.setRoomId(room.getId());
        players.setOpenId(requestMap.get(WeChatConstant.FROM_USER_NAME));
        players.setDelFlag("0");
        Players player = playersMapper.getPlayersByRoomIdAndOpenId(room.getId(), players.getOpenId());
        if (player != null) {
            return SendUtil.sendTextMsg(requestMap,"加入失败,您已经在这个房间了");
        }
        int i = playersMapper.addPlayers(players);
        if (i == 1) {
            return SendUtil.sendTextMsg(requestMap,"加入成功");
        }
        return SendUtil.sendTextMsg(requestMap,"加入失败");
    }

    protected String sendRoomList(Map<String, String> requestMap) {
        MapperUtil instance = MapperUtil.getInstance();
        RoomMapper roomMapper = instance.getRoomMapper();
        List<Room> rooms = roomMapper.getAllRoom();
        if (rooms != null) {
            String roomNum = "";
            for (Room room:rooms) {
                roomNum = "房间号:"+room.getId()+"\n";
            }
            return SendUtil.sendTextMsg(requestMap,"房间列表:\n"+roomNum+"\n需要加入房间请输入'加入房间:房间号 (例: 加入房间:1)'");
        }
        return SendUtil.sendTextMsg(requestMap,"当前没有房间");
    }


    @Override
    protected String send(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        UserMapper userMapper = MapperUtil.getInstance().getUserMapper();
        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        Players player = playersMapper.getPlayersBuOpenId(openId);
        if (player != null) {
            return SendUtil.sendTextMsg(requestMap,"创建房间失败,您已经加入房间了,加入的房间号是:"+player.getRoomId());
        }
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
            Players players = new Players();
            players.setOpenId(openId);
            players.setRoomId(room.getId());
            playersMapper.addPlayers(players);
        } else {
            return SendUtil.sendTextMsg(requestMap,"您已经创建好房间了\n房间号是:"+room.getId()+
                    "\n房间名字是:"+room.getRoomName()+"\n创建时间:"+DateUtil.formatDate(room.getCreateTime()));
        }
        return SendUtil.sendTextMsg(requestMap,"房间创建完成\n房间号是:"+room.getId()+
                "\n房间名字是:"+room.getRoomName()+"\n创建时间:"+DateUtil.formatDate(room.getCreateTime()));
    }

    protected String sendMyRoom(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        RoomMapper roomMapper = MapperUtil.getInstance().getRoomMapper();
        UserMapper userMapper = MapperUtil.getInstance().getUserMapper();
        Room room = roomMapper.getRoomByOpenId(openId);
        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        List<Players> players = playersMapper.getPlayersByRoomId(room.getId());
        if (players != null){
            String roomsPlayers = "";
            for (Players player :players) {
                User user = userMapper.getUserByOpenId(player.getOpenId());
                String pickName = user.getPickName();
                if(pickName == null || pickName.equals("")){
                    pickName = "陌生玩家";
                }
                roomsPlayers = roomsPlayers + pickName+"\n";
            }
            return SendUtil.sendTextMsg(requestMap,"您的房间号是:"+room.getId()+"\n房间名字是:"+room.getRoomName()+"\n创建时间:"+room.getCreateTime()
                    +"\n用户数量:"+players.size()+"\n分别有:"+roomsPlayers);
        } else {
            return SendUtil.sendTextMsg(requestMap,"您的房间号是:"+room.getId()+"\n房间名字是:"+room.getRoomName()+"\n创建时间:"
                    + DateUtil.formatDate(room.getCreateTime())+"\n用户数量:"+players.size());
        }
    }

    protected String sendOpenPoker(Map<String, String> requestMap){
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        RoomMapper roomMapper = MapperUtil.getInstance().getRoomMapper();
        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        Players player = playersMapper.getPlayersBuOpenId(openId);

        UserMapper userMapper = MapperUtil.getInstance().getUserMapper();

        Room room = roomMapper.getRoomById(player.getRoomId());
        if (!room.getIsSendPoker().equals("1")) {
            return SendUtil.sendTextMsg(requestMap,"开牌失败,还没有发牌");
        }
        List<Players> players = playersMapper.getPlayersByRoomId(player.getRoomId());
        String pokers = "";
        Integer i = 1;
        for (Players play : players) {
            String poker = play.getPoker();
            User user = userMapper.getUserByOpenId(play.getOpenId());
            if (user.getPickName().equals("") || user.getPickName() == null) {
                pokers += "玩家"+ i +"的牌:"+ poker + "\n";
            } else {
                pokers += user.getPickName()+"的牌:"+ poker + "\n";
            }
            i++;
        }
        room.setIsSendPoker("0");
        int i1 = roomMapper.updateIsSendPoker(room);
        if(i1 == 1){
            return SendUtil.sendTextMsg(requestMap,pokers);
        }
        return SendUtil.sendTextMsg(requestMap,"开牌失败");
    }

    private String sendPoker(Map<String, String> requestMap , Room room){
        PlayersMapper playersMapper = MapperUtil.getInstance().getPlayersMapper();
        List<Players> players = playersMapper.getPlayersByRoomId(room.getId());
        List<List<String>> pokers = MakePoker.makePoker(players.size());
        if(pokers.size() != players.size()){
            return SendUtil.sendTextMsg(requestMap,"发牌失败");
        }
        int i = 0;
        for (Players player :players) {
            List<String> list = pokers.get(i);
            player.setPoker(list.get(0)+","+list.get(1)+","+list.get(2));
            int flag = playersMapper.UpdatePokerByOpenId(player);
            i++;
        }
        return "";
    }
}
