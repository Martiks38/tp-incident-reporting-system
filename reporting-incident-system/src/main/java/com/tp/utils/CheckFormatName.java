package com.tp.utils;

import java.util.regex.Pattern;

public class CheckFormatName {
  private static final Pattern ONLY_LETTERS_AND_SPACES = Pattern.compile("^[a-zA-Z ]+$");

  public static boolean isValidName(String name) {
    return name != null && ONLY_LETTERS_AND_SPACES.matcher(name).matches();
  }

}
