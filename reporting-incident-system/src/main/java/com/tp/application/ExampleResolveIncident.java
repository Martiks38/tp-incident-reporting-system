package com.tp.application;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.tp.domain.incident.Incident;
import com.tp.domain.technical.Technical;
import com.tp.infrastructure.incident.PersistenceIncident;

import jakarta.persistence.EntityManager;

public class ExampleResolveIncident {

  final static EntityManager manager = GetEntityManager.getManager();

  public static void ResolveIncident() {
    Random random = new Random();
    Scanner scanner = GetScanner.getScanner();

    List<Incident> incidents = new PersistenceIncident(manager).findAll();
    List<Incident> unresolvedIncidents = incidents.stream()
        .filter(i -> !i.getResolved())
        .collect(Collectors.toList());

    Incident incidentToResolve = unresolvedIncidents.get(unresolvedIncidents.size() * random.nextInt());
    Technical technical = incidentToResolve.getTechnical();

    String considerations;
    boolean invalidConsiderations = true;

    do {
      System.out.println("Ingrese las consideraciones del incidente, máximo 255 caracteres.");

      considerations = scanner.nextLine();
      invalidConsiderations = considerations.length() <= 0 || considerations.length() > 255;

      if (invalidConsiderations) {
        System.out.println("\nLas consideraciones ingresadas no son válidas.\n");
      }
    } while (invalidConsiderations);

    technical.resolveIncident(incidentToResolve, considerations);
  }

}
