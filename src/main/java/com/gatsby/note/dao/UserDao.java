package com.gatsby.note.dao;

import com.gatsby.note.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.gatsby.note.dao
 * @NAME: UserDao
 * @AUTHOR: Jonah
 * @DATE: 2023/5/18
 */
public class UserDao {
    public User queryUserByName(String username){

        String sql = "select * from tb_user where uname = ?";

        List<Object> params = new ArrayList<>();
        params.add(username);

        User user = (User) BaseDao.queryRow(sql, params, User.class);

        return user;
    }

    public Integer insertUser(User user){
        String sql = "insert into tb_user(uname,upwd,nick,head,mood) values(?,?,?,?,?)";

        List params = new ArrayList();
        params.add(user.getUname());
        params.add(user.getUpwd());
        params.add(user.getNick());
        params.add(user.getHead());
        params.add(user.getMood());
        int rows = BaseDao.executeUpdate(sql, params);

        return rows;
    }
}
