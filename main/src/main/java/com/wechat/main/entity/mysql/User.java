package com.wechat.main.entity.mysql;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String openId;
    private Double money;
    private Integer prizeTime;

    public User() {

    }

    public User(String openId, Double money, Integer prizeTime) {
        this.openId = openId;
        this.money = money;
        this.prizeTime = prizeTime;
    }

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(Integer prizeTime) {
        this.prizeTime = prizeTime;
    }
}
