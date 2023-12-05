package com.tp.domain.operator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.tp.application.GetEntityManager;
import com.tp.assets.ActionClient;
import com.tp.domain.client.Client;
import com.tp.domain.commercial_area.ComercialArea;
import com.tp.domain.incident.Incident;
import com.tp.domain.service.Service;
import com.tp.domain.specialty.Specialty;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;
import com.tp.infrastructure.client.PersistenceClient;
import com.tp.infrastructure.incident.PersistenceIncident;
import com.tp.infrastructure.service.PersistenceService;
import com.tp.infrastructure.technical.PersistenceTechnical;
import com.tp.infrastructure.type_problem.PersistenceTypeProblem;

import jakarta.persistence.EntityManager;

public class Operator {
  static final Scanner scanner = new Scanner(System.in);
  static final EntityManager manager = GetEntityManager.getManager();

  public static void assistCustomer() {
    PersistenceClient persistenceClient = new PersistenceClient(manager);

    String business_name;
    String cuit;

    Client customer = new Client();

    System.out.print("Buenos días.");
    System.out.print("Por favor ingrese su razón social.");

    business_name = scanner.nextLine();

    System.out.print("Por favor ingrese su cuit");

    cuit = scanner.nextLine();

    customer.setBusiness_name(business_name);
    customer.setCuit(cuit);
    customer.setState(true);

    findClientDate(customer, persistenceClient);

    List<Service> client_services = customer.getClient_services();

    Incident newIncident = new Incident();
    Date currentDay = Date.valueOf(LocalDate.now());

    newIncident.setClient(customer);
    newIncident.setResolved(false);
    newIncident.setConsiderations("");
    newIncident.setCreate_time(currentDay);
    newIncident.setState(true);

    if (client_services.size() == 0) {
      System.out.print("Mensaje para el Operador");
      System.out.print("No ha contratado ningún servicio.");

      System.out.print("Mensaje para el cliente");
      System.out.print("¿Cuál de nuestros servicios desea contratar?\n\n");

      handlerServiceClient(customer, newIncident);

      handlerTypesProblem(newIncident);

      handlerDescription(newIncident);

      PersistenceIncident persistenceIncident = new PersistenceIncident(manager);

      Service incidentService = newIncident.getIncident_service();
      List<TypeProblem> typesProblemOfIncidentService = incidentService.getTypesProblem();

      if (typesProblemOfIncidentService.containsAll(newIncident.getIncident_type_problem())) {

        createIncident(newIncident, persistenceIncident);

      } else {
        System.out.println(
            "Los tipos de problemas asociados al incidente reportado no coinciden con el servicio adjuntado.\n");
      }

    } else {
      System.out.print("El cliente tiene contratado los siguientes servicios:\n\n");

      customer.getClient_services().forEach(service -> {
        System.out.print(service.getService_name() + "\n");
      });
    }

    scanner.close();

  }

  private static void findClientDate(Client client, PersistenceClient persistenceClient) {
    Client customerData = persistenceClient.findByName(client.getBusiness_name());

    if (customerData != null) {
      client = customerData;
    }

    if (customerData == null || !customerData.getState()) {
      String email;

      System.out
          .print("Es un cliente nuevo le vamos a pedir que ingrese los datos que le solicitaremos a continuación.");
      System.out.print("Ingrese una cuenta de email.");

      email = scanner.nextLine();

      System.out.print("Muchas gracias.");

      client.setMail(email);
      client.setClient_services(new ArrayList<>());
      client.setIncidents(new ArrayList<>());

      ComercialArea.handlerClient(client, ActionClient.CREATE_CLIENT, null);
    }
  }

  private static void handlerServiceClient(Client customer, Incident incident) {
    List<Service> services = new PersistenceService(manager).findAll();
    List<Service> client_services = customer.getClient_services();

    services.forEach(s -> {
      String name = s.getService_name();
      StringBuilder capitalizeName = new StringBuilder();

      capitalizeName.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));

      System.out.print(" *\t" + capitalizeName);
      System.out.print(" \t\tOfrece solución a los siguientes problemas:\n");

      List<TypeProblem> typesProblem = s.getTypesProblem();

      typesProblem.forEach(tp -> {
        System.out.print(" \t\t" + tp.getType_problem_name());
      });

      System.out.println();
    });

    boolean isInvalidOption = true;
    int amount_services = services.size();
    int option = -1;

    do {
      System.out.print("Elija una opción (1-" + services.size() + "): ");

      option = scanner.nextInt();

      isInvalidOption = !(option > 0 && option <= amount_services);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {
        System.out.print("Desea contratar otro servicio. ( y/n )");

        Service selectedService = services.get(option - 1);

        if (!client_services.contains(selectedService)) {
          client_services.add(selectedService);
        }

        String isContinue = scanner.nextLine().toLowerCase();

        isInvalidOption = isContinue.equals("y");
      }
    } while (isInvalidOption);

    customer.setClient_services(client_services);

    ComercialArea.handlerClient(customer, ActionClient.UPDATE_CLIENT, null);

    int amountClientServices = client_services.size();
    isInvalidOption = true;

    System.out.print("Elija el servicio con el cuál quiere reportar el incidente.");
    System.out.println("Sus servicios contratados son:\n");

    client_services.forEach(service -> {
      System.out.print(service.getService_name());
    });

    do {
      System.out.print("\n\nElija una opción (1-" + amountClientServices + "): ");

      option = scanner.nextInt();

      isInvalidOption = !(option > 0 && option <= amountClientServices);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {

        Service selectedService = services.get(option - 1);

        if (!client_services.contains(selectedService)) {
          client_services.add(selectedService);
        }
      }
    } while (isInvalidOption);
  }

  private static void handlerTypesProblem(Incident incident) {
    List<TypeProblem> typesProblem = new PersistenceTypeProblem(manager).findAll();

    int option = -1;
    boolean isInvalidOption = true;
    int amountTypesProblem = typesProblem.size();

    List<TypeProblem> incidentTypeProblem = new ArrayList<>();

    System.out.print("¿Cuáles son los tipos problemas asociados al incidente que desea reportar?\n\n");

    typesProblem.forEach(tp -> {
      System.out.print(tp.getType_problem_name());
    });

    do {
      System.out.print("Elija una opción (1-" + amountTypesProblem + "): ");

      option = scanner.nextInt();

      isInvalidOption = !(option > 0 && option <= amountTypesProblem);

      if (isInvalidOption) {
        System.out.print("Opción inválida.\n\n");
      } else {
        TypeProblem selectedTypeProblemService = typesProblem.get(option - 1);

        System.out.print("Desea agregar otro tipo de problema al incidente. ( y/n )");

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
      System.out.print("Ingrese una descripción del problema. (máximo 255 caracteres)");

      String incidentDescription = scanner.nextLine();
      int lengthIncidentDescription = incidentDescription.length();

      if (lengthIncidentDescription == 0 || lengthIncidentDescription > 255) {
        System.out.print("Descripción no válida.");
      } else {
        incident.setDescription(incidentDescription);
      }
    } while (isInvalidDescription);
  }

  private static void createIncident(Incident incident, PersistenceIncident persistenceIncident) {
    List<Technical> technicals = new PersistenceTechnical(manager).findAll();
    List<Technical> possibleTechnicians = new ArrayList<>();

    persistenceIncident.save(incident);

    possibleTechnicians = technicals
        .stream()
        .filter(t -> {
          List<Specialty> specialties = t.getSpecialties();

        }).collect(Collectors.toList());

  }
}
