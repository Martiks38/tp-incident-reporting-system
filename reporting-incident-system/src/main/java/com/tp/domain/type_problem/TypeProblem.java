package com.tp.domain.type_problem;

import java.util.List;

import com.tp.domain.specialty.Specialty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "type_problem", schema = "type_problem")
@Entity
public class TypeProblem {
  @Id
  @Getter
  @Column(nullable = false, name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long type_problem_id;

  @Getter
  @Setter
  @Column(nullable = false, length = 45)
  private String type_problem_name;

  @Getter
  @Setter
  @Column(nullable = false)
  private Long maximum_resolution_time;

  @Getter
  @Setter
  @Column(nullable = false)
  private Long estimated_resolution_time;

  @Getter
  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "type_problem__specialty", joinColumns = @JoinColumn(name = "fk_tps_type_problem"), inverseJoinColumns = @JoinColumn(name = "fk_tps_specialty"))
  private List<Specialty> specialties;
}
