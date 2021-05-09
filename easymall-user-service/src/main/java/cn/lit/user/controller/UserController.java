package cn.lit.user.controller;

import cn.lit.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user/manage")
public class UserController {
    @Autowired
    private UserService us;
    @RequestMapping("checkUserName")
    public SysResult checkUserName(String userName){
        if(us.userNameExists(userName)){
            //存在
            return SysResult.build(201,"存在了",null);
        }else{
            return SysResult.ok();
        }
    }

    //用户注册表单提交
    @RequestMapping(value="{userId}",method= RequestMethod.PUT)
    public SysResult addUser(User user){
        //Message msg=new Message();

        //判断新增成功失败
        try{
            us.addUser(user);
            return  SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return  SysResult.build(201,"新增失败",null);
        }
    }

    //登录功能 login 实现登录用户名密码校验存储到redis中
    @RequestMapping("login")
    public SysResult doLogin(User user,
                             HttpServletRequest req, HttpServletResponse res){
        //从业务层获取redis的key值 ticket
        String ticket=us.doLogin(user);
        //判断非空 !=null !"".equals()
        if(StringUtils.isNotEmpty(ticket)){
            //非空,登录成功
            //ticket加入到cookie中返回给浏览器
            CookieUtils.setCookie(req,res,"EM_TICKET",ticket);
            return SysResult.ok();
        }
        //登录失败
            return SysResult.build(201,"你传的什么数据",null);
    }
    @RequestMapping("query/{ticket}")
    public SysResult queryUserJson(@PathVariable String ticket){
       String userJson=us.queryUserJson(ticket);
       if(userJson==null){
           //已经超时
           return SysResult.build(201,"都超时了",null);
       }
       return SysResult.build(200,"成功获取",userJson);
    }
}
