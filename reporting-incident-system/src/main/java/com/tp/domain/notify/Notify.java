package com.tp.domain.notify;

public interface Notify {
  void emitMessage(String message, String environmentData);
}
