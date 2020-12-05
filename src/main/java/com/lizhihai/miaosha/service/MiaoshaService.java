package com.lizhihai.miaosha.service;

import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.GoodsKey;
import com.lizhihai.miaosha.config.redis.key.MiaoshaGoodsKey;
import com.lizhihai.miaosha.config.redis.key.MiaoshaKey;
import com.lizhihai.miaosha.config.redis.key.OrderKey;
import com.lizhihai.miaosha.exception.GlobalException;
import com.lizhihai.miaosha.util.MD5Util;
import com.lizhihai.miaosha.vo.entity.*;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

@Service
public class MiaoshaService {
    private static Logger logger= LoggerFactory.getLogger(MiaoshaService.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    private char[] opt={'+','-','*'};

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsParam detail) {
        logger.info(detail.toString());
        int rs = goodsService.reduceStock(detail);
        if (rs<=0){
            redisService.set(MiaoshaGoodsKey.isGoodsOver, ""+detail.getId(),true);
            throw new GlobalException(CodeMsg.MIAOSHA_OVER);
        }
        OrderInfo orderInfo=orderService.createOrder(user,detail);
        return orderInfo;
    }

    public long getMiaoshaResult(Long userId, Long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order!=null){
            return order.getOrderId();
        }else{
            boolean isOver = redisService.exit(MiaoshaGoodsKey.isGoodsOver, "" + goodsId);
            if (isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    public String createMiaoshaPath(Long userId, Long goodsId) {
        String path = MD5Util.md5(UUID.randomUUID() + "" + userId);
        redisService.set(MiaoshaKey.getMiaoshaPath,userId+""+goodsId,path);
        return path;
    }

    public boolean checkPath(Long userId, Long goodsId,String path) {
        if (path==null){
            return false;
        }
        String rpath = redisService.get(MiaoshaKey.getMiaoshaPath, userId + "" + goodsId, String.class);
        return rpath.equals(path);
    }

    public BufferedImage createVerifyCodeImg(MiaoshaUser user, Long goodsId) {
        int width=85;
        int height=35;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,width,height);

        g.setColor(Color.black);
        g.drawRect(0,0,width-1,height-1);

        Random random = new Random();
        for (int i = 0; i <100 ; i++) {
            g.drawOval(random.nextInt(width),random.nextInt(height),0,0);
        }
        String verifyCode=generateVerifyCode(random);
        g.setColor(new Color(0,100,0));
        g.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,24));
        g.drawString(verifyCode,8,24);
        g.dispose();

        int rnd=calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode,user.getId()+"_"+goodsId,rnd);
        return image;
    }

    private Integer calc(String verifyCode) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Integer eval=null;
        try {
             eval = (Integer) engine.eval(verifyCode);
            return eval;
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateVerifyCode(Random random) {
        int numb1 = random.nextInt(10);
        int numb2 = random.nextInt(10);
        int numb3 = random.nextInt(10);
        char optL=opt[random.nextInt(3)];
        char optR=opt[random.nextInt(3)];
        return ""+numb1+optL+numb2+optR+numb3;
    }


    public boolean checkVerifyCode(MiaoshaUser user, Long goodsId, Integer verifyCode) {
        if (user==null||goodsId<=0||verifyCode==null){
            return false;
        }
        Integer number = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "_" + goodsId, Integer.class);
        boolean rs=(number==verifyCode);
        if (rs){
            redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "_" + goodsId);
        }
        return rs;
    }
}
