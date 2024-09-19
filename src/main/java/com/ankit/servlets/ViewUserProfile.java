package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.UserBean;
import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/viewuserprofile")
public class ViewUserProfile extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		UserBean ub = TrainUtil.getCurrentCustomer(req);
		RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
		rd.include(req, res);
		pw.println("<div class='tab'>" + "		<p1 class='menu'>" + "	Hello " + TrainUtil.getCurrentUserName(req)
				+ " ! Welcome to our new NITRTC Website" + "		</p1>" + "	</div>");
		pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>View Profile</a></p1>&nbsp;"
				+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>&nbsp;"
				+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
		pw.println("<div class='tab'>Users Profile View</div>");
		pw.println("<div class='tab'>" + "<table>" + "<tr><td>Profile Photo :</td><td>Not Available</td></tr>"
				+ "<tr><td>User Name :</td><td>" + ub.getMailId() + "</td></tr>"
				+ "<tr><td>Password :</td><td><input type='password' disabled value='" + ub.getPWord() + "'/></td></tr>"
				+ "<tr><td>First Name :</td><td>" + ub.getFName() + "</td></tr>" + "<tr><td>Last Name :</td><td>"
				+ ub.getLName() + "</td></tr>" + "<tr><td>Address :</td><td>" + ub.getAddr() + "</td></tr>"
				+ "<tr><td>Phone No:</td><td>" + ub.getPhNo() + "</td></tr>" + "<tr><td>Mail Id :</td><td>"
				+ ub.getMailId() + "</td></tr>" + "</table>" + "</div>");

	}

}
