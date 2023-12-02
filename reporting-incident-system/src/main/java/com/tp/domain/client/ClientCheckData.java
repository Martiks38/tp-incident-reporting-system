package com.tp.domain.client;

import java.util.UUID;

public class ClientCheckData {

  private static String message;

  public static void check(Client c){
    Long id = c.getClient_id();

    String cuit = c.getCuit();
    String bussiness_name = c.getBusiness_name();

    // if(id_length != UUID.randomUUID().toString().length()){
    //   message += "El id del cliente no es válido.\n";
    // }

    if(cuit == null || cuit.length() != 11){
      message += "El cuit debe tener 11 dígitos.\n";
    }

    if(bussiness_name == null || bussiness_name.length() == 0){
      message += "El nombre del negocio no puede estar vacío.\n";
    }

    if(message != null){
      throw new RuntimeException(message);
    }
  }
}
