package com.tp.domain.client;

import com.tp.domain.client.Client;
import com.tp.domain.client.ClientDAO;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientRepository implements ClientDAO {

  static EntityManager manager;

  public ClientRepository(EntityManager mg){
    manager = mg;
  }

  @Override
  public Client find(Long id) {

    Client client = new Client();
    try {
      client = manager.find(Client.class, id);
    }catch (Exception e){
      System.err.println("Error metodo ClientRepository.find");
    }

    return client;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Client> findAll() {
    List<Client> result = new ArrayList<>();
    try {
      result = manager.createQuery("FROM Client").getResultList();
      if (result.isEmpty()){
        throw new RuntimeException("No hay clientes");
      }
    }catch (Exception e){
      System.err.println("Error metodo ClientRepository.findAll" + e);
    }
    return result;
  }

  @Override
  public void save(Client data) {
    List<Client> clientes = findAll();
    try {
      if (!clientes.stream().anyMatch(client -> Objects.equals(client.getBusiness_name(), data.getBusiness_name()))
        && !clientes.stream().anyMatch(client -> Objects.equals(client.getCuit(),data.getCuit()))
      ){
        manager.persist(data);
      }else {
        throw new Exception("Ya existe el cliente:" + data);
      }
    }catch (Exception e){
      System.err.println("Error metodo ClienteRepository.update: "+ e);
    }
  }

  @Override
  public void update(Long id, Client data) {
    Client client = manager.find(Client.class,id);
    try {
      if (client != null){
        if (!client.equals(data)){
          client.setBusiness_name(data.getBusiness_name());
          client.setMail(data.getMail());
          client.setCuit(data.getCuit());
          client.setClient_services(data.getClient_services());
          manager.merge(client);
        }else {
          throw new Exception("Modifique los campos del cliente al actualizar");
        }

      }else {
        throw new Exception("El cliente que desea actualizar no existe");
      }
    }catch (Exception e){
      System.err.println("Error metodo ClienteRepository.update: "+ e);
    }
  }


  @Override
  public void delete(Long id) {

    Client client = manager.find(Client.class,id);
    try {
      if (client != null){
        client.setState(false);
        manager.merge(client);
      }else {
        throw new Exception("El cliente que desea borrar no existe");
      }
    }catch (Exception e){
      System.err.println("Error metodo ClienteRepository.delete "+ e);
    }
  }

}
