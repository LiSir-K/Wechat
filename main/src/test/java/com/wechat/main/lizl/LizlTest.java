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

import java.util.Date;

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

}
