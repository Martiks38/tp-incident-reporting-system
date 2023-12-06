package com.tp.domain.notify;

public class NotifyBasic implements Notify {

  @Override
  public void emitMessage(String message, String environmentData) {
    System.out.print(message);
  }

}
