package com.wechat.main.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.main.config.WechatConfig;
import com.wechat.main.entity.msg.strategy.MsgStrategy;
import com.wechat.main.entity.msg.strategy.MsgStrategyContext;
import com.wechat.main.entity.msg.strategy.MsgStrategyFactory;
import com.wechat.main.entity.token.AccessToken;
import com.wechat.main.entity.token.AccessTokenInfo;
import com.wechat.main.entity.token.Menu;
import com.wechat.main.mapper.AccessTokenInfoMapper;
import com.wechat.main.mapper.MenuMapper;
import com.wechat.main.service.WeChatService;
import com.wechat.main.util.HttpClient;
import com.wechat.main.util.date.DateUtil;
import com.wechat.main.util.sql.MapperUtil;
import com.wechat.main.util.wechat.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {


    public String processRequest(HttpServletRequest request) {
        // 默认返回的文本消息内容
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = XmlUtil.xmlToMap(request);
            // 工厂类生产策略
            MsgStrategy msgStrategy = MsgStrategyFactory.getReceiveMsg(requestMap);
            // 将策略交给执行策略的上下文
            MsgStrategyContext context = new MsgStrategyContext(msgStrategy);
            return context.execute(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public AccessToken getAccessToken() {
        HttpClient httpClient = new HttpClient();
        /**
         * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
         */
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", WechatConfig.APP_ID, WechatConfig.APP_SECRET);
        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
        String result = httpClient.sendGetRequest(Url, "");
        log.info("获取到的access_token="+result);

        //使用FastJson将Json字符串解析成Json对象
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setTokenName(json.getString("access_token"));
        token.setExpireSecond(json.getInteger("expires_in"));

        AccessTokenInfoMapper accessTokenInfoMapper = MapperUtil.getInstance().getAccessTokenInfoMapper();
        AccessTokenInfo oldTokenInfo = accessTokenInfoMapper.getTokenInfoByDelFlag();
        if (null != oldTokenInfo){
            log.info("刷新token--旧token创建时间{}", DateUtil.formatDate(oldTokenInfo.getCreateTime()));
            int updateToken = accessTokenInfoMapper.updateOldTokenDelFlag(oldTokenInfo.getId());
            if(updateToken == 0){
                log.info("修改旧token失败");
            }
        }
        AccessTokenInfo tokenInfo = new AccessTokenInfo();
        tokenInfo.setToken(token.getTokenName());
        tokenInfo.setDelFlag("0");
        tokenInfo.setCreateTime(new Date());
        tokenInfo.setExpireSecond(token.getExpireSecond());
        tokenInfo.setUpdateTime(new Date());
        int saveToken = accessTokenInfoMapper.saveToken(tokenInfo);
        if(saveToken == 0){
            log.info("刷新token失败 保存新token失败");
            return token;
        }

        return token;

    }

    public AccessToken getLocalToken(){
        AccessTokenInfoMapper accessTokenInfoMapper = MapperUtil.getInstance().getAccessTokenInfoMapper();
        AccessTokenInfo tokenInfo = accessTokenInfoMapper.getTokenInfoByDelFlag();
        Date createTime = tokenInfo.getCreateTime();
        Date now = new Date();
        Long l = now.getTime() - createTime.getTime();
        Long hour=(l/(60*60*1000));
        if(hour.intValue() >= 2){
            return this.getAccessToken();
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setTokenName(tokenInfo.getToken());
        accessToken.setExpireSecond(tokenInfo.getExpireSecond());
        return accessToken;
    }

    @Override
    public String setMenu() {
        HttpClient httpClient = new HttpClient();
        AccessToken localToken = this.getLocalToken();
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", localToken.getTokenName());
        MenuMapper menuMapper = MapperUtil.getInstance().getMenuMapper();
        Menu menu = menuMapper.getMenu();
        String result = httpClient.sendGetRequest(Url, menu.getMenu());
        log.info("获取目录返回结果{}",result);
        System.out.println(result);
        return null;
    }
}
