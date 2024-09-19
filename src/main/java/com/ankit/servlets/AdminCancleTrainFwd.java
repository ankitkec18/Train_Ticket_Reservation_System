package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.constant.UserRole;
import com.ankit.utility.TrainUtil;

@WebServlet("/cancletrainfwd")
public class AdminCancleTrainFwd extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
		RequestDispatcher rd = req.getRequestDispatcher("CancleTrain.html");
		rd.forward(req, res);

	}

}
