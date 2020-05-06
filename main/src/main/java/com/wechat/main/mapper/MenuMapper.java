package com.wechat.main.mapper;

import com.wechat.main.entity.token.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.mapper
 * @Date: Create in 13:48 2020/5/6
 */
@Mapper
public interface MenuMapper {
    /**
     * 查询目录
     */
    @Select("select * from menu where del_flag = 0 order by create_time limit 1")
    Menu getMenu();
}
