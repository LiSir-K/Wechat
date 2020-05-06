package com.wechat.main.util.sql;

import com.wechat.main.mapper.AccessTokenInfoMapper;
import com.wechat.main.mapper.MenuMapper;
import com.wechat.main.mapper.NpcMapper;
import com.wechat.main.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MapperUtil {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NpcMapper npcMapper;
    @Autowired
    private AccessTokenInfoMapper accessTokenInfoMapper;
    @Autowired
    private MenuMapper menuMapper;

    private static MapperUtil mapperUtil;

    @PostConstruct
    public void init() {
        mapperUtil = this;
    }

    public static MapperUtil getInstance() {
        return mapperUtil;
    }

    public UserMapper getUserMapper() {
        return this.userMapper;
    }

    public  NpcMapper getNpcMapper() {
        return this.npcMapper;
    }

    public AccessTokenInfoMapper getAccessTokenInfoMapper() {
        return accessTokenInfoMapper;
    }

    public MenuMapper getMenuMapper() {
        return menuMapper;
    }
}
