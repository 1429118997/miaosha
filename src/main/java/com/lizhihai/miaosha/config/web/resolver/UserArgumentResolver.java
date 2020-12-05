package com.lizhihai.miaosha.config.web.resolver;

import com.lizhihai.miaosha.config.context.UserContext;
import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.UserKey;
import com.lizhihai.miaosha.service.MiaoshaUserService;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private  static Logger logger = LoggerFactory.getLogger(UserArgumentResolver.class);

    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> c = methodParameter.getParameterType();
        return c == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return UserContext.getUser();

    }

}
