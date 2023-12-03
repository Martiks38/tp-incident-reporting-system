package com.tp.infrastructure.specialty;

import java.util.List;

import com.tp.domain.specialty.Specialty;
import com.tp.domain.specialty.SpecialtyDAO;

import jakarta.persistence.EntityManager;

public class PersistenceSpecialty implements SpecialtyDAO {

  private static EntityManager manager;

  public PersistenceSpecialty(EntityManager mg) {
    manager = mg;
  }

  @Override
  public Specialty findById(Long id) {
    return manager.find(Specialty.class, id);
  }

  @Override
  public Specialty findByName(String name) {
    return manager.find(Specialty.class, name);
  }

  @Override
  public List<Specialty> findAll() {
    return manager.createQuery("FROM Specialty", Specialty.class).getResultList();
  }

}
