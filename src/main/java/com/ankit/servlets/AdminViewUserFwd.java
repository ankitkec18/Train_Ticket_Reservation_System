package com.ankit.servlets;

import java.io.*;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.UserBean;
import com.ankit.bean.TrainException;
import com.ankit.constant.UserRole;
import com.ankit.service.UserService;
import com.ankit.service.impl.UserServiceImpl;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/adminviewuserfwd")
public class AdminViewUserFwd extends HttpServlet {

    private UserService userService = new UserServiceImpl(UserRole.CUSTOMER);

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
        try {
            List<UserBean> users = userService.getAllUsers();
            if (users != null && !users.isEmpty()) {
                RequestDispatcher rd = req.getRequestDispatcher("ViewUsers.html");
                rd.include(req, res);
                pw.println("<div class='tab1'>Registered Users</div>");
                pw.println("<div class='tab'><table><tr><th>Email Id</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Phone No</th></tr>");

                for (UserBean user : users) {
                    pw.println("<tr>" +
                               "<td>" + user.getMailId() + "</td>" +
                               "<td>" + user.getFName() + "</td>" +
                               "<td>" + user.getLName() + "</td>" +
                               "<td>" + user.getAddr() + "</td>" +
                               "<td>" + user.getPhNo() + "</td>" +
                               "</tr>");
                }
                pw.println("</table></div>");
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("ViewUsers.html");
                rd.include(req, res);
                pw.println("<div class='main'><p1 class='menu red'>No Registered Users</p1></div>");
            }
        } catch (Exception e) {
            throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
        }
    }
}
