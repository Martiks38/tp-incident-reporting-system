package com.tp.domain.incident;

import java.util.List;

import com.tp.application.GetEntityManager;
import com.tp.domain.client.Client;
import com.tp.infrastructure.incident.PersistenceIncident;

import jakarta.persistence.EntityManager;

public class InitSuscribeIncidents {

  public final static void initExecution() {
    EntityManager manager = GetEntityManager.getManager();
    PersistenceIncident persistenceIncident = new PersistenceIncident(manager);

    List<Incident> incidents = persistenceIncident.findAll();

    incidents.forEach(i -> {
      Client client = i.getClient();
      boolean isResolved = i.getResolved();

      if (!isResolved)
        i.subscribe(client);

    });
  }

}
