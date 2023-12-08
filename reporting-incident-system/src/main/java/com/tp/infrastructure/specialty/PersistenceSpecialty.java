package com.tp.infrastructure.specialty;

import java.util.List;

import com.tp.domain.specialty.Specialty;
import com.tp.domain.specialty.SpecialtyDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    if (name == null)
      return null;

    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Specialty> query = cb.createQuery(Specialty.class);
    Root<Specialty> root = query.from(Specialty.class);

    query.select(root).where(cb.equal(root.get("specialty_name"), name));

    TypedQuery<Specialty> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Specialty> findAll() {
    return manager.createQuery("FROM Specialty", Specialty.class).getResultList();
  }

}
