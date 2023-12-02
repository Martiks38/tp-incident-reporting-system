package com.tp.domain.client;

import java.util.List;

public interface ClientDAO {
  Client find(String id);

  List<Client> findAll();

  void save(Client data);

  void update(String id, Client data);

  void delete(String id);
}
