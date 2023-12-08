package com.tp;

import com.tp.application.GetScanner;
import com.tp.domain.incident.InitSuscribeIncidents;
import com.tp.domain.operator.Operator;
import com.tp.domain.rrhh.Rrhh;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = GetScanner.getScanner();

        int opcion;

        final int days = 7;
        final String specialty_name1 = "training";
        final String specialty_name2 = "software";

        InitSuscribeIncidents.initExecution();

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
                    Rrhh.technicianWithMostIncidentsForNDaysBySpecialty(days, specialty_name1);

                    System.out.println();

                    Rrhh.technicianWithMostIncidentsForNDaysBySpecialty(days, specialty_name2);
                    break;
                case 5:
                    Operator.assistCustomer();
                    break;
                case 6:
                    System.out.println("Cerrando incident reporting system.");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

            System.out.println();
        } while (opcion != 6);

        GetScanner.closeScanner();
    }
}