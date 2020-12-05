package com.lizhihai.miaosha.validator.accesslimit;

import com.alibaba.fastjson.JSONPObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhihai.miaosha.config.context.UserContext;
import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.MiaoshaUserKey;
import com.lizhihai.miaosha.service.MiaoshaUserService;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.nio.charset.Charset;


@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit!=null){
                int access = accessLimit.access();
                int time = accessLimit.time();
                boolean login = accessLimit.login();
                StringBuffer key = new StringBuffer(request.getRequestURI());
                if (login){
                    MiaoshaUser user = getUser(request, response);
                    UserContext.setUser(user);

                    if (user==null){
                        render(response, CodeMsg.USER_NOT_LOGIN);
                        return false;
                    }
                    key.append("_"+user.getId());
                }
                MiaoshaUserKey userKey = MiaoshaUserKey.getKey(time, "access");
                Integer count = redisService.get(userKey, key.toString(), Integer.class);
                if (count==null){
                    redisService.set(userKey, key.toString(), 0);
                }else if (count<access){
                    redisService.inc(userKey, key.toString());
                }else{
                    render(response,CodeMsg.ACCESS_LIMIT);
                    return false;
                }
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream stream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        stream.write(objectMapper.writeValueAsString(codeMsg).getBytes("UTF-8"));
        stream.flush();
        stream.close();
    }

    private MiaoshaUser getUser(HttpServletRequest request,HttpServletResponse response){
        String parameter = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookie = getCookie(request);
        if (StringUtils.isEmpty(parameter) && StringUtils.isEmpty(cookie)) {
            return null;
        }
        String token = StringUtils.isEmpty(parameter) ? cookie : parameter;
        MiaoshaUser user = userService.getBytoKen(response, token);
        return user;
    }

    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length==0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(MiaoshaUserService.COOKIE_NAME_TOKEN)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
