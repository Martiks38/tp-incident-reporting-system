package com.tp.domain.technical;

import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.incident.Incident;
import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.specialty.Specialty;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "technical", schema = "technical")
@Entity
public class Technical {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long technical_id;

  public Technical(Long id) {
    this.technical_id = id;
  }

  public Technical(String technical_name, int number_incidents_resolved, Long incident_resolution_speed, String mail,
      String phone_number, boolean state, List<Incident> incidents, NotificationMedium medium,
      List<Specialty> specialties) {
    this.technical_name = technical_name;
    this.number_incidents_resolved = number_incidents_resolved;
    this.incident_resolution_speed = incident_resolution_speed;
    this.mail = mail;
    this.phone_number = phone_number;
    this.state = state;
    this.incidents = incidents;
    this.medium = medium;
    this.specialties = specialties;
  }

  @Setter
  @Column(nullable = false, length = 60)
  private String technical_name;

  @Setter
  @Column(nullable = false)
  private int number_incidents_resolved;

  @Setter
  @Column(nullable = true)
  private Long incident_resolution_speed;

  @Setter
  @Column(nullable = false, length = 45)
  private String mail;

  @Setter
  @Column(nullable = false, length = 45)
  private String phone_number;

  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private boolean state;

  @Setter
  @OneToMany(mappedBy = "technical")
  private List<Incident> incidents;

  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_notification_medium", nullable = false)
  private NotificationMedium medium;

  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "technical__specialty", joinColumns = @JoinColumn(name = "fk_ts_technical"), inverseJoinColumns = @JoinColumn(name = "fk_ts_specialty"))
  private List<Specialty> specialties;
}
