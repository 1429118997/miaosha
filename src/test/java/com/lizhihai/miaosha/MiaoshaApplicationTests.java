package com.lizhihai.miaosha;

import com.lizhihai.miaosha.service.MiaoshaGoodsService;
import com.lizhihai.miaosha.util.MD5Util;
import com.lizhihai.miaosha.vo.entity.MiaoshaGoods;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
class MiaoshaApplicationTests {
//
//    @Autowired
//    MiaoshaGoodsService miaoshaGoodsService;
//
//
//    @Test
//    void contextLoads() {
//        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
//        miaoshaGoods.setId(1l);
//        miaoshaGoods.setStartDate(new Date(System.currentTimeMillis()+60000));
//        miaoshaGoodsService.updateByPrimaryKeySelective(miaoshaGoods);
//    }
//
//    @Test
//    public void addUser() throws ClassNotFoundException, SQLException, IOException {
//        int length=20000;
//        ArrayList<MiaoshaUser> miaoshaUsers = new ArrayList<>();
//        for ( int i=0;i<length;i++) {
//            MiaoshaUser miaoshaUser = new MiaoshaUser();
//            miaoshaUser.setId(13000000000L+i);
//            miaoshaUser.setSalt("1a2b3c");
//            miaoshaUser.setPassword(MD5Util.inputPassToDBPass("123456",miaoshaUser.getSalt()));
//            miaoshaUser.setNickname("zhangsan"+i);
//            miaoshaUser.setRegisterDate(new Date());
//            miaoshaUser.setLoginCount(1);
//            miaoshaUsers.add(miaoshaUser);
//        }
//        System.out.println("start create user");
////        Class.forName("com.mysql.jdbc.Driver");
////        Connection connection = DriverManager.getConnection(
////                "jdbc:mysql://120.79.195.32:3306/miaosha?useTimezone=true&serverTimezone=CST&useUncoide=true&characterEncoding=utf-8",
////                "root",
////                "lizhihai"
////        );
////        String sql="INSERT INTO `miaosha_user` (`id`,`nickname`,`password`,`salt`,`head`,`register_date`,`login_count`) VALUES(?,?,?,?,?,?,?)";
////        PreparedStatement preparedStatement = connection.prepareStatement(sql);
////        for (MiaoshaUser miaoshaUser : miaoshaUsers) {
////            preparedStatement.setLong(1,miaoshaUser.getId());
////            preparedStatement.setString(2,miaoshaUser.getNickname());
////            preparedStatement.setString(3,miaoshaUser.getPassword());
////            preparedStatement.setString(4,miaoshaUser.getSalt());
////            preparedStatement.setString(5,null);
////            preparedStatement.setTimestamp(6,new Timestamp(miaoshaUser.getRegisterDate().getTime()));
////            preparedStatement.setInt(7,miaoshaUser.getLoginCount());
////            preparedStatement.addBatch();
////        }
////        preparedStatement.executeBatch();
////        preparedStatement.close();
////        connection.close();
//        System.out.println("wirte to file");
//
//        String urlString="http://120.79.195.32/login/create_token";
//        File file = new File("config.txt");
//        if (file.exists()){
//            file.delete();
//        }
//        file.createNewFile();
//        RandomAccessFile raf = new RandomAccessFile(file, "rw");
//        raf.seek(0);
//        for (MiaoshaUser miaoshaUser : miaoshaUsers) {
//            URL url = new URL(urlString);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("POST");
//            con.setDoOutput(true);
//            OutputStream out = con.getOutputStream();
//            String requestParam="mobile="+miaoshaUser.getId()+"&password="+MD5Util.inputPassToFormPass("123456");
//            out.write(requestParam.getBytes());
//            out.flush();
//            InputStream in = con.getInputStream();
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            byte[] buff = new byte[1024];
//            int len=0;
//            while ((len=in.read(buff))>=0){
//                bout.write(buff,0,len);
//            }
//            in.close();
//            bout.close();
//            String response=new String(bout.toByteArray());
//            System.out.println("create token: "+miaoshaUser.getId());
//            String row =miaoshaUser.getId()+","+response;
//            raf.seek(raf.length());
//            raf.write(row.getBytes());
//            raf.write("\r\n".getBytes());
//            System.out.println("write to file :"+miaoshaUser.getId());
//        }
//        raf.close();
//        System.out.println("over");
//
//    }

}
