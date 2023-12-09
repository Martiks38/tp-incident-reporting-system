package com.tp.domain.notify;

public class EmailDecorator extends NotifyDecorator {

  public EmailDecorator(Notify medium) {
    super(medium);
  }

  @Override
  public void emitMessage(String message, String environmentData) {
    System.out.print("\nNotificación por medio de email.\n");

    super.emitMessage(message, environmentData);
  }

}
