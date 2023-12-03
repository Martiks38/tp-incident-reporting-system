package com.tp.domain.specialty;

import java.util.List;


public interface SpecialtyDAO {
  Specialty findById(Long id);
  
  Specialty findByName(String name);

  List<Specialty> findAll();
}
