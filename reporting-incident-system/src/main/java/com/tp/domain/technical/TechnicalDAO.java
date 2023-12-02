package com.tp.domain.technical;

import java.util.List;

public interface TechnicalDAO {
  Technical find(String id);

  List<Technical> findAll();

  void save(Technical data);

  void update(String id, Technical data);

  void delete(String id);
}
