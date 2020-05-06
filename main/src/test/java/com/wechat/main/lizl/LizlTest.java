package com.wechat.main.lizl;

import com.wechat.main.MainApplication;
import com.wechat.main.config.WechatConfig;
import com.wechat.main.entity.token.AccessToken;
import com.wechat.main.entity.token.AccessTokenInfo;
import com.wechat.main.mapper.AccessTokenInfoMapper;
import com.wechat.main.util.sql.MapperUtil;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class LizlTest {

    @Test
    public void Test01(){
        AccessTokenInfoMapper accessTokenInfoMapper = MapperUtil.getInstance().getAccessTokenInfoMapper();
        AccessToken token = new AccessToken();
        token.setTokenName("33_u15OfmWamreJYum62UBzdqFvxDEkLFKZEbxehXt40LWC3obU0fTxUf4SDUaaVpARqoiKYtCnk5IBHydXdjeVe2l79Ru1EOZvYwjbOddE6VaGMWGreEJh06Kzse2Ka1gswNSut-OuXnHNMG-7EYZeAHAUOP");
        token.setExpireSecond(7200);
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setToken(token.getTokenName());
        accessTokenInfo.setDelFlag("0");
        accessTokenInfo.setExpireSecond(token.getExpireSecond());
        accessTokenInfo.setCreateTime(new Date());
        accessTokenInfo.setUpdateTime(new Date());
        int saveToken = accessTokenInfoMapper.saveToken(accessTokenInfo);
        System.out.println(saveToken);
    }

    @Test
    public void Test02() {
        //准备花色
        ArrayList<String> color = new ArrayList<String>();
        color.add("♠");
        color.add("♥");
        color.add("♦");
        color.add("♣");

        //准备数字
        ArrayList<String> number = new ArrayList<String>();
        Collections.addAll(number,"3","4","5","6","7","8","9","10","J","Q","K","A","2");

        //定义一个map集合：用来将数字与每一张牌进行对应
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        int index = 0;
        for (String thisNumber : number) {
            for (String thisColor : color) {
                map.put(index++, thisColor + thisNumber);
            }
        }

        //一副54张的牌 ArrayList里边为0-53的数的新牌
        ArrayList<Integer> cards = new ArrayList<Integer>();

        for (int i = 0; i <= 53; i++) {
            cards.add(i);
        }

        //洗牌
        Collections.shuffle(cards);
        String s1 = map.get(cards.get(0));
        String s2 = map.get(cards.get(1));
        String s3 = map.get(cards.get(2));
        System.out.println(s1 + " " + s2 + " " + s3);
    }


}
