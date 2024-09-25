package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/adminhome")
public class AdminHome extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
		RequestDispatcher rd = req.getRequestDispatcher("AdminHome.html");
		rd.include(req, res);
		
		pw.println("<div class='tab1'>Home</div>");
		
		pw.println("<div class='tab'>Hello " + "Admin"
				+ " ! Good to See You here.<br/> Here you can add new Train "
				+ "cancel, update , schedule, view user , and many more information.<br/>Just go to the Side Menu Links and "
				+ "Explore the Advantages.<br/><br/>Thanks For Being Connected with us!" + "</div>");

	}

}
