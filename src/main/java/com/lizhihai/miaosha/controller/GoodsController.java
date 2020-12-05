package com.lizhihai.miaosha.controller;

import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.GoodsKey;
import com.lizhihai.miaosha.config.redis.key.OrderKey;
import com.lizhihai.miaosha.dao.GoodsMapper;
import com.lizhihai.miaosha.service.GoodsService;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.Result;
import com.lizhihai.miaosha.vo.param.GoodsDetailParam;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


    /**
     * 没优化之前  270
     * 页面缓存   7000
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,MiaoshaUser user, Model model) {
        String html = redisService.get(GoodsKey.goodsListKey, "", String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user", user);
        List<GoodsParam> goodsList = goodsService.listGoodsParam();
        model.addAttribute("goodsList", goodsList);

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.goodsListKey,"",html);
        }
        return html;
    }

   /* @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(@PathVariable("goodsId") long goodsId, Model model, MiaoshaUser user,HttpServletRequest request, HttpServletResponse response) {
        String html = redisService.get(OrderKey.orderDetail, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user", user);
        GoodsParam goods = goodsService.getGoodsDetailByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startDate = goods.getStartDate().getTime();
        long endDate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();


        int miaoshaStatus=0;
        int remianSecond=0;

        if (now>endDate){    //已经结束
            miaoshaStatus=2;
            remianSecond=-1;
        }else if (now<startDate){  //开没开始
            miaoshaStatus=0;
            remianSecond=(int)((startDate-now)/1000);
        }else{
            miaoshaStatus=1;
            remianSecond=0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remianSecond",remianSecond);
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(OrderKey.orderDetail,""+goodsId,html);
        }
        return html;

    }
*/

    @RequestMapping(value = "/to_detail")
    @ResponseBody
    public Result detail(Long goodsId, Model model, MiaoshaUser user, HttpServletRequest request, HttpServletResponse response) {
        GoodsParam goods = goodsService.getGoodsDetailByGoodsId(goodsId);
        long startDate = goods.getStartDate().getTime();
        long endDate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus=0;
        int remianSecond=0;

        if (now>endDate){    //已经结束
            miaoshaStatus=2;
            remianSecond=-1;
        }else if (now<startDate){  //开没开始
            miaoshaStatus=0;
            remianSecond=(int)((startDate-now)/1000);
        }else{
            miaoshaStatus=1;
            remianSecond=0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remianSecond",remianSecond);

        GoodsDetailParam param = new GoodsDetailParam();
        param.setUser(user);
        param.setGoods(goods);
        param.setMiaoshaStatus(miaoshaStatus);
        param.setRemianSecond(remianSecond);
        return Result.success(param);
    }

}
