package com.tp.domain.service;

import java.util.List;

public interface ServiceDAO {
  Service findById(Long id);

  Service findByName(String name);

  List<Service> findAll();
}
