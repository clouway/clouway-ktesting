package com.clouway.testing.common.time;

import java.util.Calendar;
import java.util.Date;

/**
 * CalendarUtil an utility helper class for creation of different kind of dates.
 *
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public final class CalendarUtil {

  public static Date january(int year, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.MONTH, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  public static Date february(int year, int day) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.set(Calendar.YEAR, year);
      calendar.set(Calendar.DAY_OF_MONTH, day);
      calendar.set(Calendar.MONTH, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      return calendar.getTime();
    }


  public static Date today(int hour, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
}
