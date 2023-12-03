package com.tp.infrastructure.notificationMedium;

import java.util.List;

import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.notificationMedium.NotificationMediumDAO;

import jakarta.persistence.EntityManager;

public class PersistenceNotificationMedium implements NotificationMediumDAO {

  private static EntityManager manager;

  public PersistenceNotificationMedium(EntityManager mg) {
    manager = mg;
  }

  @Override
  public NotificationMedium findById(Long id) {
    return manager.find(NotificationMedium.class, id);
  }

  @Override
  public NotificationMedium findByName(String name) {
    return manager.find(NotificationMedium.class, name);
  }

  @Override
  public List<NotificationMedium> findAll() {
    return manager.createQuery("FROM NotificationMedium", NotificationMedium.class).getResultList();
  }

}
