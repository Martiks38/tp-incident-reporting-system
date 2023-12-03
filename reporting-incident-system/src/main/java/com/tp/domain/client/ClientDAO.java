package com.tp.domain.client;

import java.util.List;

public interface ClientDAO {
  Client find(Long id);

  List<Client> findAll();

  void save(Client data);

  void update(Long id, Client data);

  void delete(Long id);
}
