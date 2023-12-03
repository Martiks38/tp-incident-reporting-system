package com.tp.domain.service;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceRepository implements ServiceDAO{

    static EntityManager manager;

    public ServiceRepository(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public Service find(Long id) {
        Service service = new Service();
        try {
            service = manager.find(Service.class, id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return service;
    }


    @Override
    public List<Service> findAll() {
        List<Service> result = new ArrayList<>();
        try {
            result = manager.createQuery("FROM Service").getResultList();
            if (result.isEmpty()){
                throw new RuntimeException("No hay servicios");
            }
        }catch (Exception e){
            System.err.println("Error metodo ServiceRepository.findAll" + e);
        }
        return result;
    }

    @Override
    public void save(Service data) {
        List<Service> services = findAll();
        try {
            if (!services.stream().anyMatch(service -> Objects.equals(service.getService_name(), data.getService_name()))){
                manager.persist(data);
            }else {
                throw new Exception("Ya existe el servicio:" + data);
            }
        }catch (Exception e){
            System.err.println("Error metodo ServicioRepository.save: "+ e);
        }
    }


    /*
    *   El metodo update solo actualiza el nombre del servicio.
    *
     */
    @Override
    public void update(Long id, Service data) {
        Service service = manager.find(Service.class, id);
        try {
            if (service != null){
                if (data.getService_name().equals(service.getService_name())){
                    service.setService_name(data.getService_name());
                    manager.merge(service);
                }else {
                    throw new Exception("El servicio: "+ service.getService_name() +" ya existe");
                }
            }else {
                throw new Exception("El servicio no existe");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    /*
     * Servicio no tiene state o borrado logico;
     *
     */
    @Override
    public void delete(Long id) {
        Service service = manager.find(Service.class,id);
        try {
            if (service != null){
                throw new RuntimeException("");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
