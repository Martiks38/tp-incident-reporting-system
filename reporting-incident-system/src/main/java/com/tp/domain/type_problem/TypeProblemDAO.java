package com.tp.domain.type_problem;

import java.util.List;

public interface TypeProblemDAO {
  TypeProblem find(Long id);

  List<TypeProblem> findAll();

  void save(Long id, TypeProblem data);

  void update(Long id, TypeProblem data);

  void delete(Long id);
}
