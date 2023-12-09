package com.tp.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.tp.application.GetScanner;

public class Inputs {
  static Scanner scanner = GetScanner.getScanner();

  public static int getIntInput(String message, String errorMessage) {
    int value = 0;

    while (true) {
      try {
        System.out.print(message);
        value = scanner.nextInt();
        break;
      } catch (InputMismatchException e) {
        System.out.println(errorMessage);
        scanner.nextLine();
      }
    }

    scanner.nextLine();

    return value;
  }

}
