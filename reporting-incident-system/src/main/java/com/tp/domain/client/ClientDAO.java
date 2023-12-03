package com.tp.domain.client;

import java.util.List;

public interface ClientDAO {
  Client findById(Long id);

  Client findByName(String name);

  List<Client> findAll();

  void save(Client data);

  void update(Client data);

  void delete(Long id);
}
