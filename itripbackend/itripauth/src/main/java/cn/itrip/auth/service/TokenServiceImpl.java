package cn.itrip.auth.service;


import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.common.UserAgentUtil;
import com.alibaba.fastjson.JSONArray;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Resource  // 注意要加注解，否则会报空指针异常
    private RedisAPI redisAPI;

    private String tokenPrefix = "token:";

    private int expire = SESSION_TIMEOUT;

    public void setExpire(int expire){
        this.expire = expire;
    }
    public int getExpire(){
        return expire;
    }


    @Override
    public String generateToken(String agent, ItripUser user) {
        try {
            UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(agent);
            StringBuilder sb = new StringBuilder();
            sb.append(tokenPrefix);   // 统一前缀
            if(userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)){
                if(UserAgentUtil.CheckAgent(agent)){
                    sb.append("MOBILE-");
                }else{
                    sb.append("PC-");
                }
            }else if(userAgentInfo.getDeviceType().equals("Personal computer")){
                sb.append("PC-");
            }else{
                sb.append("MOBILE-");
            }
            sb.append(MD5.getMd5(user.getUserCode(), 32) + "-");  // 加密的用户名称
            sb.append(user.getId() + "-");
            sb.append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "-");
            sb.append(MD5.getMd5(agent, 6));   // 识别客户端的简化实现----6位MD5码

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveTokenAndUserToRedis(String token, ItripUser user) {

        if(token.startsWith(tokenPrefix + "PC-"))
            redisAPI.set(token, expire, JSONArray.toJSONString(user));   // value不能直接传对象，将它转换为json格式的字符串传进去。
        else
            redisAPI.set(token, JSONArray.toJSONString(user));   // 手机认证永不失败，不需要设置过期时间

    }


}
