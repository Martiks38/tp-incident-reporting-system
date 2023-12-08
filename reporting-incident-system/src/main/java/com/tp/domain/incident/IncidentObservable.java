package com.tp.domain.incident;

public interface IncidentObservable {
  void subscribe(IncidentObserver client);

  void unsubscribe();

  void notifyObservable();
}
