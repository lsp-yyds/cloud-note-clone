package com.gatsby.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.gatsby.note.dao.UserDao;
import com.gatsby.note.po.User;
import com.gatsby.note.vo.ResultInfo;

/**
 * @PACKAGE_NAME: com.gatsby.note.service
 * @NAME: UserService
 * @AUTHOR: Jonah
 * @DATE: 2023/5/18
 */
public class UserService {

    private UserDao userDao = new UserDao();

    public ResultInfo<User> userLogin(String username, String password) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        User u = new User();
        u.setUname(username);
        u.setUpwd(password);
        resultInfo.setResult(u);

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名或密码不能为空！");
            return resultInfo;
        }

        User user = userDao.queryUserByName(username);

        if (user == null){
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
            return resultInfo;
        }

        if (!DigestUtil.md5Hex(password).equals(user.getUpwd())){
            resultInfo.setCode(0);
            resultInfo.setMsg("密码不正确！");
            return resultInfo;
        }

        resultInfo.setCode(1);
        resultInfo.setResult(user);

        return resultInfo;
    }

    public ResultInfo<User> userRegister(String username, String password, String cpassword) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(cpassword)){
            resultInfo.setCode(0);
            resultInfo.setMsg("必填项不能为空！");
            return resultInfo;
        }

        if (!password.equals(cpassword)){
            resultInfo.setCode(0);
            resultInfo.setMsg("两次密码输入不一致！");
            return resultInfo;
        }

        User u = new User();
        u.setUname(username);
        u.setUpwd(DigestUtil.md5Hex(password));
        resultInfo.setResult(u);

        User user = userDao.queryUserByName(username);

        if (user != null){
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户名已存在！");
            return resultInfo;
        }

        u.setNick(username);
        u.setHead("404.jpg");
        u.setMood("Hello World!");

        Integer rows = userDao.insertUser(u);

        if (rows > 0){
            resultInfo.setCode(1);
            resultInfo.setMsg("注册成功！");
        }else{
            resultInfo.setCode(0);
            resultInfo.setMsg("注册失败！");
        }

        resultInfo.setResult(u);
        return resultInfo;
    }

    public String ajaxRegister(String username) {
        String msg = "";

        if ("".equals(username) || username == null){
            return msg;
        }else{
            User user = userDao.queryUserByName(username);
            if (user != null){
                msg = "该用户已存在";
            }
        }

        return msg;
    }
}
