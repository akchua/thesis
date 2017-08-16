package com.thesis.tremor.database.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.thesis.tremor.database.entity.base.BaseObject;
import com.thesis.tremor.utility.DateUtil;
import com.thesis.tremor.utility.format.DateFormatter;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   7 Aug 2017
 */
@Entity(name = "Session")
@Table(name = Session.TABLE_NAME)
public class Session extends BaseObject {

	private static final long serialVersionUID = -4202887454703992031L;

	public static final String TABLE_NAME = "session";
	
	private Date dateDone;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "requested_on")
	public Date getDateDone() {
		return dateDone;
	}
	
	@Transient
	public String getFormattedDateDone() {
		final String formattedDateDone;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDone);
		
		if(cal.getTimeInMillis() == DateUtil.getDefaultDateInMillis()) {
			formattedDateDone = "n/a";
		} else {
			formattedDateDone = DateFormatter.shortFormat(dateDone);
		}
		return formattedDateDone;
	}

	public void setDateDone(Date dateDone) {
		this.dateDone = dateDone;
	}
}