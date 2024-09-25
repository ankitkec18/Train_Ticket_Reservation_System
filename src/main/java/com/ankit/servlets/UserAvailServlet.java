package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.ankit.bean.TrainBean;
import com.ankit.bean.TrainException;
import com.ankit.constant.UserRole;
import com.ankit.service.TrainService;
import com.ankit.service.impl.TrainServiceImpl;
import com.ankit.utility.TrainUtil;

@SuppressWarnings("serial")
@WebServlet("/useravail")
public class UserAvailServlet extends HttpServlet {
	private TrainService trainService = new TrainServiceImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		try {

			String trainNo = req.getParameter("trainno");
			TrainBean train = trainService.getTrainById(trainNo);
			if (train != null) {
				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				
				
				pw.println("<div class='tab green'>Available Seats are <p2 class=\"red\"> "
						+ train.getSeats() + " Seats</p2></div>");
				
				pw.println("<div class='tab'>" + "<table>" + "<tr><td class='blue'>Train Name :</td><td>"
						+ train.getTr_name() + "</td></tr>" + "<tr><td class='blue'>Train Number :</td><td>"
						+ train.getTr_no() + "</td></tr>" + "<tr><td class='blue'>From Station :</td><td>"
						+ train.getFrom_stn() + "</td></tr>" + "<tr><td class='blue'>To Station :</td><td>"
						+ train.getTo_stn() + "</td></tr>" + "<tr><td class='blue'>Available Seats:</td><td>"
						+ train.getSeats() + "</td></tr>" + "<tr><td class='blue'>Fare (INR) :</td><td>"
						+ train.getFare() + " RS</td></tr>" + "</table>" + "</div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("Availability.html");
				rd.include(req, res);

				pw.println("<div class='tab'><p1 class='menu'>Train No." + trainNo + " is Not Available !</p1></div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}

}
