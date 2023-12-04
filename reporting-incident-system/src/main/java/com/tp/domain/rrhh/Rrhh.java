package com.tp.domain.rrhh;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tp.application.GetEntityManager;
import com.tp.domain.incident.Incident;
import com.tp.domain.technical.Technical;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.utils.ModifyText;

import jakarta.persistence.EntityManager;

public class Rrhh {

  public static void technicianWithFasterIncidentResolution() {
    EntityManager manager = GetEntityManager.getManager();

    PersistenceTechnical conectionTechnical = new PersistenceTechnical(manager);

    List<Technical> technicals = conectionTechnical.findAll();

    Optional<Technical> technicianWithTheShortestTime = technicals.stream()
        .filter(t -> t.getIncident_resolution_speed() != null)
        .collect(Collectors.minBy(Comparator.comparing(Technical::getIncident_resolution_speed)));

    if (technicianWithTheShortestTime.isPresent()) {
      Technical technical = technicianWithTheShortestTime.get();

      System.out.println("\nEl técnico que más rápido resolvió los incidentes es: " +
          technical.getTechnical_name());
    } else {
      System.out.println("\nNo se encontro ningún técnico que haya resuelto incidentes.");
    }

  }

  public static void generateReport() {
    EntityManager manager = GetEntityManager.getManager();

    PersistenceTechnical conectionTechnical = new PersistenceTechnical(manager);

    List<Technical> technicals = conectionTechnical.findAll();

    System.out.print("\n\n\n");

    if (technicals.size() == 0) {
      System.out.print("No se encontraron técnicos.");
      return;
    }

    technicals.forEach(technical -> {
      String technical_name = technical.getTechnical_name();
      List<Incident> incidents = technical.getIncidents();

      System.out.print("Técnico: " + technical_name + "\n");
      System.out.print("Incidentes\n");

      if (incidents.size() == 0) {
        System.out.println("No tiene ningún incidente asignado.\n\n");
        return;
      }

      incidents.forEach(incident -> {
        Long id = incident.getIncident_id();
        String description = incident.getDescription();
        boolean isResolved = incident.getResolved();
        String incidentState = isResolved ? "RESUELTO" : "PENDIENTE";

        System.out.print("Incidente: " + id + "\n");
        System.out.print("Descripción\n");
        ModifyText.LimitCharacterLine(description, 80);
        System.out.print("\n");
        System.out.print("Estado: " + incidentState + "\n\n");
      });
    });
  }

  public static void technicianWithMostIncidentsForNDays(int days) {
    EntityManager manager = GetEntityManager.getManager();

    List<Incident> incidents = new PersistenceIncident(manager).findAll();

    if (incidents.size() == 0) {
      System.out.print("No se encontraron incidentes.");
      return;
    }

    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = currentDate.minusDays(days);

    Map<Technical, List<Incident>> incidentsResolvedInLastNDays = incidents.stream()
        .filter(i -> i.getTime_is_up() != null)
        .filter(i -> {
          LocalDate solutionDate = i.getTime_is_up().toLocalDate();

          return solutionDate.isAfter(startDate);
        })
        .collect(Collectors.groupingBy(Incident::getTechnical));

    Optional<Entry<Technical, List<Incident>>> technicalWithMostResolvedIncidentsInLastNDays = incidentsResolvedInLastNDays
        .entrySet()
        .stream().max(Comparator.comparingInt(entry -> entry.getValue().size()));

    if (technicalWithMostResolvedIncidentsInLastNDays.isPresent()) {
      Technical technical = technicalWithMostResolvedIncidentsInLastNDays.get().getKey();
      String name = technical.getTechnical_name();

      System.out.print("\nEl técnico que más incidentes resolvió en los últimos " + days + " días es: " + name + ".\n");
    } else {
      System.out
          .print("\nNo se encontraron técnicos que hayan resuelto incidentes durante los últimos " + days + " días.\n");
    }
  }

}
