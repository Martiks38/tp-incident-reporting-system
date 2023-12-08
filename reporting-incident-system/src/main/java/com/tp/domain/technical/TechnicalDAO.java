package com.tp.domain.technical;

import java.util.List;

public interface TechnicalDAO {
  Technical findById(Long id);

  Technical findByName(String name);

  List<Technical> findAll();

  void save(Technical data);

  void update(Technical data);

  void delete(Technical technical);
}
