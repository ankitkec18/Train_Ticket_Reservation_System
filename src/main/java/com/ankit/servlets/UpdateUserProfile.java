package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import com.ankit.bean.TrainException;
import com.ankit.bean.UserBean;
import com.ankit.constant.UserRole;
import com.ankit.service.UserService;
import com.ankit.service.impl.UserServiceImpl;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/updateuserprofile")
public class UpdateUserProfile extends HttpServlet {
	private UserService userService = new UserServiceImpl(UserRole.CUSTOMER);

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		UserBean ub = TrainUtil.getCurrentCustomer(req);

		String fName = req.getParameter("firstname");
		String lName = req.getParameter("lastname");
		String addR = req.getParameter("address");
		long phNo = Long.parseLong(req.getParameter("phone"));
		String mailId = req.getParameter("mail");

		ub.setFName(fName);
		ub.setLName(lName);
		ub.setAddr(addR);
		ub.setPhNo(phNo);
		ub.setMailId(mailId);

		try {
			String message = userService.updateUser(ub);
			if ("SUCCESS".equalsIgnoreCase(message)) {

				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				pw.println("<div class='tab'>" + "		<p1 class='menu'>" + "	Hello " + ub.getFName()
						+ " ! Welcome to our new NITRTC Website" + "		</p1>" + "	</div>");
				pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
						+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
						+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
				pw.println("<div class='tab'>Your Profile has Been Successfully Updated</div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
						+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
						+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
				pw.println("<div class='tab'>Please Enter the valid Information</div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());

		}

	}

}
