package com.tp.domain.technical;

import java.util.List;

import com.tp.domain.specialty.Specialty;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TechnicalRepository implements TechnicalDAO{

  static EntityManager manager;

  public TechnicalRepository(EntityManager mg){
    manager = mg;
  }

  @Override
  public Technical find(String id) {
    return manager.find(Technical.class, id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Technical> findAll() {
    return (List<Technical>) manager.createQuery("FROM Technical").getResultList();
  }

  @Override
  public void save(Technical data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();
      
      TechnicalCheckData.check(data);

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
  public void update(String id, Technical data) {
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      Technical technical = manager.find(Technical.class, id);

      if(technical == null){
        throw new RuntimeException("No se ha encontrado el técnico para actualizar sus datos.");
      }

      String t_name = data.getTechnical_name();
      Integer t_number_incidents_resolved = data.getNumber_incidents_resolved();
      Long t_incident_resolution_speed = data.getIncident_resolution_speed();
      String t_mail = data.getMail();
      String t_phone_number = data.getPhone_number();
      // Long t_fk_notification_medium = data.getFk_notification_medium();
      List<Specialty> t_specialties = data.getSpecialties();

      if(t_name != null){
        technical.setTechnical_name(t_name);
      }

      if(t_number_incidents_resolved != null && t_number_incidents_resolved > 0){
        technical.setNumber_incidents_resolved(t_number_incidents_resolved);
      }

      if(t_incident_resolution_speed != null && t_incident_resolution_speed > 0){
        technical.setIncident_resolution_speed(t_incident_resolution_speed);
      }

      if(t_mail != null && TechnicalCheckData.isValidEmail(t_mail)){
        technical.setMail(t_mail);
      }

      if(t_phone_number != null){
        technical.setPhone_number(t_phone_number);
      }

      // if(t_fk_notification_medium != null){
      //   technical.setFk_notification_medium(t_fk_notification_medium);
      // }

      if(t_specialties != null && t_specialties.size() != 0){
        technical.setSpecialties(t_specialties);

        /**
         * @TODO
         * Añadir a la lista de especialidades de no existir alli
         *   */

        // if (!specialties.contains(specialty)) {
          // if (!specialty.getTechnicals().contains(this)) {
          //   specialty.getTechnicals().add(this);
        // }
      };

      manager.merge(technical);

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
    EntityTransaction transaction = manager.getTransaction();

    try {
      transaction.begin();

      Technical t = manager.find(Technical.class, id);
      
      manager.remove(t);

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
}
