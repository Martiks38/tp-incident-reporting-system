package com.tp.application;

import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.client.PersistenceClient;
import jakarta.persistence.EntityManager;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class incidentRegister {

    private EntityManager manager;
    private PersistenceClient persistenceClient;

    public incidentRegister(){
        this.manager = GetEntityManager.getManager();
        this.persistenceClient = new PersistenceClient(manager);
    }

    public void nuevo(Long idClient, String descripcion, String consideraciones, List<TypeProblem> tiposDeProblemas){
//        Client cliente = manager.find(Client.class,idClient);
        Client cliente = persistenceClient.findById(idClient);
        Incident incidente = new Incident();
        incidente.setClient(cliente);
        incidente.setDescription(descripcion);
        incidente.setConsiderations(consideraciones);
        incidente.setIncident_type_problem(tiposDeProblemas);
//        java.sql.Date now = (Date) Date.from(Instant.now());
//        incidente.setCreate_time(now);
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        incidente.setCreate_time(sqlDate);
        incidente.setState(true);
        incidente.setResolved(false);
        manager.persist(incidente);
    }




}
