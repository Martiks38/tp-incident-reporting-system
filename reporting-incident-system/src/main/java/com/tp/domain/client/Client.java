package com.tp.domain.client;

import java.util.List;

import org.hibernate.type.NumericBooleanConverter;

import com.tp.domain.incident.Incident;
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

@NoArgsConstructor
@Table(name = "client", schema = "client")
@Entity
public class Client {
  @Id
  @Getter
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long client_id;

  public Client(String cuit, String business_name, String mail, boolean state, List<Incident> incidents, List<Service> client_services){
    this.cuit = cuit;
    this.business_name = business_name;
    this.mail = mail;
    this.state = state;
    this.incidents = incidents;
    this.client_services = client_services;
  }

  @Getter
  @Setter
  @Column(nullable = false, length = 11)
  private String cuit;
  
  @Getter
  @Setter
  @Column(nullable = false, length = 80)
  private String business_name;
  
  @Getter
  @Setter
  @Column(nullable = false, length = 45)
  private String mail;
  
  @Getter
  @Setter
  @Convert(converter = NumericBooleanConverter.class)
  @Column(nullable = false)
  private boolean state;

  @Getter
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Incident> incidents;
  
  @Getter
  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "client__service", joinColumns = @JoinColumn(name = "fk_cs_client", nullable = false), inverseJoinColumns = @JoinColumn(name = "fk_cs_service", nullable = false))
  private List<Service> client_services;
}
