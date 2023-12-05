package com.tp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tp.application.Notification;
import com.tp.application.notificationEmail;
import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.rrhh.Rrhh;

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

        Scanner scanner = new Scanner(System.in);

        int opcion;

        final int days = 7;
        final String specialty_name1 = "training";
        final String specialty_name2 = "software";

        do {
            System.out.println("Menú:");
            System.out.println("1. Emitir reporte con los incidentes asignados a cada técnico.");
            System.out.println("2. Emitir reporte del técnico con mayor velocidad al resolve un incidente.");
            System.out.println("3. Emitir reporte del técnico con más incidentes resueltos hace N días.");
            System.out.println(
                    "4. Emitir reporte del técnico con más incidentes resueltos hace N días según una especialidad.");
            System.out.println("5. Crear incidente.");
            System.out.println("6. Salir");

            System.out.print("Elige una opción (1-6): ");
            opcion = scanner.nextInt();

            System.out.println();
            switch (opcion) {
                case 1:
                    Rrhh.generateReport();
                    break;
                case 2:
                    Rrhh.technicianWithFasterIncidentResolution();
                    break;
                case 3:
                    Rrhh.technicianWithMostIncidentsForNDays(days);
                    break;
                case 4:
                    Rrhh.technicianWithMostIncidentsForNDaysBySpecialty(days, specialty_name1);

                    System.out.println();

                    Rrhh.technicianWithMostIncidentsForNDaysBySpecialty(days, specialty_name2);
                    break;
                case 5:
                    CreateIncident();
                    break;
                case 6:
                    System.out.println("Cerrando incident reporting system.");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

            System.out.println();
        } while (opcion != 6);

        scanner.close();

    }

    public static void CreateIncident() {
        final String persistenceUnitName = "test-bd";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);

        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();

        try {
            List<TypeProblem> listaProblemas = new ArrayList<>();
            List<TypeProblem> listaProblemas2 = new ArrayList<>();

            PersistenceTypeProblem TiposProblemas = new PersistenceTypeProblem(manager);
            listaProblemas = TiposProblemas.findAll();

            listaProblemas2.add(listaProblemas.get(1));
            listaProblemas2.add(listaProblemas.get(2));

            LocalDate today = LocalDate.now();
            Date sqlDate = Date.valueOf(today);
            PersistenceTechnical tecnicoRepo = new PersistenceTechnical(manager);
            Technical tecnico = tecnicoRepo.findById(1L);
            PersistenceClient clienteRepo = new PersistenceClient(manager);
            Client cliente = clienteRepo.findById(1L);

            PersistenceIncident pi = new PersistenceIncident(manager);
            Incident primerIncidente = new Incident(false, "Problema de conexion", "No conecto el modem", sqlDate, null,
                    true, tecnico, cliente, listaProblemas2);
            pi.save(primerIncidente);
            System.out.println(primerIncidente + "\n se ingreso correctamente");

            NotificationMedium nm = tecnico.getMedium();
            Notification notification = new Notification();

            if (nm.getMedium().equals("Email")) {
                notification.setStrategy(new notificationEmail());
            }

            if (nm.getMedium().equals("WhatsApp")) {
                notification.setStrategy(new notificationEmail());
            }

            notification.executeStrategy("Se inicio un nuevo incidente" + primerIncidente);
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