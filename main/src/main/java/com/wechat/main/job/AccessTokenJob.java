package com.wechat.main.job;

import com.wechat.main.entity.token.AccessToken;
import com.wechat.main.entity.token.AccessTokenInfo;
import com.wechat.main.mapper.AccessTokenInfoMapper;
import com.wechat.main.service.WeChatService;
import com.wechat.main.util.date.DateUtil;
import com.wechat.main.util.sql.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class AccessTokenJob {

    @Autowired
    private WeChatService weChatService;

    @Scheduled(cron = "0 0 0/2 * * ?")
    private void flushAccessToken(){
        Date date = new Date();
        String formatDate = DateUtil.formatDate(date);
        log.info("定时任务--刷新token定时任务启动时间{}",formatDate);
        try {
            AccessToken accessToken = weChatService.getAccessToken();
            if(null == accessToken){
                return;
            }
            AccessTokenInfoMapper accessTokenInfoMapper = MapperUtil.getInstance().getAccessTokenInfoMapper();
            AccessTokenInfo oldTokenInfo = accessTokenInfoMapper.getTokenInfoByDelFlag();
            if(!oldTokenInfo.getToken().equals(accessToken.getTokenName())){
                log.info("定时任务--刷新token--旧token创建时间{}",DateUtil.formatDate(oldTokenInfo.getCreateTime()));
                int updateToken = accessTokenInfoMapper.updateOldTokenDelFlag(oldTokenInfo.getId());
                if(updateToken == 0){
                    log.info("定时任务--刷新token失败 保存修改旧token失败");
                }
                AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
                accessTokenInfo.setToken(accessToken.getTokenName());
                accessTokenInfo.setDelFlag("0");
                accessTokenInfo.setCreateTime(new Date());
                accessTokenInfo.setUpdateTime(new Date());
                accessTokenInfo.setExpireSecond(accessToken.getExpireSecond());
                int saveToken = accessTokenInfoMapper.saveToken(accessTokenInfo);
                if(saveToken == 0){
                    log.info("定时任务--刷新token失败 保存新token失败");
                    return;
                }
            }

        } catch (Exception e){
            log.info("定时任务--刷新token失败 异常信息{}",e.getMessage());
            return;
        }
        log.info("定时任务--刷新token成功");
    }

}
