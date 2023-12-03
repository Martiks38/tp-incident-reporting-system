package com.tp.domain.type_problem;

import java.util.List;

public interface TypeProblemDAO {
  TypeProblem findById(Long id);
  
  TypeProblem findByName(String name);

  List<TypeProblem> findAll();
}
