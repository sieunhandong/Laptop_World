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
import java.io.IOException;
import java.util.Vector;
import model.User;

public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("Register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        Vector<User> users = (new UserDAO()).getAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                req.setAttribute("duplicateUsername", "Username already exists");
                req.getRequestDispatcher("Register.jsp").forward(req, resp);
            }
        }

        UserDAO userDao = new UserDAO();
        User user = new User(username, password, fullname, email, phone, address, 1);
        userDao.insert(user);
        req.setAttribute("registerSuccess", "Register successful");
        req.getRequestDispatcher("Register.jsp").forward(req, resp);
    }
}
