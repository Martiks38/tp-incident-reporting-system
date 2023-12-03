package com.tp.domain.specialty;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "specialty", schema = "specialty")
@Entity
public class Specialty {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long specialty_id;

  public Specialty(String specialty_name) {
    this.specialty_name = specialty_name;
  }

  @Setter
  @Column(nullable = false, length = 60)
  private String specialty_name;
}
