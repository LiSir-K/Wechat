package com.wechat.main.entity.game;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.game
 * @Date: Create in 15:17 2020/5/7
 */

import lombok.Data;

import java.util.Date;

@Data
public class Room {

    private String id;
    private String openId;
    private String roomName;
    private String delFlag;
    private String isSendPoker;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getIsSendPoker() {
        return isSendPoker;
    }

    public void setIsSendPoker(String isSendPoker) {
        this.isSendPoker = isSendPoker;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
