package com.tp.utils;

import java.time.LocalDate;
import java.time.ZoneId;

import com.tp.assets.Constant;

public class GetDate {
  static final ZoneId GMTTimeZone = Constant.GMT_TIME_ZONE;

  public static LocalDate calculateDateFromToday(Integer numberDays) {
    LocalDate currentDate = LocalDate.now();

    if (numberDays == null) {
      return currentDate;
    }

    return currentDate
        .atStartOfDay(GMTTimeZone)
        .plusDays(numberDays)
        .toLocalDate();
  }
}
