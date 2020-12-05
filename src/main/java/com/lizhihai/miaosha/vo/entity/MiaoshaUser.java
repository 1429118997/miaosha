package com.lizhihai.miaosha.vo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaUser  implements Serializable {
    /**
    * 用户ID，手机号码
    */
    private Long id;

    private String nickname;

    /**
    * MD5(MD5(pass明文+固定salt) + salt)
    */
    private String password;

    private String salt;

    /**
    * 头像，云存储的ID
    */
    private String head;

    /**
    * 注册时间
    */
    private Date registerDate;

    /**
    * 上蔟登录时间
    */
    private Date lastLoginDate;

    /**
    * 登录次数
    */
    private Integer loginCount;
}