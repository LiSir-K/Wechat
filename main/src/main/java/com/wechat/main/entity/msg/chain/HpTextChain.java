package com.wechat.main.entity.msg.chain;

import com.wechat.main.entity.mysql.Npc;
import com.wechat.main.mapper.NpcMapper;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.SendUtil;

import java.util.Map;

public class HpTextChain extends AbstractTextChain {

    public HpTextChain() {
        super("血量", "hp", "HP");
    }

    public HpTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        NpcMapper npcMapper = mapperUtil.getNpcMapper();
        Npc npc = npcMapper.getNpcById(1);
        return SendUtil.sendTextMsg(requestMap, "周建云剩余血量：" + npc.getHp());
    }
}
