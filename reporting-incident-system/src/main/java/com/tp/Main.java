package com.tp;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp.application.incidentRegister;
import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.rrhh.Rrhh;
import com.tp.domain.service.Service;

import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.client.PersistenceClient;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.infrastructure.type_problem.PersistenceTypeProblem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Rrhh.generateReport();
        Rrhh.technicianWithFasterIncidentResolution();
        TestFunciones();
    }

    public static void TestFunciones() {
        final String persistenceUnitName = "test-bd";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);

        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();

        try {
//             tx.begin();
            System.out.println(Date.from(Instant.now()));

//            Service s = manager.createQuery("FROM Service", Service.class).getResultList().get(0);
//            List<Service> services = new ArrayList<Service>();

//            services.add(s);

//             Client c = new Client("23142356312", "Maraviglioso Games S.A.",
//             "contact@gmailmaravigliosogames.com", true, new ArrayList<>(), services);

             List<TypeProblem> listaProblemas = new ArrayList<>();
             List<TypeProblem> listaProblemas2 = new ArrayList<>();

            PersistenceTypeProblem TiposProblemas = new PersistenceTypeProblem(manager);
            listaProblemas = TiposProblemas.findAll();
//             TypeProblem problema1 = new TypeProblem();
//             TypeProblem problema2 = new TypeProblem();


            listaProblemas2.add(listaProblemas.get(1));
            listaProblemas2.add(listaProblemas.get(2));
//
//            incidentRegister incidentRegister = new incidentRegister();
//            incidentRegister.nuevo(1L,"Problema de conexion","No conecto el modem",listaProblemas2);
            LocalDate today = LocalDate.now();
            Date sqlDate = Date.valueOf(today);
            PersistenceTechnical tecnicoRepo = new PersistenceTechnical(manager);
            Technical tecnico = tecnicoRepo.findById(1L);
            PersistenceClient clienteRepo = new PersistenceClient(manager);
            Client cliente = clienteRepo.findById(1L);
//            PersistenceTypeProblem problemas = new PersistenceTypeProblem(manager);
//            List<TypeProblem> problemList
            Incident primerIncidente = new Incident(false,"example","niguna",sqlDate,null,true,tecnico,cliente,listaProblemas2);
            PersistenceIncident pi = new PersistenceIncident(manager);
            pi.save(primerIncidente);
            System.out.println(primerIncidente+"\n se ingreso correctamente");

            today.plusDays(1);
            Date sqlDate2 = Date.valueOf("2023-12-05");

            primerIncidente = pi.findById(4L);

            primerIncidente.setResolved(true);
            primerIncidente.setTime_is_up(sqlDate2);
            pi.update(primerIncidente);
            System.out.println(primerIncidente+"\n se actualizo correctamente");

            


//            System.out.println(primerIncidente.getIncident_id());
            // manager.persist(c);
//             tx.commit();

//            List<Client> clients = (List<Client>) manager.createQuery("FROM Client", Client.class).getResultList();
//
//            clients.stream().forEach(client -> System.out.println(client.getBusiness_name()));

//            Client cliente = manager.find(Client.class, 4);
//
//            tx.begin();
//
//            cliente.setClient_services(services);
//
//            manager.merge(cliente);
//
//            tx.commit();

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