package com.wechat.main.entity.game;

import lombok.Data;

import java.util.Date;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.game
 * @Date: Create in 16:19 2020/5/7
 */
@Data
public class Players {
    private Integer id ;
    private String openId;
    private String roomId;
    private String delFlag;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
