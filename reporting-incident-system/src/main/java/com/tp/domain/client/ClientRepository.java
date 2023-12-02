package com.tp.domain.client;

import java.util.List;

// import com.tp.infrastructure.domain.service.Service;
// import com.tp.infrastructure.domain.technical.Technical;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClientRepository implements ClientDAO{

  static EntityManager manager;

  public ClientRepository(EntityManager mg){
    manager = mg;
  }

  @Override
  public Client find(String id) {
    Client client = manager.find(Client.class, id);

    return client;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Client> findAll() {
    List<Client> clients = (List<Client>) manager.createQuery("FROM Client");

    return clients;
  }

  @Override
  public void save(Client data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();
      
      ClientCheckData.check(data);

      manager.persist(data);

      transaction.commit();
    } catch (Exception e) {
      if(transaction != null && transaction.isActive()){
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

  @Override
  public void update(String id, Client data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      Client client = this.find(id);

      if(client == null){
        throw new RuntimeException("No se ha el cliente solicitado.");
      }

      /**@TODO Terminar errores */
      // String c_cuit = client.getCuit();
      // String c_bussiness_name = client.getBusiness_name();
      // Service c_service = client.getService();

      // if(t_name != null){
      //   technical.setTechnical_name(t_name);
      // }

      // if(t_media == null){
      //   technical.setMeans_notification(t_media);
      // }

      // if(number_incidents_resolved < 0){
      //   technical.setNumber_incidents_resolved(number_incidents_resolved);
      // }

      // if(incident_resolution_speed < 0){
      //   technical.setIncident_resolution_speed(incident_resolution_speed);
      // }

      manager.persist(data);

      transaction.commit();
    } catch (Exception e) {
      if(transaction != null && transaction.isActive()){
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    }
  }

  @Override
  public void delete(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
  
}
