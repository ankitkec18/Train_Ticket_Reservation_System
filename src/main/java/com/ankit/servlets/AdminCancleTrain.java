package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.TrainException;
import com.ankit.constant.ResponseCode;
import com.ankit.constant.UserRole;
import com.ankit.service.TrainService;
import com.ankit.service.impl.TrainServiceImpl;
import com.ankit.utility.TrainUtil;

@WebServlet("/admincancletrain")
public class AdminCancleTrain extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrainService trainService = new TrainServiceImpl();

	/**
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
		try {
			String trainNo = req.getParameter("trainno");
			String message = trainService.deleteTrainById(trainNo);
			if (ResponseCode.SUCCESS.toString().equalsIgnoreCase(message)) {
				RequestDispatcher rd = req.getRequestDispatcher("CancleTrain.html");
				rd.include(req, res);
				pw.println("<div class='main'><p1 class='menu'>Train number " + trainNo
						+ " has been Deleted Successfully.</p1></div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("CancleTrain.html");
				rd.include(req, res);
				pw.println("<div class='tab'><p1 class='menu'>Train No." + trainNo + " is Not Available !</p1></div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}

}
