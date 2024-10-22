package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.UserBean;
import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/edituserprofile")
public class EditUserProfile extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		UserBean ub = TrainUtil.getCurrentCustomer(req);
		RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
		rd.include(req, res);
		
		pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>View Profile</a></p1>&nbsp;"
				+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>&nbsp;"
				+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
		pw.println("<div class='tab1'>Profile Update</div>");
		pw.println("<div class='tab'>" + "<table><form action='updateuserprofile' method='post'>"
				+ "<tr><td>User Name :</td><td><input type='text' name='username' value='" + ub.getMailId()
				+ "' disabled></td></tr>" + "<tr><td>First Name :</td><td><input type='text' name='firstname' value='"
				+ ub.getFName() + "'></td></tr>"
				+ "<tr><td>Last Name :</td><td><input type='text' name='lastname' value='" + ub.getLName()
				+ "'></td></tr>" + "<tr><td>Address :</td><td><input type='text' name='address' value='" + ub.getAddr()
				+ "'></td></tr>" + "<tr><td>Phone No:</td><td><input type='text' name='phone' value='" + ub.getPhNo()
				+ "'></td></tr>" + "<tr><td><input type='hidden' name='mail' value='" + ub.getMailId() + "'></td></tr>"
				+ "<tr><td></td><td><input type='submit' name='submit' value='Update Profile'></td></tr>"
				+ "</form></table>" + "</div>");

	}

}
