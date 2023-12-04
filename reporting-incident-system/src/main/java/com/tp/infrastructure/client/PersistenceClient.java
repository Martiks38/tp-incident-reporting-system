package com.tp.infrastructure.client;

import java.util.List;

import com.tp.domain.client.Client;
import com.tp.domain.client.ClientCheckData;
import com.tp.domain.client.ClientDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersistenceClient implements ClientDAO {

  private static EntityManager manager;

  public PersistenceClient(EntityManager mg) {
    manager = mg;
  }

  @Override
  public Client findById(Long id) {
    return manager.find(Client.class, id);
  }

  @Override
  public Client findByName(String name) {
    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Client> query = cb.createQuery(Client.class);
    Root<Client> root = query.from(Client.class);

    query.select(root).where(cb.equal(root.get("business_name"), name));

    TypedQuery<Client> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Client> findAll() {
    return manager.createQuery("FROM Client", Client.class).getResultList();
  }

  @Override
  public void save(Client data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      ClientCheckData.check(data);

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
  public void update(Client data) {
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

      Client c = manager.find(Client.class, id);

      manager.remove(c);

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
