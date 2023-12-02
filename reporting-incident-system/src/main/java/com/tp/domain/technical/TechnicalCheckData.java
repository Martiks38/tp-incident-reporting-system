package com.tp.domain.technical;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tp.domain.specialty.Specialty;

public class TechnicalCheckData {

  private static String message;
  private static final int id_length = UUID.randomUUID().toString().length();
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  public static void check(Technical t){
    // String id = t.getTechnical_id();
    
    String name = t.getTechnical_name();
    int number_incidents_resolved = t.getNumber_incidents_resolved();
    Long incident_resolution_speed = t.getIncident_resolution_speed();
    String mail = t.getMail();
    // Long fk_notification_medium = t.getMedium();
    List<Specialty> specialties = t.getSpecialties();

    // if(id.length() != id_length){
    //   message += "El id del técnico no es válido.\n";
    // }

    if(name == null || name.length() == 0){
      message += "El nombre del técnico no puede estar vacío.\n";
    }
    
    if(number_incidents_resolved < 0){
      message += "El número de incidentes resultos no puede ser menor a cero (0).\n";
    }

    if(incident_resolution_speed < 0){
      message += "La velocidad de resolución de incidentes no puede ser menor a cero (0).\n";
    }

    if(!isValidEmail(mail)){
      message += "El email ingresado no es válido.\n";
    }


    if(specialties.size() == 0){
      message += "El técnico debe tener al menos una especialidad.\n";
    }

    // if(fk_notification_medium < 1){
    //   message += "La clave del tipo de notificación debe ser mayor o igual 1.\n";
    // }

    if(message != null){
      throw new RuntimeException(message);
    }
  }

  public static boolean isValidEmail(String email) {
      Matcher matcher = EMAIL_PATTERN.matcher(email);
      return matcher.matches();
  }
}
