package com.tp.domain.incident;

import java.sql.Date;
import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.client.Client;
import com.tp.domain.service.Service;
import com.tp.domain.technical.Technical;
import com.tp.domain.type_problem.TypeProblem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "incident", schema = "incident")
@Entity
public class Incident implements IncidentObservable {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long incident_id;

  public Incident(Long incident_id) {
    this.incident_id = incident_id;
  }

  public Incident(boolean resolved, String description, String considerations, Date create_time, Date time_is_up,
      boolean state, Technical technical, Client client, List<TypeProblem> incident_type_problem) {
    this.resolved = resolved;
    this.description = description;
    this.considerations = considerations;
    this.create_time = create_time;
    this.time_is_up = time_is_up;
    this.state = state;
    this.technical = technical;
    this.client = client;
    this.incident_type_problem = incident_type_problem;
  }

  @Transient
  private IncidentObserver clientObserver;

  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private Boolean resolved;

  public void setResolved(boolean resolved) {
    if (this.resolved != resolved && resolved) {
      this.resolved = resolved;
      this.notifyObservable();
    }

    if (this.resolved == null) {
      this.resolved = resolved;
    }
  }

  @Setter
  @Column(nullable = false)
  private String description;

  @Setter
  @Column(nullable = true)
  private String considerations;

  @Setter
  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date create_time;

  @Setter
  @Temporal(TemporalType.DATE)
  private Date time_is_up;

  @Setter
  @Temporal(TemporalType.DATE)
  private Date deadline;

  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private Boolean state;

  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_technical_id")
  private Technical technical;

  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_client_id")
  private Client client;

  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "incident__type_problem", joinColumns = @JoinColumn(name = "fk_itp_incident", nullable = false), inverseJoinColumns = @JoinColumn(name = "fk_itp_type_problem", nullable = false))
  private List<TypeProblem> incident_type_problem;

  @Setter
  @OneToOne(mappedBy = "incident", cascade = CascadeType.ALL)
  Service incident_service;

  @Override
  public void subscribe(IncidentObserver client) {
    this.clientObserver = client;
  }

  @Override
  public void unsubscribe() {
    this.clientObserver = null;
  }

  @Override
  public void notifyObservable() {
    if (clientObserver == null)
      return;

    clientObserver.update(this);
  }
}
