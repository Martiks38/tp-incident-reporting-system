package com.tp.application;

import java.util.Scanner;

public class GetScanner {

  private static GetScanner instance;
  private static Scanner scanner;

  private GetScanner() {
    Scanner scn = new Scanner(System.in);

    scanner = scn;
  }

  public static Scanner getScanner() {
    if (instance == null) {
      instance = new GetScanner();
    }

    return scanner;
  }

  public static void closeScanner() {
    scanner.close();
  }

}
