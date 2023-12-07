package com.tp.domain.incident;

public interface IncidentObservable {
  void subscribe(IncidentObserver client);

  void unsubscribe(IncidentObserver client);

  void notifyObservable();
}
