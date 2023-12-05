package com.tp.application;

import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.client.PersistenceClient;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.infrastructure.type_problem.PersistenceTypeProblem;
import jakarta.persistence.EntityManager;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class incidentRegister {

    private EntityManager manager;

    private PersistenceIncident persistenceIncident;
    private PersistenceClient persistenceClient;
    private PersistenceTechnical persistenceTechnical;

    public incidentRegister(){
        this.manager = GetEntityManager.getManager();
        this.persistenceIncident = new PersistenceIncident(manager);
        this.persistenceClient = new PersistenceClient(manager);
        this.persistenceTechnical = new PersistenceTechnical(manager);
    }

    public Incident nuevo(Long idClient,Long idTecnico, String descripcion, String consideraciones, List<TypeProblem> tiposDeProblemas){
        Client cliente = persistenceClient.findById(idClient);
        Technical tecnico = persistenceTechnical.findById(idTecnico);
        Incident incidente = new Incident();
        incidente.setClient(cliente);
        incidente.setTechnical(tecnico);
        incidente.setDescription(descripcion);
        incidente.setConsiderations(consideraciones);
        List<TypeProblem> problems = manager.merge(tiposDeProblemas);
        incidente.setIncident_type_problem(problems);
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        incidente.setCreate_time(sqlDate);
        incidente.setState(true);
        incidente.setResolved(false);
        persistenceIncident.save(incidente);


        return incidente;
    }

    public void agregarIncidente(){
        List<TypeProblem> listaProblemas = new ArrayList<>();
        List<TypeProblem> listaProblemas2 = new ArrayList<>();

        PersistenceTypeProblem TiposProblemas = new PersistenceTypeProblem(manager);
        listaProblemas = TiposProblemas.findAll();
//             TypeProblem problema1 = new TypeProblem();
//             TypeProblem problema2 = new TypeProblem();


        listaProblemas2.add(listaProblemas.get(1));
        listaProblemas2.add(listaProblemas.get(2));

        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        PersistenceTechnical tecnicoRepo = new PersistenceTechnical(manager);
        Technical tecnico = tecnicoRepo.findById(1L);
        PersistenceClient clienteRepo = new PersistenceClient(manager);
        Client cliente = clienteRepo.findById(1L);

        PersistenceIncident pi = new PersistenceIncident(manager);
        Incident primerIncidente = new Incident(false,"Problema de conexion","No conecto el modem",sqlDate,null,true,tecnico,cliente,listaProblemas2);
        pi.save(primerIncidente);
        System.out.println(primerIncidente+"\n se ingreso correctamente");
    }



}
