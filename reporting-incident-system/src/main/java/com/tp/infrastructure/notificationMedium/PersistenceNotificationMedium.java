package com.tp.infrastructure.notificationMedium;

import java.util.List;

import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.notificationMedium.NotificationMediumDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    if (name == null)
      return null;

    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<NotificationMedium> query = cb.createQuery(NotificationMedium.class);
    Root<NotificationMedium> root = query.from(NotificationMedium.class);

    query.select(root).where(cb.equal(root.get("medium"), name));

    TypedQuery<NotificationMedium> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<NotificationMedium> findAll() {
    return manager.createQuery("FROM NotificationMedium", NotificationMedium.class).getResultList();
  }

}
