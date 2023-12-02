package com.tp.domain.service;

import java.util.List;

public interface ServiceDAO {
  Service find(Long id);

  List<Service> findAll();

  void save(Long id, Service data);

  void update(Long id, Service data);

  void delete(Long id);
}
