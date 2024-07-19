/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;


public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        UserDAO userDao = new UserDAO();
        User user = userDao.getOne(username, password);
        session.setAttribute("user", user);

        if (session.getAttribute("user") == null) {
            req.setAttribute("invalidUser", "Username or Password is invalid");
            req.getRequestDispatcher("/Login.jsp").forward(req, resp);
        } else if (user.getBanned() == 1) {
            resp.sendRedirect("AccessDenied.jsp");
            session.removeAttribute("user");
        } else if (((User) session.getAttribute("user")).getRole_id() == 0) {
            session.setAttribute("fullname", ((User) session.getAttribute("user")).getFullname());
            req.getRequestDispatcher("admin").forward(req, resp);
        } else if (((User) session.getAttribute("user")).getRole_id() == 1) {
            session.setAttribute("fullname", ((User) session.getAttribute("user")).getFullname());
            resp.sendRedirect("customer");
        }
    }

}
