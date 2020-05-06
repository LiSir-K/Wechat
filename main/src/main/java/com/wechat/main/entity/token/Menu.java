package com.wechat.main.entity.token;

import lombok.Data;

import java.util.Date;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.entity.token
 * @Date: Create in 14:05 2020/5/6
 */
@Data
public class Menu {
    private Integer id;
    private String menu;
    private Date createTime;
    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
