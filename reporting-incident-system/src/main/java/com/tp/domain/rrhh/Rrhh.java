package com.tp.domain.rrhh;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tp.application.GetEntityManager;
import com.tp.domain.incident.Incident;
import com.tp.domain.technical.Technical;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.utils.ModifyText;

import jakarta.persistence.EntityManager;

public class Rrhh {

      private static final int DESCRIPTION_LIMIT = 80;

    public static void technicianWithFasterIncidentResolution() {
        EntityManager manager = getEntityManager();
        PersistenceTechnical connectionTechnical = new PersistenceTechnical(manager);
        List<Technical> technicals = connectionTechnical.findAll();
        Optional<Technical> technicianWithTheShortestTime = findTechnicianWithShortestTime(technicals);
        if (technicianWithTheShortestTime.isPresent()) {
            Technical technical = technicianWithTheShortestTime.get();
            System.out.println("\nEl técnico que más rápido resolvió los incidentes es: "
                    + technical.getTechnical_name());
        } else {
            System.out.println("\nNo se encontró ningún técnico que haya resuelto incidentes.");
        }
    }

    private static Optional<Technical> findTechnicianWithShortestTime(List<Technical> technicals) {
        return technicals.stream()
                .filter(t -> t.getIncident_resolution_speed() != null)
                .min(Comparator.comparing(Technical::getIncident_resolution_speed));
    }

    public static void generateReport() {
        EntityManager manager = getEntityManager();
        PersistenceTechnical connectionTechnical = new PersistenceTechnical(manager);
        List<Technical> technicals = connectionTechnical.findAll();

        System.out.print("\n\n\n");

        if (technicals.isEmpty()) {
            System.out.print("No se encontraron técnicos.");
            return;
        }

        technicals.forEach(technical -> {
            printTechnicianReport(technical);
        });
    }

    private static void printTechnicianReport(Technical technical) {
        String technicalName = technical.getTechnical_name();
        List<Incident> incidents = technical.getIncidents();

        System.out.print("Técnico: " + technicalName + "\n");
        System.out.print("Incidentes\n");

        if (incidents.isEmpty()) {
            System.out.println("No tiene ningún incidente asignado.\n\n");
            return;
        }

        incidents.forEach(incident -> {
            printIncidentDetails(incident);
        });
    }

    private static void printIncidentDetails(Incident incident) {
        Long id = incident.getIncident_id();
        String description = incident.getDescription();
        boolean isResolved = incident.getResolved();
        String incidentState = isResolved ? "RESUELTO" : "PENDIENTE";
        System.out.print("Incidente: " + id + "\n");
        System.out.print("Descripción\n");
        ModifyText.LimitCharacterLine(description, DESCRIPTION_LIMIT);
        System.out.print("\n");
        System.out.print("Estado: " + incidentState + "\n\n");
    }

    private static EntityManager getEntityManager() {
        return GetEntityManager.getManager();
    }
}
