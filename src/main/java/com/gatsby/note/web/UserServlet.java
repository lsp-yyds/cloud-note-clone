package com.gatsby.note.web;

import com.gatsby.note.po.User;
import com.gatsby.note.service.UserService;
import com.gatsby.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @PACKAGE_NAME: com.gatsby.note.web
 * @NAME: UserServlet
 * @AUTHOR: Jonah
 * @DATE: 2023/5/18
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getParameter("actionName");
        if ("login".equals(actionName)) {
            userLogin(req, resp);
        } else if ("register".equals(actionName)) {
            userRegister(req, resp);
        } else if ("ajaxRegister".equals(actionName)) {
            userAjaxRegister(req, resp);
        }
    }

    private void userAjaxRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf8");

        String username = req.getParameter("username");

        String msg = userService.ajaxRegister(username);
        PrintWriter writer = resp.getWriter();
        writer.write(msg);
    }

    private void userRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String cpassword = req.getParameter("cpassword");

        ResultInfo<User> resultInfo = userService.userRegister(username, password, cpassword);

        if (resultInfo.getCode() == 1){
            resp.sendRedirect("login.jsp");
        }else{
            req.setAttribute("resultInfo",resultInfo);
            req.getRequestDispatcher("register.jsp").forward(req,resp);
        }
    }

    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ResultInfo<User> resultInfo = userService.userLogin(username, password);

        if (resultInfo.getCode() == 1) {
            req.getSession().setAttribute("resultInfo", resultInfo);
            String rem = req.getParameter("rem");
            if ("1".equals(rem)) {
                Cookie cookie = new Cookie("user", username + "-" + password);
                cookie.setMaxAge(60*60*24*3);
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }

            resp.sendRedirect("index.jsp");
        } else {
            req.setAttribute("resultInfo", resultInfo);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
