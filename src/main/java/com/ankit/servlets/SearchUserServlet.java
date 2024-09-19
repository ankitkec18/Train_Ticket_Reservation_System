package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.UserBean;
import com.ankit.bean.TrainException;
import com.ankit.service.UserService;
import com.ankit.service.impl.UserServiceImpl;
import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/searchuser")
public class SearchUserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(UserRole.CUSTOMER);

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);

        String emailId = req.getParameter("email");
        if (emailId != null && !emailId.isEmpty()) {
            try {
                UserBean user = userService.getUserByEmailId(emailId);
                RequestDispatcher rd = req.getRequestDispatcher("ViewUserDetails.html");
                rd.include(req, res);

                pw.println("<div class='main'><p1 class='menu'>User Details</p1></div>");
                pw.println("<div class='tab'><table><tr><th>Email Id</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Phone No</th></tr>");
                pw.println("<tr>" +
                           "<td>" + user.getMailId() + "</td>" +
                           "<td>" + user.getFName() + "</td>" +
                           "<td>" + user.getLName() + "</td>" +
                           "<td>" + user.getAddr() + "</td>" +
                           "<td>" + user.getPhNo() + "</td>" +
                           "</tr>");
                pw.println("</table></div>");
            } catch (TrainException e) {
                pw.println("<div class='main'><p1 class='menu red'>User not found</p1></div>");
            }
        } else {
            pw.println("<div class='main'><p1 class='menu red'>Invalid Email ID</p1></div>");
        }
    }
}
