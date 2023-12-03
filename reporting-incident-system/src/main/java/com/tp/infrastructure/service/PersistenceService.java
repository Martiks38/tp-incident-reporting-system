package com.tp.infrastructure.service;

import java.util.List;

import com.tp.domain.service.Service;
import com.tp.domain.service.ServiceDAO;

import jakarta.persistence.EntityManager;

public class PersistenceService implements ServiceDAO {

  private static EntityManager manager;

  public PersistenceService(EntityManager mg){
    manager = mg;
  }

  @Override
  public Service findById(Long id) {
    return manager.find(Service.class, id);
  }

  @Override
  public Service findByName(String name) {
    return manager.find(Service.class, name);
  }

  @Override
  public List<Service> findAll() {
    return manager.createQuery("FROM Service", Service.class).getResultList();
  }
  
}
