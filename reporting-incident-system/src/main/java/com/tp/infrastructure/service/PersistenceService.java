package com.tp.infrastructure.service;

import java.util.ArrayList;
import java.util.List;

import com.tp.domain.service.Service;
import com.tp.domain.service.ServiceDAO;

import jakarta.persistence.EntityManager;

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
    Service service = new Service();

    try {
      service = manager.find(Service.class, name);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return service;
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
