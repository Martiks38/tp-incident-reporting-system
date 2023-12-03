package com.tp.domain.notificationMedium;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "notification_medium", schema = "notification_medium")
@Entity
public class NotificationMedium {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  private Long medium_id;

  public NotificationMedium(String medium) {
    this.medium = medium;
  }

  @Setter
  @Column(nullable = false, length = 15)
  private String medium;
}
