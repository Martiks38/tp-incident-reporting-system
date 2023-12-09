package com.tp;

import com.tp.application.ExampleResolveIncident;
import com.tp.application.GetEntityManager;
import com.tp.application.GetScanner;
import com.tp.domain.incident.InitSuscribeIncidents;
import com.tp.domain.operator.Operator;
import com.tp.domain.rrhh.Rrhh;

import java.util.Scanner;

/**
 * TODO
 * 
 * Añadir control try_catch
 * a los scanner empleados
 * con esta tarea como la última se finaliza el proyecto
 * 
 */
public class Main {
    public static void main(String[] args) {

        Scanner scanner = GetScanner.getScanner();

        int opcion;

        InitSuscribeIncidents.initExecution();

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Emitir reporte con los incidentes asignados a cada técnico.");
            System.out.println("2. Emitir reporte del técnico con mayor velocidad al resolve un incidente.");
            System.out.println("3. Emitir reporte del técnico con más incidentes resueltos hace N días.");
            System.out.println(
                    "4. Emitir reporte del técnico con más incidentes resueltos hace N días según una especialidad.");
            System.out.println("5. Crear incidente.");
            System.out.println("6. Finalizar incidente.");
            System.out.println("7. Salir");
            System.out.print("Elige una opción (1-7): ");

            opcion = scanner.nextInt();

            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Rrhh.generateReport();
                    break;
                case 2:
                    Rrhh.technicianWithFasterIncidentResolution();
                    break;
                case 3:
                    Rrhh.technicianWithMostIncidentsForNDays();
                    break;
                case 4:
                    Rrhh.technicianWithMostIncidentsForNDaysBySpecialty();
                    break;
                case 5:
                    Operator.assistCustomer();
                    break;
                case 6:
                    ExampleResolveIncident.ResolveIncident();
                    break;
                case 7:
                    System.out.println("Cerrando incident reporting system.");
                    break;

                default:
                    System.out.println("Opción no válida. Ingrese su opción nuevamente.");
            }

            System.out.println();
        } while (opcion != 7);

        GetScanner.closeScanner();
        GetEntityManager.closeManager();
    }
}