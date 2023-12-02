package com.tp.domain.incident;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;

public class IncidentRepository implements IncidentDAO{
  static EntityManager manager;

  IncidentRepository(EntityManager mg){
    manager = mg;
  }

  @Override
  public Incident find(UUID id) {
    Incident incident = manager.find(Incident.class, id);

    return incident;
  }

  @Override
  public List<Incident> findAll() {
    // List<Incident> incidents = manager.getEntity(Incident.class);
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void save(UUID id, Incident data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void update(UUID id, Incident data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
