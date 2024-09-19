package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/adminsearchuserfwd")
public class AdminSearchUserFwd extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
        RequestDispatcher rd = req.getRequestDispatcher("AdminSearchUser.html");
        rd.forward(req, res);
    }
}
