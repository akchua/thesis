package com.thesis.tremor.utility.format;

import java.time.Duration;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   7 Aug 2017
 */
public class DurationFormatter {

	public static String formatDuration(Duration duration) {
	    long seconds = duration.getSeconds();
	    long absSeconds = Math.abs(seconds);
	    String positive = String.format(
	        "%d:%02d:%02d",
	        absSeconds / 3600,
	        (absSeconds % 3600) / 60,
	        absSeconds % 60);
	    return seconds < 0 ? "-" + positive : positive;
	}
}
