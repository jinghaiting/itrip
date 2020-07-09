package cn.itrip.auth.controller;

import cn.itrip.auth.exception.UserLoginFailedException;
import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private  UserService userService;
    @Resource
    private TokenService tokenService;


    @RequestMapping(value = "/dologin", method = RequestMethod.POST, produces = "application/json")
    public Dto doLogin(@RequestParam String name, @RequestParam String password, HttpServletRequest request){

        ItripUser user = null;

        //-----------------------------调用登录方法------------------------------------
        // 1. 如果两个用户名和密码都不空再往下处理，否则返回Dto说参数错误
        if(EmptyUtils.isNotEmpty(name) && EmptyUtils.isNotEmpty(password)){   // EmptyUtils.isNotEmpty(name)也可以写为： ！EmptyUtils.isEmpty(name)
            try {
                user = userService.login(name, MD5.getMd5(password, 32));  // 密码存入数据库的时候就是按这种方式进行加密的，所以这里传入的参数也是加密后的
            } catch(UserLoginFailedException e){
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);  // DtoUtil已经将Dto封装好了，这样就不用我们每次去new Dto对象，再去set属性值，这样代码乱。封装成工具类，只需要传参数就可以，美滋滋。
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
            }
            // 2. 如果查出来的用户不空，再往下处理，否则返回Dto说参数错误
            if(!EmptyUtils.isEmpty(user)){
                // 2.1 用户的所以信息都ok，验证成功
                // --------------------------生成token----------------------------------
                String token = tokenService.generateToken(request.getHeader("user-agent"), user);
                // ---------------------------将token保存到redis------------------------------------
                tokenService.saveTokenAndUserToRedis(token, user);
                // 前端带来的vo(参数三个：token, 过期时间，生成时间)
                ItripTokenVO itripTokenVO = new ItripTokenVO(token,
                        Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT * 1000,
                        Calendar.getInstance().getTimeInMillis());
                // ----------------------------响应数据，返回Dto--------------------------------
                return DtoUtil.returnDataSuccess(itripTokenVO);

            }else{
                return DtoUtil.returnFail("用户名或者密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }

        }else{
            return DtoUtil.returnFail("提交的参数有误，请检查！", ErrorCode.AUTH_PARAMETER_ERROR);   // 用户名和密码不能保证都非空
        }

    }


}
