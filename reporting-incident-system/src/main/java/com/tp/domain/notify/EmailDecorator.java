package com.tp.domain.notify;

public class EmailDecorator extends NotifyDecorator {

  public EmailDecorator(Notify medium) {
    super(medium);
  }

  @Override
  public void emitMessage(String message, String environmentData) {
    super.emitMessage(message, environmentData);

    System.out.print("Notificación por medio de email.\n");
  }

}
