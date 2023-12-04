package com.tp;

import com.tp.domain.rrhh.Rrhh;

public class Main {
    public static void main(String[] args) {
        final int days = 7;
        final String specialty_name = "training";

        // Rrhh.generateReport();
        // Rrhh.technicianWithFasterIncidentResolution();
        // Rrhh.technicianWithMostIncidentsForNDays(days);
        Rrhh.technicianWithMostIncidentsForNDaysBySpecialty(days, specialty_name);
    }
}