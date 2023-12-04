package com.tp.infrastructure.type_problem;

import java.util.List;

import com.tp.domain.type_problem.TypeProblem;
import com.tp.domain.type_problem.TypeProblemDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<TypeProblem> query = cb.createQuery(TypeProblem.class);
    Root<TypeProblem> root = query.from(TypeProblem.class);

    query.select(root).where(cb.equal(root.get("type_problem_name"), name));

    TypedQuery<TypeProblem> typedQuery = manager.createQuery(query);

    try {
      return typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<TypeProblem> findAll() {
    return manager.createQuery("FROM TypeProblem", TypeProblem.class).getResultList();
  }

}
