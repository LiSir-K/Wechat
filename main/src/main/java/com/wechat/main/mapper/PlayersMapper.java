package com.wechat.main.mapper;

import com.wechat.main.entity.game.Players;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.mapper
 * @Date: Create in 15:22 2020/5/7
 */
@Mapper
public interface PlayersMapper {

    @Select("select * from players where room_id = #{roomId} and open_id = #{openId} and del_flag = 0 order by create_time limit 1")
    Players getPlayersByRoomIdAndOpenId(String roomId, String openId);

    @Select("select * from players where room_id = #{roomId} and del_flag = 0")
    List<Players> getPlayersByRoomId(String roomId);

    @Insert("insert into players (open_id , room_id , del_flag) values (#{openId} , #{roomId} , 0)")
    int addPlayers(Players players);
}
