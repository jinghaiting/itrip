package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {

    // 登录
    public ItripUser login(String userCode, String password) throws Exception;


}
