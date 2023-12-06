package com.tp.domain.notify;

public class WhatsappDecorator extends NotifyDecorator {

  WhatsappDecorator(Notify medium) {
    super(medium);
  }

  @Override
  public void emitMessage(String message, String environmentData) {
    super.emitMessage(message, environmentData);

    System.out.print("Notificación por medio de WhatsApp.\n");
  }

}
