package com.tp.domain.notificationMedium;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class NotificationMediumRepository implements NotificationMediumDAO{

  private static EntityManager manager;

  public NotificationMediumRepository(EntityManager mg){
    manager = mg;
  }

  @Override
  public NotificationMedium find(int id) {
    return manager.find(NotificationMedium.class, id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<NotificationMedium> findAll() {
    return (List<NotificationMedium>) manager.createQuery("FROM NotificationMedium").getResultList();
    }

  @Override
  @SuppressWarnings("unchecked")
  public void save(NotificationMedium data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      List<NotificationMedium> list_notification_medium = (List<NotificationMedium>) manager.createQuery("FROM NotificationMedium").getResultList();

      boolean existsNotificationMedium = list_notification_medium.contains(data);

      if(existsNotificationMedium){
        throw new RuntimeException("El medio de notificación ya existe.");
      }
      
      manager.persist(data);

      transaction.commit();
    } catch (Exception e) {
      if(transaction != null && transaction.isActive()){
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

  @Override
  public void update(int id, NotificationMedium data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      NotificationMedium notification_medium = manager.find(NotificationMedium.class, id);

      if(notification_medium == null){
        throw new RuntimeException("El medio de notificación solicitado no se pudo encontrar.");
      }

      String nm_medium = data.getMedium();

      if(nm_medium != null){
        notification_medium.setMedium(nm_medium);
      }

      manager.merge(notification_medium);

      transaction.commit();
    } catch (Exception e) {
      if(transaction != null && transaction.isActive()){
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
   }
  }

  @Override
  public void delete(int id) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      NotificationMedium nm = manager.find(NotificationMedium.class, id);

      manager.remove(nm);

      transaction.commit();
    } catch (Exception e) {
      if(transaction != null && transaction.isActive()){
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }
  
}
