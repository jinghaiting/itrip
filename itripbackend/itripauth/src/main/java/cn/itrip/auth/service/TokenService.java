package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface TokenService {

    /**
     * 会话超时时间
     */
    public final static int SESSION_TIMEOUT = 2*60*60;  // 默认2h

    /**
     * 生成token
     * @param agent Http头中的user-agent信息
     * @param user 用户信息
     * @return Token格式 (String)
     * 		 PC端： “前缀PC-USERCODE-USERID-CREATIONDATE-RANDOM[6位]”
     *  	移动端：“前缀MOBILE-USERCODE-USERID-CREATIONDATE-RANDOM[6位]”
     */
    public String generateToken(String agent, ItripUser user);


    /**
     * 保存用户信息至redis，存入redis是以键值对存的，这里的key是token，value是Itripuser
     * @param token  作为key存入redis
     * @param user   作为value存入redis
     */
    public void saveTokenAndUserToRedis(String token, ItripUser user);



}
