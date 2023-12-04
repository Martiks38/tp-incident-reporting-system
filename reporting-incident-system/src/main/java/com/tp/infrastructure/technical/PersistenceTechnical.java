package com.tp.infrastructure.technical;

import java.util.List;

import com.tp.domain.technical.Technical;
import com.tp.domain.technical.TechnicalCheckData;
import com.tp.domain.technical.TechnicalDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersistenceTechnical implements TechnicalDAO {

  private static EntityManager manager;

  public PersistenceTechnical(EntityManager mg) {
    manager = mg;
  }

  @Override
  public Technical findById(Long id) {
    return manager.find(Technical.class, id);
  }

  @Override
  public Technical findByName(String name) {
    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Technical> query = cb.createQuery(Technical.class);
    Root<Technical> root = query.from(Technical.class);

    query.select(root).where(cb.equal(root.get("technical_name"), name));

    TypedQuery<Technical> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Technical> findAll() {
    return manager.createQuery("FROM Technical", Technical.class).getResultList();
  }

  @Override
  public void save(Technical data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      TechnicalCheckData.check(data);

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
  public void update(Technical data) {
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

      Technical t = manager.find(Technical.class, id);

      manager.remove(t);

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
