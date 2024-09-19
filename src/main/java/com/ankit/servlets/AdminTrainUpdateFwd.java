package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@SuppressWarnings("serial")
@WebServlet("/updatetrain")
public class AdminTrainUpdateFwd extends HttpServlet {

	/**
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");

		RequestDispatcher rd = req.getRequestDispatcher("AdminUpdateTrain.html");
		rd.forward(req, res);

	}

}
