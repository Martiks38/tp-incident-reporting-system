package com.tp.domain.commercial_area;

import com.tp.application.GetEntityManager;
import com.tp.assets.ActionClient;
import com.tp.domain.client.Client;
import com.tp.infrastructure.client.PersistenceClient;

import jakarta.persistence.EntityManager;

public class ComercialArea {

  public static void handlerClient(Client client, ActionClient action) {
    EntityManager manager = GetEntityManager.getManager();

    PersistenceClient conectionClient = new PersistenceClient(manager);

    if (action == ActionClient.CREATE_CLIENT) {
      conectionClient.save(client);
    }

    if (action == ActionClient.REMOVE_CLIENT) {
      conectionClient.delete(client);
    }

    if (action == ActionClient.UPDATE_CLIENT) {
      conectionClient.update(client);
    }
  }

}
