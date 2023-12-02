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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "technical", schema = "technical")
@Entity
public class Technical {

  @Id
  @Getter
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long technical_id;

  @Getter
  @Setter
  @Column(nullable = false, length = 60)
  private String technical_name;
  
  @Getter
  @Setter
  @Column(nullable = false)
  private int number_incidents_resolved;

  @Getter
  @Setter
  @Column(nullable = true)
  private Long incident_resolution_speed;

  @Getter
  @Setter
  @Column(nullable = false, length = 45)
  private String mail;

  @Getter
  @Setter
  @Column(nullable = false, length = 45)
  private String phone_number;

  @Getter
  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private boolean state;

  @Getter
  @Setter
  @OneToMany(mappedBy = "technical")
  private List<Incident> incidents;

  @Getter
  @Setter
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_notification_medium", nullable = false)
  private NotificationMedium medium;

  @Getter
  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "technical__specialty", joinColumns = @JoinColumn(name = "fk_ts_technical"), inverseJoinColumns = @JoinColumn(name = "fk_ts_specialty"))
  private List<Specialty> specialties;
}
