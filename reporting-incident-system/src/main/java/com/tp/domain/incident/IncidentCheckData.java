package com.tp.domain.incident;

import java.sql.Date;
import java.util.List;

import com.tp.domain.client.Client;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;

public class IncidentCheckData {

  private static String message = null;

  public static void check(Incident i) {
    Boolean resolved = i.getResolved();
    String description = i.getDescription();
    Date create_time = i.getCreate_time();
    Boolean state = i.getState();
    Technical technical = i.getTechnical();
    Client client = i.getClient();
    List<TypeProblem> list_type_problem = i.getIncident_type_problem();

    if (resolved == null) {
      i.setResolved(false);
    }

    if (description == null) {
      message += "El incidente debe tener una descripción del problema.\n";
    }

    if (create_time == null) {
      message += "Se debe añadir la fecha de creación del incidente.\n";
    }

    if (state == null) {
      i.setState(true);
    }

    if (technical == null) {
      message += "El incidente debe tener un técnico asignado,\n";
    }

    if (client == null) {
      message += "Debe incluir el cliente que reportó el problema.\n";
    }

    if (list_type_problem == null || list_type_problem.size() == 0) {
      message += "Debe incluir una lista de los tipos de problemas relacionados al incidente.\n";
    }

    if (message != null) {
      throw new RuntimeException(message);
    }
  }

}
