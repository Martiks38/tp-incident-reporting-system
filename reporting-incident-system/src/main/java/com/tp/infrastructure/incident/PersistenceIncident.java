package com.tp.infrastructure.incident;

import java.util.List;

import com.tp.domain.incident.Incident;
import com.tp.domain.incident.IncidentCheckData;
import com.tp.domain.incident.IncidentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PersistenceIncident implements IncidentDAO {

  private static EntityManager manager;

  public PersistenceIncident(EntityManager mg) {
    manager = mg;
  }

  @Override
  public Incident findById(Long id) {
    return manager.find(Incident.class, id);
  }

  @Override
  public List<Incident> findAll() {
    return manager.createQuery("FROM Incident", Incident.class).getResultList();
  }

  @Override
  public void save(Long id, Incident data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      IncidentCheckData.check(data);

      manager.persist(data);

      transaction.commit();

    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una
      // base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

  @Override
  public void update(Incident data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      manager.merge(data);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una
      // base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

  @Override
  public void delete(Long id) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      Incident incident = manager.find(Incident.class, id);

      manager.remove(incident);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una
      // base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

}
