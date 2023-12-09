package com.tp.domain.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.application.GetEntityManager;
import com.tp.domain.incident.Incident;
import com.tp.domain.specialty.Specialty;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.utils.Inputs;

import jakarta.persistence.EntityManager;

public class GenerateIncident {
  private static EntityManager manager = GetEntityManager.getManager();

  public static void issueIncident(Incident incident) {

    PersistenceIncident persistenceIncident = new PersistenceIncident(manager);
    PersistenceTechnical persistenceTechnical = new PersistenceTechnical(manager);

    List<Technical> technicals = persistenceTechnical.findAll();
    List<Technical> possibleTechnicians = new ArrayList<>();
    List<TypeProblem> typeProblems = incident.getIncident_type_problem();

    possibleTechnicians = technicals
        .stream()
        .filter(t -> {
          List<Specialty> specialties = t.getSpecialties();

          List<TypeProblem> canSolve = specialties
              .stream()
              .map(Specialty::getTypesProblem)
              .flatMap(List::stream)
              .distinct()
              .collect(Collectors.toList());

          return canSolve.containsAll(typeProblems);
        }).collect(Collectors.toList());

    System.out.println("\nMensaje al operador.");
    System.out.println("Los posibles técnicos que pueden resolver el problema son: \n\n");

    for (int ind = 0; ind < possibleTechnicians.size(); ind++) {
      System.out.println(" " + (ind + 1) + "-\t" + possibleTechnicians.get(ind).getTechnical_name());
    }

    boolean isInvalidOption = true;
    int amountPossibleTechnician = possibleTechnicians.size();
    int option = -1;

    do {
      option = Inputs.getIntInput("\nElija uno de ellos (1-" + amountPossibleTechnician + "): ",
          "Debe ingresar un valor del 1 al " + amountPossibleTechnician);

      isInvalidOption = !(option > 0 && option <= amountPossibleTechnician);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {
        Technical selectedTechnical = possibleTechnicians.get(option - 1);

        incident.setTechnical(selectedTechnical);

        incident.subscribe(incident.getClient());

        persistenceIncident.save(incident);
      }

    } while (isInvalidOption);

  }

}
