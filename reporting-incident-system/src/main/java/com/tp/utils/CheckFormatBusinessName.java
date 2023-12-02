package com.tp.utils;

import java.util.regex.Pattern;

public class CheckFormatBusinessName {
  private static final Pattern PATTERN_BUSINESS_NAME = Pattern.compile("^[\\w&',.() -]+$");

  public static boolean isValidName(String name) {
    return name != null && PATTERN_BUSINESS_NAME.matcher(name).matches();
  }

}
