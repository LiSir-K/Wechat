package com.wechat.main.mapper;

import com.wechat.main.entity.game.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.mapper
 * @Date: Create in 15:22 2020/5/7
 */
@Mapper
public interface RoomMapper {

    @Select("select * from room where open_id = #{openId} and del_flag = 0")
    Room getRoomByOpenId(String openId);

    @Insert("insert into room (open_id , room_name , del_flag) values (#{openId} , #{roomName} , #{delFlag})")
    int addRoom(Room newRoom);
}
