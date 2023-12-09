package com.tp.domain.operator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.tp.application.GetEntityManager;
import com.tp.application.GetScanner;
import com.tp.assets.ActionClient;
import com.tp.assets.Constant;
import com.tp.domain.client.Client;
import com.tp.domain.commercial_area.ComercialArea;
import com.tp.domain.incident.Incident;
import com.tp.domain.service.Service;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.client.PersistenceClient;
import com.tp.infrastructure.service.PersistenceService;
import com.tp.infrastructure.type_problem.PersistenceTypeProblem;
import com.tp.utils.CheckFormat;
import com.tp.utils.Inputs;

import jakarta.persistence.EntityManager;

public class Operator {
  static final Scanner scanner = GetScanner.getScanner();
  static final EntityManager manager = GetEntityManager.getManager();
  static final ZoneId GMTTimeZone = Constant.GMT_TIME_ZONE;

  public static void assistCustomer() {
    PersistenceClient persistenceClient = new PersistenceClient(manager);

    Client customer = new Client();

    customer.setState(true);

    System.out.print("\nBuenos días.\n\n");
    System.out.print("Por favor ingrese su razón social: ");
    customer.setBusiness_name(scanner.nextLine());

    System.out.print("\nPor favor ingrese su cuit: ");
    customer.setCuit(scanner.nextLine());

    findClientData(customer, persistenceClient);

    List<Service> client_services = customer.getClientServices();

    Incident newIncident = new Incident();
    Date currentDay = Date.valueOf(LocalDate.now());

    newIncident.setClient(customer);
    newIncident.setResolved(false);
    newIncident.setConsiderations("");
    newIncident.setCreate_time(currentDay);
    newIncident.setState(true);

    if (client_services.size() == 0) {
      System.out.println("\nMensaje para el Operador");
      System.out.println("No ha contratado ningún servicio.\n");

      System.out.println("Mensaje para el cliente");
      System.out.println("¿Cuál de nuestros servicios desea contratar?\n\n");

      handlerServiceClient(customer, newIncident);

    } else {

      System.out.println("Mensaje para el operador.");
      System.out.println("El cliente tiene contratado los siguientes servicios:\n");

      List<Service> clientServices = customer.getClientServices();
      int amountClientServices = clientServices.size();

      clientServices.forEach(service -> {
        System.out.println(service.getService_name());
      });

      boolean isInvalidOption = true;
      int optionService = -1;

      System.out.println("\nMensaje para el cliente.");

      do {
        System.out.println("Tiene los siguientes servicios contratados.");

        for (int ind = 0; ind < clientServices.size(); ind++) {
          System.out.print(" " + (ind + 1) + "-\t" + clientServices.get(ind).getService_name() + "\n");
        }

        optionService = Inputs.getIntInput(
            "\nPor favor elija con cuál servicio desea reportar el incidente. Introduzca su opción del 1 al "
                + amountClientServices + ". ",
            "Debe elegir un valor entre 1 y " + amountClientServices + ".");

        isInvalidOption = !(optionService > 0 && optionService <= amountClientServices);

        if (isInvalidOption) {
          System.out.print("No es una opción válida.\n");
        } else {
          Service selectedService = clientServices.get(optionService - 1);

          newIncident.setService(selectedService);
        }
      } while (isInvalidOption);

    }

    handlerTypesProblem(newIncident);

    handlerDescription(newIncident);

    final Service incidentService = newIncident.getService();

    List<TypeProblem> containedTypesProblems = newIncident.getIncident_type_problem()
        .stream()
        .filter(tp -> {
          return !incidentService.getTypesProblem().contains(tp);
        })
        .collect(Collectors.toList());

    while (containedTypesProblems.size() != 0) {
      System.out.println(
          "\nLos tipos de problemas asociados al incidente reportado no coinciden con el servicio adjuntado.\n");

      handlerTypesProblem(newIncident);
    }

    GenerateIncident.issueIncident(newIncident);

    ReportIncident.reportNewIncident(newIncident);

  }

  private static void findClientData(Client client, PersistenceClient persistenceClient) {
    Client customerData = persistenceClient.findByName(client.getBusiness_name());

    if (customerData != null) {
      client.copyProperties(customerData);
    }

    if (customerData == null || !customerData.getState()) {
      boolean isInvalidEmail = true;
      String email;

      System.out
          .println(
              "\nEs un cliente nuevo le vamos a pedir que ingrese los datos que le solicitaremos a continuación.\n");

      System.out.print("Ingrese una cuenta de email. ");

      do {
        email = scanner.nextLine();

        isInvalidEmail = !CheckFormat.isValidEmail(email);

        if (isInvalidEmail) {
          System.out.print("\nEl correo no es válido por favor ingreselo nuevamente. ");
        }
      } while (isInvalidEmail);

      System.out.println("\nMuchas gracias.\n");

      client.setMail(email);
      client.setClientServices(new ArrayList<>());
      client.setIncidents(new ArrayList<>());

      ComercialArea.handlerClient(client, ActionClient.CREATE_CLIENT);
    }
  }

