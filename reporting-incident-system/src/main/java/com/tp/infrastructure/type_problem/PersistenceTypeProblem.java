package com.tp.infrastructure.type_problem;

import java.util.List;

import com.tp.domain.type_problem.TypeProblem;
import com.tp.domain.type_problem.TypeProblemDAO;

import jakarta.persistence.EntityManager;

public class PersistenceTypeProblem implements TypeProblemDAO {

  private static EntityManager manager;

  public PersistenceTypeProblem(EntityManager mg) {
    manager = mg;
  }

  @Override
  public TypeProblem findById(Long id) {
    return manager.find(TypeProblem.class, id);
  }

  @Override
  public TypeProblem findByName(String name) {
    return manager.find(TypeProblem.class, name);
  }

  @Override
  public List<TypeProblem> findAll() {
    return manager.createQuery("FROM TypeProblem", TypeProblem.class).getResultList();
  }

}
