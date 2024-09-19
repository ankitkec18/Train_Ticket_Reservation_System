package com.ankit.service;
import java.util.List;

import com.ankit.bean.HistoryBean;
import com.ankit.bean.TrainException;


public interface BookingService {

	public List<HistoryBean> getAllBookingsByCustomerId(String customerEmailId) throws TrainException;

	public HistoryBean createHistory(HistoryBean bookingDetails) throws TrainException;

}
