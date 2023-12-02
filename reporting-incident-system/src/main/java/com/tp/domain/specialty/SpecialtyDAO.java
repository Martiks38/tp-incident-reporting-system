package com.tp.domain.specialty;

import java.util.List;


public interface SpecialtyDAO {
  Specialty find(Long id);

  List<Specialty> findAll();

  void save(Long id, Specialty data);

  void update(Long id, Specialty data);

  void delete(Long id);
}