  private static void handlerServiceClient(Client customer, Incident incident) {
    List<Service> services = new PersistenceService(manager).findAll();
    List<Service> client_services = customer.getClientServices();

    services.forEach(s -> {
      String name = s.getService_name();
      StringBuilder capitalizeName = new StringBuilder();

      capitalizeName.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));

      System.out.println(" * " + capitalizeName);
      System.out.println(" \tOfrece solución a los siguientes problemas:\n");

      List<TypeProblem> typesProblem = s.getTypesProblem();

      typesProblem.forEach(tp -> {
        System.out.println(" \t\t" + tp.getType_problem_name());
      });

      System.out.println();
    });

    boolean isInvalidOption = true;
    int amount_services = services.size();
    int option = -1;

    do {
      option = Inputs.getIntInput("\nElija un servicio del (1-" + amount_services + "): ",
          "Debe introducir un valor entre 1 y " + amount_services + ".");
      scanner.nextLine();

      isInvalidOption = !(option > 0 && option <= amount_services);

      if (isInvalidOption) {
        System.out.println("Opción inválida.");
      } else {
        System.out.print("Desea contratar otro servicio. ( y/n ) ");

        Service selectedService = services.get(option - 1);

        if (!client_services.contains(selectedService)) {
          client_services.add(selectedService);
        }

        String isContinue = scanner.nextLine().toLowerCase();

        isInvalidOption = isContinue.equals("y");
      }
    } while (isInvalidOption);

    customer.setClientServices(client_services);

    ComercialArea.handlerClient(customer, ActionClient.UPDATE_CLIENT);

    int amountClientServices = client_services.size();
    isInvalidOption = true;

    System.out.println("Elija el servicio con el cuál quiere reportar el incidente.");
    System.out.println("Tiene los siguientes servicios contratados.");

    for (int ind = 0; ind < amountClientServices; ind++) {
      System.out.print(" " + (ind + 1) + "-\t" + client_services.get(ind).getService_name() + "\n");
    }

    do {
      System.out.print("\n\nElija una opción (1-" + amountClientServices + "): ");

      option = Inputs.getIntInput("\n\nElija una opción (1-" + amountClientServices + "): ",
          "Debe introducir un valor entre 1 y " + amountClientServices + ".");

      isInvalidOption = !(option > 0 && option <= amountClientServices);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {
        Service selectedService = client_services.get(option - 1);

        incident.setService(selectedService);
      }
    } while (isInvalidOption);
  }

  private static void handlerTypesProblem(Incident incident) {
    List<TypeProblem> typesProblem = new PersistenceTypeProblem(manager).findAll();

    int option = -1;
    boolean isInvalidOption = true;
    int amountTypesProblem = typesProblem.size();

    List<TypeProblem> incidentTypeProblem = new ArrayList<>();

    System.out.print("\n¿Cuáles son los tipos de problemas asociados al incidente que desea reportar?\n\n");

    for (int ind = 0; ind < typesProblem.size(); ind++) {
      System.out.println(" " + (ind + 1) + "-\t" + typesProblem.get(ind).getType_problem_name());
    }

    do {
      option = Inputs.getIntInput("\nElija una opción (1-" + amountTypesProblem + "): ",
          "Debe introducir un valor entre 1 y " + amountTypesProblem + ".");
      scanner.nextLine();

      isInvalidOption = !(option > 0 && option <= amountTypesProblem);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {
        TypeProblem selectedTypeProblemService = typesProblem.get(option - 1);

        System.out.print("\nDesea agregar otro tipo de problema al incidente. ( y/n ) ");

        if (!incidentTypeProblem.contains(selectedTypeProblemService)) {
          incidentTypeProblem.add(selectedTypeProblemService);
        }

        String isContinue = scanner.nextLine().toLowerCase();

        isInvalidOption = isContinue.equals("y");
      }
    } while (isInvalidOption);

    incident.setIncident_type_problem(incidentTypeProblem);
  }

  private static void handlerDescription(Incident incident) {
    boolean isInvalidDescription = true;

    do {
      System.out.print("\n\nIngrese una descripción del problema. (máximo 255 caracteres)\n");

      String incidentDescription = scanner.nextLine();
      int lengthIncidentDescription = incidentDescription.length();

      isInvalidDescription = lengthIncidentDescription == 0 || lengthIncidentDescription > 255;

      if (isInvalidDescription) {
        System.out.println("\nDescripción no válida.");
      } else {
        incident.setDescription(incidentDescription);
      }
    } while (isInvalidDescription);
  }

}
