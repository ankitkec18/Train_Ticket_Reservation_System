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
@WebServlet("/changeuserpwd")
public class ChangeUserPwd extends HttpServlet {
	private UserService userService = new UserServiceImpl(UserRole.CUSTOMER);

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		UserBean currentUser = TrainUtil.getCurrentCustomer(req);

		try {
			String u_Name = req.getParameter("username");
			String oldPWord = (String) req.getParameter("oldpassword");
			String newPWord = req.getParameter("newpassword");
			if (currentUser.getMailId().equals(u_Name)) {
				if (currentUser.getPWord().equals(oldPWord)) {
					currentUser.setPWord(newPWord);
					String message = userService.updateUser(currentUser);
					
					if ("SUCCESS".equalsIgnoreCase(message)) {
						RequestDispatcher rd = req.getRequestDispatcher("UserLogin.html");
						rd.include(req, res);
						TrainUtil.logout(res);
						pw.println(
								"<div class='tab'>Your Username and Password has Been Updated Successfully<br/>Please Login Again !</div>");
					} else {
						RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
						rd.include(req, res);
						pw.println("<div class='tab'>" + "		<p1 class='menu'>" + "	Hello " + currentUser.getFName()
								+ " ! Welcome to our new NITRTC Website" + "		</p1>" + "	</div>");
						
						pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>View Profile</a></p1>&nbsp;"
								+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>&nbsp;"
								+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
						pw.println("<div class='tab'>Invalid Username and Old Password !</div>");
					}
				} else {
					RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
					rd.include(req, res);
					pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
							+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
							+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
					pw.println("<div class='tab'>Wrong Old PassWord!</div>");
				}
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
						+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
						+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
				pw.println("<div class='tab'>Invalid UserName</div>");
			}

		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}

}
