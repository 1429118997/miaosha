package com.lizhihai.miaosha.controller;

import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.GoodsKey;
import com.lizhihai.miaosha.config.redis.key.MiaoshaGoodsKey;
import com.lizhihai.miaosha.service.GoodsService;
import com.lizhihai.miaosha.service.MiaoshaService;
import com.lizhihai.miaosha.service.OrderService;
import com.lizhihai.miaosha.service.mq.MQSender;
import com.lizhihai.miaosha.validator.accesslimit.AccessLimit;
import com.lizhihai.miaosha.vo.entity.*;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import com.lizhihai.miaosha.vo.param.MiaoShaMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsParam> goodsParams = goodsService.listGoodsParam();
        if (goodsParams == null) {
            return;
        }
        for (GoodsParam goodsParam : goodsParams) {
            redisService.set(MiaoshaGoodsKey.miaoshaGoodsStock, "" + goodsParam.getId(), goodsParam.getStockCount());
            localOverMap.put(goodsParam.getId(), false);
        }
    }

    /**
     * 5000*20
     * 没优化之前  600qps
     * 解決超卖与redis优化重复购买 2000qps
     * 内存标志,mq异步下单，redis预计库存 优化 3900qps
     *
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result list(MiaoshaUser user, Long goodsId,@PathVariable("path") String path) {

        if (user == null) {
            return Result.error(CodeMsg.SESSION_EXPIRE);
        }


        boolean check = miaoshaService.checkPath(user.getId(), goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUIRE_ILLEGA);
        }

        //内存标志
        if (localOverMap.get(goodsId)) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        //redis预减库存
        //redis减库存无原子性，会导致redis的库存与mysql的库存不一致----lua脚本，分布式锁
        Long stock = redisService.decr(MiaoshaGoodsKey.miaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //判断是否秒杀成功
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        MiaoShaMessage message = new MiaoShaMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        //异步下单
        mqSender.send(message);
        return Result.success(0);
    }

    @PostMapping("/result")
    @ResponseBody
    public Result getMiaoshaResult(MiaoshaUser user, Long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_EXPIRE);
        }
        Long miaoshaResult = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(miaoshaResult);
    }

    @AccessLimit(access = 5,time = 5,login = true)
    @RequestMapping("/path")
    @ResponseBody
    public Result getMiaoshaPath(MiaoshaUser user, Long goodsId,@RequestParam(value = "verifyCode",defaultValue = "0") Integer verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_EXPIRE);
        }
        if (goodsId == null || goodsId <= 0) {
            return Result.error(CodeMsg.REQUIRE_ILLEGA);
        }

        boolean checkVerifyCode = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if (!checkVerifyCode){
            return Result.error(CodeMsg.VERIFYCODE_ERROR);
        }

        String path = miaoshaService.createMiaoshaPath(user.getId(), goodsId);
        return Result.success(path);
    }

    @AccessLimit(access = 5,time = 5,login = true)
    @RequestMapping("/verifyCodeImg")
    @ResponseBody
    public Result getVerifyCodeImg(MiaoshaUser user, Long goodsId, HttpServletResponse response) throws IOException {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_EXPIRE);
        }

        if (goodsId==null||goodsId<=0){
            return Result.error(CodeMsg.REQUIRE_ILLEGA);
        }
        BufferedImage image=miaoshaService.createVerifyCodeImg(user,goodsId);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"JPG",outputStream);
        return null;
    }
}


