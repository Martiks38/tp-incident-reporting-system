package com.tp.domain.notify;

public class WhatsappDecorator extends NotifyDecorator {

  public WhatsappDecorator(Notify medium) {
    super(medium);
  }

  @Override
  public void emitMessage(String message, String environmentData) {
    System.out.print("Notificación por medio de WhatsApp.\n");

    super.emitMessage(message, environmentData);
  }

}
