package com.wechat.main.mapper;

import com.wechat.main.entity.token.AccessTokenInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccessTokenInfoMapper {
    /**
     * 查询token的信息
     */
    @Select("select * from access_token_info where token = #{token}")
    AccessTokenInfo getTokenInfo(String token);

    /**
     * 根据删除标志位查询token的信息
     */
    @Select("select * from access_token_info where del_flag = 0 order by create_time limit 1")
    AccessTokenInfo getTokenInfoByDelFlag();

    @Insert("insert into access_token_info (token,del_flag,expire_second) values (#{token},#{delFlag},#{expireSecond})")
    int saveToken(AccessTokenInfo accessTokenInfo);

    @Update("update access_token_info set del_flag = 1 where id = #{id}")
    int updateOldTokenDelFlag(Long id);
}
