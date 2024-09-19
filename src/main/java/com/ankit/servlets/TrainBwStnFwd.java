package com.ankit.servlets;



import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/trainbwstnfwd")
public class TrainBwStnFwd extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		RequestDispatcher rd = req.getRequestDispatcher("TrainBwStn.html");
		rd.forward(req, res);

	}

}
