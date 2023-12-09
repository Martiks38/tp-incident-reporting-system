package com.tp.application;

import com.tp.assets.Constant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class GetEntityManager {
  final String persistenceUnitName = Constant.PERSISTENCE_UNIT_NAME;

  private static GetEntityManager instance;
  private static EntityManager manager;

  private GetEntityManager() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);

    manager = factory.createEntityManager();
  }

  public static EntityManager getManager() {
    if (instance == null) {
      instance = new GetEntityManager();
    }

    return manager;
  }

  public static void closeManager() {
    manager.close();
  }
}
