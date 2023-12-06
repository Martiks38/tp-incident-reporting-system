package com.tp.domain.notify;

public abstract class NotifyDecorator implements Notify {
  protected Notify wrappee;

  public NotifyDecorator(Notify medium) {
    this.wrappee = medium;
  }

  @Override
  public void emitMessage(String message, String environmentData) {
    wrappee.emitMessage(message, environmentData);
  }

}
