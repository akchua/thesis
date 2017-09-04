package com.thesis.tremor.beans;

import java.util.Calendar;
import java.util.Date;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public class DateDuration {

	private Date from;
	
	private Date to;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	/**
	 * Inclusive end date.
	 * @param to The end date
	 */
	public void setTo(Date to) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(to);
		calendar.add(Calendar.HOUR_OF_DAY, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		this.to= calendar.getTime();
	}
}
