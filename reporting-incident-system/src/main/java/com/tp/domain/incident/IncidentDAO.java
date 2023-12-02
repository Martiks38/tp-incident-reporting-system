package com.tp.domain.incident;

import java.util.List;
import java.util.UUID;

public interface IncidentDAO {
  Incident find(UUID id);

  List<Incident> findAll();

  void save(UUID id, Incident data);

  void update(UUID id, Incident data);

  void delete(UUID id);
}
