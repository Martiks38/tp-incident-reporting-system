package com.tp.domain.incident;

import java.sql.Date;
import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.client.Client;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "incident", schema = "incident")
@Entity
public class Incident {
  @Id
  @Getter
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long incident_id;

  @Getter
  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private boolean resolved;

  @Getter
  @Setter
  @Column(nullable = false)
  private String description;

  @Getter
  @Setter
  @Column(nullable = false)
  private String considerations;

  @Getter
  @Setter
  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date create_time;

  @Getter
  @Setter
  @Temporal(TemporalType.DATE)
  private Date time_us_up;

  @Getter
  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private boolean state;

  @Getter
  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_technical_id")
  private Technical technical;

  @Getter
  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_client_id")
  private Client client;

  @Getter
  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "incident__type_problem", joinColumns = @JoinColumn(name = "fk_itp_problem", nullable = false), inverseJoinColumns = @JoinColumn(name = "fk_itp_incident", nullable = false))
  private List<TypeProblem> incident_type_problem;
}
