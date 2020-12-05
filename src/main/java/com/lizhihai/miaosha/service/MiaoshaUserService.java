package com.lizhihai.miaosha.service;

import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.MiaoshaUserKey;
import com.lizhihai.miaosha.config.redis.key.UserKey;
import com.lizhihai.miaosha.dao.MiaoshaUserMapper;
import com.lizhihai.miaosha.exception.GlobalException;
import com.lizhihai.miaosha.util.MD5Util;
import com.lizhihai.miaosha.util.UUIDUtil;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import com.lizhihai.miaosha.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class MiaoshaUserService {

    public final static String COOKIE_NAME_TOKEN="token" ;

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    RedisService redisService;

    public MiaoshaUser login(HttpServletResponse response,LoginParam loginParam){
        if (loginParam==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginParam.getMobile();
        String password = loginParam.getPassword();
        MiaoshaUser user = miaoshaUserMapper.getById(Long.valueOf(mobile));
        if (user==null){
            throw new GlobalException(CodeMsg.USER_EXIT);
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.fromPassToDBPass(password, salt);
        if (dbPass.equals(calcPass)){
            //生存cookie
            String token = UUIDUtil.uuid();
            addCookie(response,token,user);
            return user;
        }else{
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //有效期
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getById(Long id){
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user!=null){
            return user;
        }

        //访问数据库
        user = miaoshaUserMapper.getById(id);
        if (user!=null){
            redisService.set(MiaoshaUserKey.getById,""+id,user);
        }
        return user;
    }

    public boolean updatePass(String token,Long id ,String pass){
        //取user
        MiaoshaUser user = getById(id);
        if (user==null){
            throw new GlobalException(CodeMsg.MOBOLE_BIND_ERROR);
        }
        MiaoshaUser toBeUpdate=new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.fromPassToDBPass(pass,user.getSalt()));
        long update = miaoshaUserMapper.update(toBeUpdate);
        if (update<=0){
            throw new GlobalException(CodeMsg.PASSWORD_UPDATE);
        }
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        user.setPassword(MD5Util.fromPassToDBPass(pass,user.getSalt()));
        redisService.set(MiaoshaUserKey.token,token,user);
        return true;
    }

    public MiaoshaUser getBytoKen(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (user!=null){
            addCookie(response,token,user);
            return user;
        }
        return user;
    }

    public String createToken(HttpServletResponse response, LoginParam loginParam) {
        if (loginParam==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile=loginParam.getMobile();
        String password=loginParam.getPassword();
        MiaoshaUser user = miaoshaUserMapper.getById(Long.parseLong(mobile));
        if (user==null){
            throw  new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.fromPassToDBPass(password, salt);

        if (!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return token;
    }
}
