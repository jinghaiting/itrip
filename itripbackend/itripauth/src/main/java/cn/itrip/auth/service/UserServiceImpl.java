package cn.itrip.auth.service;

import cn.itrip.auth.exception.UserLoginFailedException;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private ItripUserMapper itripUserMapper;

    /**
     * 登录，信息全部符合，返回ItripUser,否则返回空
     */
    @Override
    public ItripUser login(String userCode, String password) throws Exception {
        // 根据userCode查询user
        ItripUser itripUser = this.findByUserCode(userCode);
        // 要求：查到的用户不空，且密码正确，且激活
        if(itripUser != null && itripUser.getUserPassword().equals(password)){
            if(itripUser.getActivated() != 1){
                throw new UserLoginFailedException("该用户未激活，登录失败！");
            }
            return itripUser;
        }else{
            return null;  // 走这条的可能性：（1）根据userCode查询为空，说明userCode错误 （2）user存在但是密码不匹配，也就是密码错误
        }
    }

    /**
     * 根据userCode查询是否存在该用户，调dao层方法
     */
    private ItripUser findByUserCode(String userCode) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        List<ItripUser> list = itripUserMapper.getItripUserListByMap(param);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}

