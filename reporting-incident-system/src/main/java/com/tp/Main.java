package com.tp;

import java.util.ArrayList;
import java.util.List;

import com.tp.domain.client.Client;
import com.tp.domain.rrhh.Rrhh;
import com.tp.domain.service.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Rrhh.generateReport();
    }

    public static void TestFunciones() {
        final String persistenceUnitName = "test-bd";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);

        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();

        try {
            // tx.begin();

            Service s = manager.createQuery("FROM Service", Service.class).getResultList().get(0);
            List<Service> services = new ArrayList<Service>();

            services.add(s);

            // Client c = new Client("23142356312", "Maraviglioso Games S.A.",
            // "contact@gmailmaravigliosogames.com", true, new ArrayList<>(), services);

            // manager.persist(c);
            // tx.commit();

            List<Client> clients = (List<Client>) manager.createQuery("FROM Client", Client.class).getResultList();

            clients.stream().forEach(client -> System.out.println(client.getBusiness_name()));

            Client cliente = manager.find(Client.class, 4);

            tx.begin();

            cliente.setClient_services(services);

            manager.merge(cliente);

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una
            // base de datos que almacene los errores
            e.printStackTrace();
            System.err.println("Error en la transacción: " + e.getMessage());
        }
    }
}