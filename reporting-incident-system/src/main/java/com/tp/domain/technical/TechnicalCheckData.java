package com.tp.domain.technical;

import java.util.List;

import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.specialty.Specialty;
import com.tp.utils.CheckFormatEmail;
import com.tp.utils.CheckFormatName;

public class TechnicalCheckData {

  private static String message;

  public static void check(Technical t) {
    String name = t.getTechnical_name();
    int number_incidents_resolved = t.getNumber_incidents_resolved();
    Long incident_resolution_speed = t.getIncident_resolution_speed();
    String mail = t.getMail();
    String phone_number = t.getPhone_number();
    List<Specialty> specialties = t.getSpecialties();
    NotificationMedium medium = t.getMedium();

    if (!CheckFormatName.isValidName(name)) {
      message += "El nombre del técnico no puede estar vacío.\n";
    }

    if (number_incidents_resolved < 0) {
      message += "El número de incidentes resultos no puede ser menor a cero (0).\n";
    }

    if (incident_resolution_speed < 0) {
      message += "La velocidad de resolución de incidentes no puede ser menor a cero (0).\n";
    }

    if (!CheckFormatEmail.isValidEmail(mail)) {
      message += "El email ingresado no es válido.\n";
    }

    if (phone_number == null || phone_number.length() > 15) {
      message += "El número de teléfono no es válido y puede tener un máximo de 15 dígitos.\n";
    }

    if (medium == null) {
      message += "El técnico debe tener asignado un medio de comunicación.\n";
    }

    if (specialties.size() == 0) {
      message += "El técnico debe tener al menos una especialidad.\n";
    }

    if (message != null) {
      throw new RuntimeException(message);
    }
  }

}
