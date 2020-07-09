package cn.itrip.auth.exception;

/**
 * 用户登录失败异常
 */
public class UserLoginFailedException extends Exception {

    public UserLoginFailedException(String erorMsg){
        super(erorMsg);
    }

}
