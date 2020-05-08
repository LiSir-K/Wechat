package com.wechat.main.mapper;

import com.wechat.main.entity.game.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Select("select * from room where del_flag = 0")
    List<Room> getAllRoom();

    @Select("select * from room where id = #{id} and del_flag = 0 order by create_time limit 1")
    Room getRoomById(String id);

    @Update("update room set is_send_poker = #{isSendPoker} where id = #{id} and del_flag = 0")
    int updateIsSendPoker(Room room);
}
