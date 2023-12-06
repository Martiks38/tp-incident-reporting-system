package com.tp.domain.client;

import java.util.List;
import java.util.Objects;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.incident.Incident;
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
public class Client {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long client_id;

  public Client(Long id) {
    client_id = id;
  }

  public Client(String cuit, String business_name, String mail, boolean state, List<Incident> incidents,
      List<Service> client_services) {
    this.cuit = cuit;
    this.business_name = business_name;
    this.mail = mail;
    this.state = state;
    this.incidents = incidents;
    this.client_services = client_services;
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
  @JoinTable(name = "client__service", joinColumns = @JoinColumn(name = "fk_cs_client", nullable = false), inverseJoinColumns = @JoinColumn(name = "fk_cs_service", nullable = false))
  private List<Service> client_services;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Client))
      return false;
    Client client = (Client) o;
    return state == client.state && Objects.equals(client_id, client.client_id) && Objects.equals(cuit, client.cuit)
        && Objects.equals(business_name, client.business_name) && Objects.equals(mail, client.mail)
        && Objects.equals(incidents, client.incidents) && Objects.equals(client_services, client.client_services);
  }

  @Override
  public String toString() {
    return "Client{" +
        "client_id=" + client_id +
        ", cuit='" + cuit + '\'' +
        ", business_name='" + business_name + '\'' +
        ", mail='" + mail + '\'' +
        ", state=" + state +
        '}';
  }

  public void receiveIncidentNotification(String message) {
    NotifyDecorator notify = new EmailDecorator(new NotifyBasic());

    notify.emitMessage(message, this.mail);
  }
}
