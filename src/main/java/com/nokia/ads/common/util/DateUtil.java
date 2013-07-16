package com.nokia.ads.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final Log logger = Log.getLogger(DateUtil.class);

	// Using a ThreadLocal because SimpleDateFormat is not Thread Safe.
	private static final SimpleDateFormat[] validDateFormats =
	// Ordered by precedence for parsing
	// Put the 'yy' for each format above the 'yyyy'; This way 2 digit years
	// will be formatted correctly
	{ new SimpleDateFormat("dd/MM/yy"), new SimpleDateFormat("dd/MM/yyyy"),
			new SimpleDateFormat("yyMMdd"), new SimpleDateFormat("yyyyMMdd"),
			new SimpleDateFormat("yyyyMMddHHmmss"),
			new SimpleDateFormat("yyyyMMdd HHmmss"),
			new SimpleDateFormat("ddMMyy"), new SimpleDateFormat("ddMMyyyy"),
			new SimpleDateFormat("MMddyy"), new SimpleDateFormat("MMddyyyy"),
			new SimpleDateFormat("dd/MMMMMMMMMM/yy"),
			new SimpleDateFormat("dd/MMMMMMMMMM/yyyy"),
			new SimpleDateFormat("MMMMMMMMMM/dd/yy"),
			new SimpleDateFormat("MMMMMMMMMM/dd/yyyy"),
			new SimpleDateFormat("yyyy/MMMMMMMMMM/dd"),
			new SimpleDateFormat("MM/dd/yy"),
			new SimpleDateFormat("MM/dd/yyyy"),
			new SimpleDateFormat("yy/MM/dd"),
			new SimpleDateFormat("yyyy/MM/dd"),
			new SimpleDateFormat("yy/dd/MM"),
			new SimpleDateFormat("yyyy/dd/MM"),
			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
			new SimpleDateFormat("yyyyddMM"),
			new SimpleDateFormat("dd-MMMMMMMMMM-yy"),
			new SimpleDateFormat("dd-MMMMMMMMMM-yyyy"),
			new SimpleDateFormat("MMMMMMMMMM-dd-yy"),
			new SimpleDateFormat("MMMMMMMMMM-dd-yyyy"),
			new SimpleDateFormat("yy-MMMMMMMMMM-dd"),
			new SimpleDateFormat("yyyy-MMMMMMMMMM-dd"),
			new SimpleDateFormat("yy-dd-MMMMMMMMMM"),
			new SimpleDateFormat("yyyy-dd-MMMMMMMMMM"),
			new SimpleDateFormat("dd-MM-yy"),
			new SimpleDateFormat("dd-MM-yyyy"),
			new SimpleDateFormat("MM-dd-yy"),
			new SimpleDateFormat("MM-dd-yyyy"),
			new SimpleDateFormat("yy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yy-dd-MM"),
			new SimpleDateFormat("yyyy-dd-MM"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),
			new SimpleDateFormat("yyyy-M-d"),
			new SimpleDateFormat("dd.MMMMMMMMMM.yy"),
			new SimpleDateFormat("dd.MMMMMMMMMM.yyyy"),
			new SimpleDateFormat("MMMMMMMMMM.dd.yy"),
			new SimpleDateFormat("MMMMMMMMMM.dd.yyyy"),
			new SimpleDateFormat("yy.MMMMMMMMMM.dd"),
			new SimpleDateFormat("yyyy.MMMMMMMMMM.dd"),
			new SimpleDateFormat("yy.dd.MMMMMMMMMM"),
			new SimpleDateFormat("yyyy.dd.MMMMMMMMMM"),
			new SimpleDateFormat("dd.MM.yy"),
			new SimpleDateFormat("dd.MM.yyyy"),
			new SimpleDateFormat("MM.dd.yy"),
			new SimpleDateFormat("MM.dd.yyyy"),
			new SimpleDateFormat("yy.MM.dd"),
			new SimpleDateFormat("yyyy.MM.dd"),
			new SimpleDateFormat("yy.dd.MM"),
			new SimpleDateFormat("yyyy.dd.MM"),
			new SimpleDateFormat("dd MMMMMMMMMM yy"),
			new SimpleDateFormat("dd MMMMMMMMMM yyyy"),
			new SimpleDateFormat("MMMMMMMMMM dd yy"),
			new SimpleDateFormat("MMMMMMMMMM dd yyyy"),
			new SimpleDateFormat("MMMMMMMMMM dd, yy"),
			new SimpleDateFormat("MMMMMMMMMM dd, yyyy") };

	/**
	 * Get the age given the dob as a Calendar object
	 * 
	 * @param dob
	 *            Calendar object representing the dob
	 * @return age of the person
	 */
	public static int getAge(Calendar dob) {
		Calendar now = Calendar.getInstance();
		return getAgePvt(dob, now);
	}

	// Just useful for UT
	protected static int getAgePvt(Calendar dob, Calendar now) {
		if (dob == null || now == null || !dob.isSet(Calendar.YEAR)
				|| !dob.isSet(Calendar.MONTH))
			return Integer.MIN_VALUE;
		int res = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if ((dob.get(Calendar.MONTH) > now.get(Calendar.MONTH))
				|| (dob.get(Calendar.MONTH) == now.get(Calendar.MONTH)
						&& dob.isSet(Calendar.DAY_OF_MONTH) && dob
						.get(Calendar.DAY_OF_MONTH) > now
						.get(Calendar.DAY_OF_MONTH))) {
			res--;
		}
		return res;
	}

	public static synchronized Calendar parseDateStr(String dateStr) {
		return parseDateStr(dateStr, 0, false);
	}

	/**
	 * Given a date in any reasonable format, try to convert it into a Calendar
	 * object
	 * 
	 * @param dateStr
	 *            A string representing a date
	 * @param lookbackYears
	 *            If the year format is 2 letters, start the 100 year period
	 *            from now-lookbackYears
	 * @param getLatestDate
	 *            If set, and there are multiple matches, send back the latest
	 *            date.
	 * @return a Calendar object representing the date
	 */
	public static synchronized Calendar parseDateStr(String dateStr,
			int lookbackYears, boolean getLatestDate) {
		Calendar cal = null;
		Calendar lookbackYear = Calendar.getInstance();
		lookbackYear.add(Calendar.YEAR, -lookbackYears);

		// Go through all the non-ambiguous formats
		for (SimpleDateFormat df : validDateFormats) {
			try {
				df.setLenient(false);
				df.set2DigitYearStart(lookbackYear.getTime());

				Date d = df.parse(dateStr);
				Calendar cur = Calendar.getInstance();
				cur.setTime(d);
				// If it is the first match, or if getLatestDate is set and the
				// current match is after the previous match
				if (cal == null || (getLatestDate && cur.after(cal))) {
					cal = cur;
				}
			} catch (Exception e) {
				// Just continue in the loop, look for the next pattern
			}
		}
		return cal;
	}

	/**
	 * 将时间清空，获取calendar的年月日毫秒数
	 * 
	 * @param calendar
	 */
	public static Long getDateInMillis(Calendar calendar) {
		if (null == calendar) {
			return null;
		} else {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTimeInMillis();
		}
	}
	public static Date addDay(int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }
}
