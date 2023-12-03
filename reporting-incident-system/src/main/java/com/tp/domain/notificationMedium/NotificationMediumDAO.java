package com.tp.domain.notificationMedium;

import java.util.List;

public interface NotificationMediumDAO {
  NotificationMedium findById(Long id);

  NotificationMedium findByName(String name);

  List<NotificationMedium> findAll();
}
