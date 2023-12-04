package com.tp.domain.technical;

import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.assets.Constant;
import com.tp.domain.incident.Incident;
import com.tp.domain.notificationMedium.NotificationMedium;
import com.tp.domain.specialty.Specialty;
import com.tp.infrastructure.notificationMedium.PersistenceNotificationMedium;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Persistence;
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
  private Boolean state;

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

  public void setPreferredNotificationMethod(String medium) {
    final String persistenceUnitName = Constant.PERSISTENCE_UNIT_NAME;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    EntityManager manager = factory.createEntityManager();
    EntityTransaction transaction = manager.getTransaction();

    PersistenceNotificationMedium persistenceNotificationMedium = new PersistenceNotificationMedium(manager);

    NotificationMedium notificationMedium = persistenceNotificationMedium.findByName(medium);

    try {
      transaction.begin();

      if (notificationMedium == null) {
        throw new RuntimeException("No existe el medio de notificación solicitado.\n");
      }

      this.setMedium(notificationMedium);
      manager.merge(this);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }

      // Para ver la traza pero se debería borrar y enviar la traza a un archivo o una
      // base de datos que almacene los errores
      e.printStackTrace();
      System.err.println("Error en la transacción: " + e.getMessage());
    } finally {
      manager.close();
    }
  }

}
