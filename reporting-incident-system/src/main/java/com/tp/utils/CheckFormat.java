package com.tp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
  private static final Pattern PATTERN_BUSINESS_NAME = Pattern.compile("^[\\w&',.() -]+$");

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  private static final Pattern ONLY_LETTERS_AND_SPACES = Pattern.compile("^[a-zA-Z ]+$");

  public static boolean isValidBusinessName(String name) {
    return name != null && PATTERN_BUSINESS_NAME.matcher(name).matches();
  }

  public static boolean isValidEmail(String email) {
    Matcher matcher = EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidName(String name) {
    return name != null && ONLY_LETTERS_AND_SPACES.matcher(name).matches();
  }

}
