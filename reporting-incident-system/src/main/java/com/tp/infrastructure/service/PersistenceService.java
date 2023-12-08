package com.tp.infrastructure.service;

import java.util.ArrayList;
import java.util.List;

import com.tp.domain.service.Service;
import com.tp.domain.service.ServiceDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersistenceService implements ServiceDAO {

  private static EntityManager manager;

  public PersistenceService(EntityManager mg) {
    manager = mg;
  }

  @Override
  public Service findById(Long id) {
    Service service = new Service();

    try {
      service = manager.find(Service.class, id);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return service;
  }

  @Override
  public Service findByName(String name) {
    if (name == null)
      return null;

    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Service> query = cb.createQuery(Service.class);
    Root<Service> root = query.from(Service.class);

    query.select(root).where(cb.equal(root.get("service_name"), name));

    TypedQuery<Service> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Service> findAll() {
    List<Service> result = new ArrayList<>();

    try {
      result = manager.createQuery("FROM Service", Service.class).getResultList();

      if (result.isEmpty()) {
        throw new RuntimeException("No hay servicios");
      }
    } catch (Exception e) {
      System.err.println("Error metodo ServiceRepository.findAll" + e);
    }

    return result;
  }

}
