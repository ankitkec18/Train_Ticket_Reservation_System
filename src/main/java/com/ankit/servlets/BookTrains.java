package com.ankit.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.ankit.bean.HistoryBean;
import com.ankit.bean.TrainBean;
import com.ankit.bean.TrainException;
import com.ankit.constant.ResponseCode;
import com.ankit.constant.UserRole;
import com.ankit.service.BookingService;
import com.ankit.service.TrainService;
import com.ankit.service.impl.BookingServiceImpl;
import com.ankit.service.impl.TrainServiceImpl;
import com.ankit.utility.TrainUtil;
import com.ankit.utility.TransactionUtil;

@SuppressWarnings("serial")
@WebServlet("/booktrains")
public class BookTrains extends HttpServlet {

	private TrainService trainService = new TrainServiceImpl();
	private BookingService bookingService = new BookingServiceImpl();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		TrainUtil.validateUserAuthorization(req, UserRole.CUSTOMER);

		RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
		rd.include(req, res);

		ServletContext sct = req.getServletContext();

		try {
			int seat = (int) sct.getAttribute("seats");
			String trainNo = (String) sct.getAttribute("trainnumber");
			String journeyDate = (String) sct.getAttribute("journeydate");
			String seatClass = (String) sct.getAttribute("class");

			String userMailId = TrainUtil.getCurrentUserEmail(req);

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date utilDate;
			String date = LocalDate.now().toString();
			utilDate = inputFormat.parse(journeyDate);
			date = outputFormat.format(utilDate);

			TrainBean train = trainService.getTrainById(trainNo);

			if (train != null) {
				int avail = train.getSeats();
				if (seat > avail) {
					pw.println("<div class='tab'><p1 class='menu red'>Only " + avail
							+ " Seats are Available in this Train!</p1></div>");

				} else if (seat <= avail) {
					avail = avail - seat;
					train.setSeats(avail);
					String responseCode = trainService.updateTrain(train);
					
					if (ResponseCode.SUCCESS.toString().equalsIgnoreCase(responseCode)) {

						HistoryBean bookingDetails = new HistoryBean();
						Double totalAmount = train.getFare() * seat;
						bookingDetails.setAmount(totalAmount);
						bookingDetails.setFrom_stn(train.getFrom_stn());
						bookingDetails.setTo_stn(train.getTo_stn());
						bookingDetails.setTr_no(trainNo);
						bookingDetails.setSeats(seat);
						bookingDetails.setMailId(userMailId);
						bookingDetails.setDate(date);
						
						// Generate the custom transaction ID
					    String transactionId = TransactionUtil.generateTransactionId();
					    bookingDetails.setTransId(transactionId);

						HistoryBean transaction = bookingService.createHistory(bookingDetails);
						
						pw.println("<div class='tab'><p class='menu green'>" + seat
								+ " Seats Booked Successfully!<br/><br/> Your Transaction Id is: "
								+ transaction.getTransId() + "</p>" + "</div>");
						pw.println("<div class='tab'>" + "<p class='menu'>" + "<table>"
								+ "<tr><td>PNR No: </td><td colspan='3' style='color:blue;'>" + transaction.getTransId()
								+ "</td></tr><tr><td>Train Name: </td><td>" + train.getTr_name()
								+ "</td><td>Train No: </td><td>" + transaction.getTr_no()
								+ "</td></tr><tr><td>Booked From: </td><td>" + transaction.getFrom_stn()
								+ "</td><td>To Station: </td><td>" + transaction.getTo_stn() + "</td></tr>"
								+ "<tr><td>Date Of Journey:</td><td>" + transaction.getDate()
								+ "</td><td>Time(HH:MM):</td><td>11:23</td></tr><tr><td>Passangers: </td><td>"
								+ transaction.getSeats() + "</td><td>Class: </td><td>" + seatClass + "</td></tr>"
								+ "<tr><td>Booking Status: </td><td style='color:green;'>CNF/S10/35</td><td>Amount Paid:</td><td>&#8377; "
								+ transaction.getAmount() + "</td></tr>" + "</table>" + "</p></div>");

					} else {
						pw.println(
								"<div class='tab'><p1 class='menu red'>Transaction Declined. Try Again !</p1></div>");

					}
				}
			} else {
				pw.println("<div class='tab'><p1 class='menu'>Invalid Train Number !</p1></div>");

			}

		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

		sct.removeAttribute("seat");
		sct.removeAttribute("trainNo");
		sct.removeAttribute("journeyDate");
		sct.removeAttribute("class");
	}

}