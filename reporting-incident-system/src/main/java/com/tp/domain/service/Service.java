package com.tp.domain.service;

import java.util.List;

import com.tp.domain.client.Client;
import com.tp.domain.incident.Incident;
import com.tp.domain.type_problem.TypeProblem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "service", schema = "service")
@Entity
public class Service {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long service_id;

  public Service(String service_name, List<Client> clients, List<TypeProblem> typesProblem) {
    this.service_name = service_name;
    this.clients = clients;
    this.typesProblem = typesProblem;
  }

  @Setter
  @Column(nullable = false, unique = true, length = 45)
  private String service_name;

  @Setter
  @ManyToMany(mappedBy = "clientServices", cascade = CascadeType.ALL)
  private List<Client> clients;

  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "service__type_problem", joinColumns = @JoinColumn(name = "fk_stp_service", nullable = false), inverseJoinColumns = @JoinColumn(name = "fk_stp_type_problem", nullable = false))
  private List<TypeProblem> typesProblem;

  @Setter
  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
  private List<Incident> incidents;
}
