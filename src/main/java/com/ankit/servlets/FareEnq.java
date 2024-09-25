package com.ankit.servlets;


import java.util.List;

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
@WebServlet("/fareenq")
public class FareEnq extends HttpServlet {
	TrainService trainService = new TrainServiceImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		try {
			String fromStation = req.getParameter("fromstation");
			String toStation = req.getParameter("tostation");
			List<TrainBean> trains = trainService.getTrainsBetweenStations(fromStation, toStation);
			if (trains != null && !trains.isEmpty()) {
				RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
				rd.include(req, res);
				pw.println("<div class='tab green'>Fare for Trains BetWeen Station " + fromStation + " and "
						+ toStation + " is as below</div>");
				pw.println("<div class='tab'><table><tr><th>Train Name</th><th>Train No</th>"
						+ "<th>From Stn</th><th>To Stn</th><th>Time</th><th>Seats</th><th>Fare (INR)</th><th>Action</th></tr>");
				for (TrainBean train : trains) {
					int hr = (int) (Math.random() * 24);
					int min = (int) (Math.random() * 60);
					String time = (hr < 10 ? ("0" + hr) : hr) + ":" + ((min < 10) ? "0" + min : min);

					pw.println("" + "<tr><td>" + train.getTr_name() + "</td>" + "<td>" + train.getTr_no() + "</td>"
							+ "<td>" + train.getFrom_stn() + "</td>" + "<td>" + train.getTo_stn() + "</td>" + "<td>"
							+ time + "</td>" + "<td>" + train.getSeats() + "</td>" + "<td>" + train.getFare()
							+ " RS</td><td><a href='booktrainbyref?trainNo=" + train.getTr_no() + "&fromStn="
							+ train.getFrom_stn() + "&toStn=" + train.getTo_stn()
							+ "'><div class='red'>Book Now</div></a></td>" + "</tr>");
				}
				pw.println("</table></div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("TrainBwStn.html");
				rd.include(req, res);
				pw.println("<div class='tab red'>There are no trains Between " + fromStation + " and "
						+ toStation + "</div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}
}
