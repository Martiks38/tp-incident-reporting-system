package com.tp.domain.notificationMedium;

import java.util.List;

public interface NotificationMediumDAO {
  NotificationMedium find(int id);

  List<NotificationMedium> findAll();

  void save(NotificationMedium data);

  void update(int id, NotificationMedium data);

  void delete(int id);
}
