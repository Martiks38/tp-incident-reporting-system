package com.tp.utils;

public class ModifyText {
  public static void LimitCharacterLine(String text, int limit) {
    String[] lines = text.split("\\R");

    for (String line : lines) {
      int length = 0;

      for (String word : line.split("\\s+")) {
        if (length + word.length() > limit) {
          System.out.println();
          length = 0;
        }
        System.out.print(word + " ");
        length += word.length() + 1;
      }
      System.out.println();
    }
  }
}
