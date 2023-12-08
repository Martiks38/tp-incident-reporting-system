package com.tp.domain.client;

import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.incident.Incident;
import com.tp.domain.incident.IncidentObserver;
import com.tp.domain.notify.EmailDecorator;
import com.tp.domain.notify.NotifyBasic;
import com.tp.domain.notify.NotifyDecorator;
import com.tp.domain.service.Service;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "client", schema = "client")
@Entity
public class Client implements IncidentObserver {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long client_id;

  public Client(Long id) {
    client_id = id;
  }

  public Client(String cuit, String business_name, String mail, boolean state, List<Incident> incidents,
      List<Service> clientServices) {
    this.cuit = cuit;
    this.business_name = business_name;
    this.mail = mail;
    this.state = state;
    this.incidents = incidents;
    this.clientServices = clientServices;
  }

  @Setter
  @Column(nullable = false, length = 11)
  private String cuit;

  @Setter
  @Column(nullable = false, length = 80)
  private String business_name;

  @Setter
  @Column(nullable = false, length = 45)
  private String mail;

  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private Boolean state;

  @Setter
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Incident> incidents;

  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "client__service", joinColumns = @JoinColumn(name = "fk_cs_client"), inverseJoinColumns = @JoinColumn(name = "fk_cs_service"))
  private List<Service> clientServices;

  @Override
  public void update(Incident incident) {
    if (incident.getResolved()) {
      String message = "Buenos días.\nLe notificamos que el incidente reportado por " + this.business_name
          + " ya ha sido solucionado.\n\nIncidente:\n" + incident.getDescription() + "\n\nConsideraciones:\n"
          + incident.getConsiderations();

      receiveIncidentNotification(message);

      incident.unsubscribe();
    }
  }

  public void receiveIncidentNotification(String message) {
    NotifyDecorator notify = new EmailDecorator(new NotifyBasic());

    notify.emitMessage(message, this.mail);
  }

  public void copyProperties(Client source) {
    this.client_id = source.getClient_id();
    this.cuit = source.getCuit();
    this.business_name = source.getBusiness_name();
    this.mail = source.getMail();
    this.state = source.getState();
    this.incidents = source.getIncidents();
    this.clientServices = source.getClientServices();
  }

}
