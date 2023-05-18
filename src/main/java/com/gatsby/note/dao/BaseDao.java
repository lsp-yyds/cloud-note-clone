package com.gatsby.note.dao;

import com.gatsby.note.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.gatsby.note.dao
 * @NAME: BaseDao
 * @AUTHOR: Jonah
 * @DATE: 2023/5/18
 */
public class BaseDao {
    public static int executeUpdate(String sql, List<Object> params) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = DBUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            if (params != null && params.size() > 0)
                for (int i = 0; i < params.size(); ++i)
                    preparedStatement.setObject(i + 1, params.get(i));

            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, preparedStatement, conn);
        }

        return rows;
    }

    public static Object findSingleValue(String sql, List<Object> params) {
        Object obj = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = DBUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            if (params != null && params.size() > 0)
                for (int i = 0; i < params.size(); ++i)
                    preparedStatement.setObject(i + 1, params.get(i));

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                obj = resultSet.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, conn);
        }

        return obj;
    }

    public static List queryRows(String sql, List<Object> params, Class cls) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = DBUtil.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            if (params != null && params.size() > 0)
                for (int i = 0; i < params.size(); ++i)
                    preparedStatement.setObject(i + 1, params.get(i));

            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();

            int fieldNum = metaData.getColumnCount();

            while (resultSet.next()) {
                Object object = cls.newInstance();

                for (int i = 1; i <= fieldNum; ++i) {
                    String columnLabel = metaData.getColumnLabel(i);
                    Field field = cls.getDeclaredField(columnLabel);
                    String setMethod = "set" + columnLabel.substring(0, 1).toUpperCase() + columnLabel.substring(1);
                    Method method = cls.getDeclaredMethod(setMethod, field.getType());
                    Object value = resultSet.getObject(columnLabel);
                    method.invoke(object, value);
                }

                list.add(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, conn);
        }

        return list;
    }

    public static Object queryRow(String sql, List<Object> params, Class cls) {
        Object obj = null;
        List list = queryRows(sql, params, cls);

        if (list != null && list.size() > 0)
            obj = list.get(0);

        return obj;
    }
}
