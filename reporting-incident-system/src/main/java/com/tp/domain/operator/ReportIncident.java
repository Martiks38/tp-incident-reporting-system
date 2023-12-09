package com.tp.domain.operator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.tp.application.GetEntityManager;
import com.tp.application.GetScanner;
import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.utils.GetDate;

import jakarta.persistence.EntityManager;

public class ReportIncident {

  public static void reportNewIncident(Incident incident) {
    EntityManager manager = GetEntityManager.getManager();
    PersistenceIncident persistenceIncident = new PersistenceIncident(manager);
    Scanner scanner = GetScanner.getScanner();

    List<TypeProblem> typesProblem = incident.getIncident_type_problem();

    int minAverageTimeByResolveInDays = typesProblem.stream()
        .min(Comparator.comparing(TypeProblem::getEstimated_resolution_time))
        .map(TypeProblem::getEstimated_resolution_time)
        .orElse(null);

    int maxResolutionTimeInDays = typesProblem.stream()
        .max(Comparator.comparing(TypeProblem::getMaximum_resolution_time))
        .map(TypeProblem::getMaximum_resolution_time)
        .orElse(null);

    LocalDate miniumResolutionDay = GetDate.calculateDateFromToday(minAverageTimeByResolveInDays);

    LocalDate maxResolutionDay = GetDate.calculateDateFromToday(maxResolutionTimeInDays);

    LocalDate estimatedDateForSolution = miniumResolutionDay;

    System.out
        .println("Se espera que el incidente se resuelva para el " +
            miniumResolutionDay + ". Y, en caso de complicación el problema se resolverá como máximo para el "
            + maxResolutionDay + ".\n");

    System.out.print("¿Desea modificar el mínimo de tiempo para realizar la tarea? (y/n) ");

    String optionModify = scanner.nextLine().toLowerCase();

    if (optionModify.equals("y")) {
      int additionalTime = maxResolutionTimeInDays - minAverageTimeByResolveInDays;

      Long selectedAdditionalTime = -1L;
      boolean incorrectSelectedAdditionalTime = true;

      do {
        System.out.print("\nPuede añadir hasta " + additionalTime
            + " días para informar al cliente que estará resuleto su problema.\n¿Cuántos desea añadir? ");

        selectedAdditionalTime = scanner.nextLong();

        incorrectSelectedAdditionalTime = !(selectedAdditionalTime >= 0 && selectedAdditionalTime <= additionalTime);

        if (incorrectSelectedAdditionalTime) {
          System.out.print("No es un tiempo válido.\n");
        } else {
          estimatedDateForSolution = miniumResolutionDay.plusDays(selectedAdditionalTime);
        }

      } while (incorrectSelectedAdditionalTime);
    }

    incident.setDeadline(Date.valueOf(estimatedDateForSolution));

    persistenceIncident.update(incident);

    Client client = incident.getClient();
    String clientMessage = "\nMensaje para el cliente.\nSu incidente ya ha sido ingresado. Se estima que su problema estará solucionado para el día "
        + estimatedDateForSolution + "\n\nMuchas gracias por elegirnos.";

    client.receiveIncidentNotification(clientMessage);
    incident.subscribe(client);

    Long id = incident.getIncident_id();
    String description = incident.getDescription();

    Technical technical = incident.getTechnical();
    String technicalMessage = "\nBuenos días.\nSe le ha asignado el incidente id: \"" + id
        + "\" con fecha de solución para el "
        + estimatedDateForSolution + ".\n\nDescripción\n" + description + "\n";

    technical.receiveIncidentNotification(technicalMessage);

  }

}
