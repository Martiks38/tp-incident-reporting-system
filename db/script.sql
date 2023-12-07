  -- MySQL Script generated by MySQL Workbench
  -- Fri Dec  1 14:15:05 2023
  -- Model: New Model    Version: 1.0
  -- MySQL Workbench Forward Engineering

  SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
  SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
  SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

  -- -----------------------------------------------------
  -- Schema incident-reporting-system
  -- -----------------------------------------------------
  DROP SCHEMA IF EXISTS `incident-reporting-system` ;

  -- -----------------------------------------------------
  -- Schema incident-reporting-system
  -- -----------------------------------------------------
  CREATE SCHEMA IF NOT EXISTS `incident-reporting-system` ;
  USE `incident-reporting-system` ;

  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`notification_medium`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`notification_medium` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`notification_medium` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `medium` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`));


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`technical`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`technical` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`technical` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `technical_name` VARCHAR(60) NOT NULL,
    `number_incidents_resolved` INT NOT NULL DEFAULT 0,
    `incident_resolution_speed` BIGINT NULL DEFAULT NULL,
    `mail` VARCHAR(45) NOT NULL,
    `phone_number` VARCHAR(45) NOT NULL,
    `fk_notification_medium` BIGINT,
	  state TINYINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_notification_medium_id` (`fk_notification_medium` ASC) VISIBLE,
    CONSTRAINT `fk_notification_medium_id`
      FOREIGN KEY (`fk_notification_medium`)
      REFERENCES `incident-reporting-system`.`notification_medium` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`specialty`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`specialty` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`specialty` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `specialty_name` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`id`));


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`technical__specialty`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`technical__specialty` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`technical__specialty` (
    `fk_ts_technical` BIGINT,
    `fk_ts_specialty` BIGINT,
    INDEX `fk_ts_technical` (`fk_ts_technical` ASC) VISIBLE,
    INDEX `fk_ts_specialty` (`fk_ts_specialty` ASC) VISIBLE,
    CONSTRAINT `fk_ts_technical`
      FOREIGN KEY (`fk_ts_technical`)
      REFERENCES `incident-reporting-system`.`technical` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
    CONSTRAINT `fk_ts_specialty`
      FOREIGN KEY (`fk_ts_specialty`)
      REFERENCES `incident-reporting-system`.`specialty` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`service`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`service` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`service` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `service_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`client`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`client` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`client` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `cuit` VARCHAR(11) NOT NULL,
    `business_name` VARCHAR(80) NOT NULL,
    `mail` VARCHAR(45) NOT NULL,
	state TINYINT NOT NULL,
    PRIMARY KEY (`id`));


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`type_problem`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`type_problem` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`type_problem` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `type_problem_name` VARCHAR(45) NOT NULL,
    `maximum_resolution_time` SMALLINT NOT NULL,
    `estimated_resolution_time` SMALLINT NOT NULL,
    complexity VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`));


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`type_problem__specialty`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`type_problem__specialty` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`type_problem__specialty` (
    `fk_tps_type_problem` BIGINT,
    `fk_tps_specialty` BIGINT,
    INDEX `fk_tps_type_problem` (`fk_tps_type_problem` ASC) VISIBLE,
    INDEX `fk_tps_specialty` (`fk_tps_specialty` ASC) VISIBLE,
    CONSTRAINT `fk_tps_type_problem`
      FOREIGN KEY (`fk_tps_type_problem`)
      REFERENCES `incident-reporting-system`.`type_problem` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
    CONSTRAINT `fk_tps_specialty`
      FOREIGN KEY (`fk_tps_specialty`)
      REFERENCES `incident-reporting-system`.`specialty` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`incident`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`incident` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`incident` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(255) NOT NULL,
    `considerations` VARCHAR(255) NOT NULL,
    `fk_technical_id` BIGINT,
    `fk_client_id` BIGINT,
    `resolved` TINYINT NOT NULL,
    `create_time` TIMESTAMP NOT NULL,
    `time_is_up` TIMESTAMP NULL DEFAULT NULL,
    `deadline` TIMESTAMP NULL DEFAULT NULL,
	`state` TINYINT NOT NULL,
    `fk_service_id` BIGINT,
    PRIMARY KEY (`id`),
    INDEX `fk_client_id` (`fk_client_id` ASC) VISIBLE,
    INDEX `fk_technical_id` (`fk_technical_id` ASC) VISIBLE,
    CONSTRAINT `fk_client_id`
      FOREIGN KEY (`fk_client_id`)
      REFERENCES `incident-reporting-system`.`client` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
    CONSTRAINT `fk_technical_id`
      FOREIGN KEY (`fk_technical_id`)
      REFERENCES `incident-reporting-system`.`technical` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
	CONSTRAINT `fk_service_id`
      FOREIGN KEY (`fk_service_id`)
      REFERENCES `incident-reporting-system`.`service` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`client__service`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`client__service` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`client__service` (
    `fk_cs_client` BIGINT,
    `fk_cs_service` BIGINT,
    INDEX `fk_cs_client` (`fk_cs_client` ASC) VISIBLE,
    INDEX `fk_cs_service` (`fk_cs_service` ASC) VISIBLE,
    CONSTRAINT `fk_cs_client`
      FOREIGN KEY (`fk_cs_client`)
      REFERENCES `incident-reporting-system`.`client` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
    CONSTRAINT `fk_cs_service`
      FOREIGN KEY (`fk_cs_service`)
      REFERENCES `incident-reporting-system`.`service` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`incident__type_problem`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`incident__type_problem` ;

CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`incident__type_problem` (
  `fk_itp_incident` BIGINT NULL DEFAULT NULL,
  `fk_itp_type_problem` BIGINT NULL DEFAULT NULL,
  INDEX `fk_itp_incident` (`fk_itp_incident` ASC) VISIBLE,
  INDEX `fk_itp_type_problem` (`fk_itp_type_problem` ASC) VISIBLE,
  CONSTRAINT `fk_itp_incident`
    FOREIGN KEY (`fk_itp_incident`)
    REFERENCES `incident-reporting-system`.`incident` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `fk_itp_type_problem`
    FOREIGN KEY (`fk_itp_type_problem`)
    REFERENCES `incident-reporting-system`.`type_problem` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL);

  -- -----------------------------------------------------
  -- Table `incident-reporting-system`.`service__type_problem`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `incident-reporting-system`.`service__type_problem` ;

  CREATE TABLE IF NOT EXISTS `incident-reporting-system`.`service__type_problem` (
    `fk_stp_service` BIGINT,
    `fk_stp_type_problem` BIGINT,
    INDEX `fk_stp_service` (`fk_stp_service` ASC) VISIBLE,
    INDEX `fk_stp_type_problem` (`fk_stp_type_problem` ASC) VISIBLE,
    CONSTRAINT `fk_stp_service`
      FOREIGN KEY (`fk_stp_service`)
      REFERENCES `incident-reporting-system`.`service` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL,
    CONSTRAINT `fk_stp_type_problem`
      FOREIGN KEY (`fk_stp_type_problem`)
      REFERENCES `incident-reporting-system`.`type_problem` (`id`)
      ON DELETE SET NULL
      ON UPDATE SET NULL);


  SET SQL_MODE=@OLD_SQL_MODE;
  SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
  SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

  -- -----------------------------------------------------
  -- Add values to tables
  -- -----------------------------------------------------

  INSERT INTO `incident-reporting-system`.`notification_medium` (`medium`)
    VALUES
      ("Email"),
      ("WhatsApp");

  INSERT INTO `incident-reporting-system`.technical
  VALUES
      (1, "Maider Gomez", 2, 30, "maidergomez@gmail.com", "3476123456", 1, 1),
      (2, "Gheorghe Galindo", 5, 21, "gheorghe.galindo@hotmail.com", "3411234567", 2, 1),
      (3, "Cesareo Gutierrez", 1, 42, "cesareo@gutierrez.com", "1112345678", 1, 1),
      (4, "Alejandra Puerta", 0, null, "alejandra_puerta@outlook.com", "3421234567", 2, 0);

  INSERT INTO `incident-reporting-system`.specialty
    VALUES
      (1, "ui/ux"),
      (2, "software"),
      (3, "training"),
      (4, "database"),
      (5, "security"),
      (6, "backup and recuperation"),
      (7, "technology consulting");

  INSERT INTO `incident-reporting-system`.technical__specialty
    VALUES
      (1, 1),
      (1, 2),
      (1, 3),
      (2, 1),
      (2, 2),
      (3, 3),
      (4, 4),
      (4, 5);

  INSERT INTO `incident-reporting-system`.`service`
    VALUES
      (1, 'basic'),
      (2, 'advanced prioritization service'),
      (3, 'escalation service'),
      (4, 'proactive maintenance'),
      (5, 'additional post-incident training');
      
  INSERT INTO `incident-reporting-system`.`client`
    VALUES
      (1, '34447117969', 'TecnoDynamics Solutions', "contact@technoDynamics.com", 1),
      (2, '27729800193', 'Global InnovateTech Services', "contact@innovateTech.com", 1),
      (3, '30956149653', 'Quantum Nexus Enterprises', "contact@quantumNexus.com", 1);

  INSERT INTO `incident-reporting-system`.type_problem
    VALUES
    (1, 'access problems', 7, 13, "simple"),
    (2, 'software errors', 12, 24, "complejo"),
    (3, 'connectivity problem', 2, 7, "simple"),
    (4, 'security issues', 30, 90, "complejo"),
    (5, 'updates and patches', 4, 14, "complejo"),
    (6, 'database errors', 15, 45, "complejo"),
    (7, 'integration problems', 5, 14, "simple"),
    (8, 'performance problems', 2, 7, "simple");

  INSERT INTO `incident-reporting-system`.service__type_problem
    VALUES
      (1, 1),  -- basic service can resolve access problems
      (2, 2),  -- advanced prioritization service can resolve software errors
      (2, 3),  -- advanced prioritization service can resolve connectivity problems
      (3, 4),  -- escalation service can resolve security issues
      (4, 5),  -- proactive maintenance can resolve updates and patches
      (5, 6),  -- additional post-incident training can resolve database errors
      (5, 7),  -- additional post-incident training can resolve integration problems
      (5, 8);  -- additional post-incident training can resolve performance problems

  INSERT INTO `incident-reporting-system`.type_problem__specialty
    VALUES
      (1, 1),
      (2, 2),
      (3, 2),
      (4, 5),
      (5, 2),
      (5, 6),
      (6, 4),
      (7, 2),
      (7, 3),
      (7, 4),
      (7, 5),
      (7, 6),
      (8, 2),
      (8, 4),
      (8, 5),
      (8, 7);
      
  INSERT INTO `incident-reporting-system`.incident
    VALUES
      (1, 'Los usuarios experimentan problemas intermitentes de conexión a la red, lo que resulta en la imposibilidad de acceder a servicios en línea o compartir archivos de manera efectiva.', 'Problemas intermitentes de conexión afectan la accesibilidad a servicios en línea y la eficacia en la compartición de archivos. Se requiere diagnóstico exhaustivo, considerando enrutador, posibles interferencias y configuraciones de firewall.', 1, 1, 0, '2023-11-24 16:13:34', null, null, 1, 2),
      (2, 'Se ha detectado un intento de acceso no autorizado a sistemas críticos de la empresa, lo que representa una amenaza potencial para la integridad y la confidencialidad de los datos.', 'Reporte de usuarios con errores al acceder a la base de datos, impactando aplicaciones dependientes. Se requiere revisión y corrección de consultas, asegurando la integridad de datos y optimizando el rendimiento.', 4, 3, 0, '2023-11-18 20:13:34', null, null, 1, 3),
      (3, 'Los usuarios informan errores al intentar acceder o manipular datos en la base de datos, lo que afecta la funcionalidad de las aplicaciones que dependen de la información almacenada.', 'Usuarios reportan errores en acceso y manipulación de datos en la base, afectando aplicaciones dependientes. Se necesita revisar y corregir consultas para garantizar la integridad y optimizar el rendimiento.', 4, 2, 0, '2023-11-20 01:13:34', null, null, 1, 5),
      (4, "Desafíos en el entrenamiento del modelo, resultando en baja precisión y rendimiento insatisfactorio.", "Optimizar el conjunto de datos, ajustar parámetros del modelo y aplicar técnicas avanzadas de entrenamiento para mejorar la calidad del modelo.", 3, 2, 1, "2023-11-24", "2023-12-3", "2023-12-20", 1, 5),
      (5, "Fallo en la integración de sistemas, causando pérdida de datos y disrupciones en los servicios.", "Implementar un middleware robusto, mejorar la validación de datos y establecer monitoreo en tiempo real para prevenir futuros fallos.", 1, 1, 1, "2023-11-24", "2023-12-2", "2023-12-31", 1, 2);

  INSERT INTO `incident-reporting-system`.client__service
    VALUES
      (1, 2),
      (2, 5),
      (3, 3);
      
INSERT INTO `incident-reporting-system`.incident__type_problem
	VALUES
		(1, 3),
        (2, 4),
        (3, 6),
        (4, 7),
        (5, 4);
      
-- SELECT * FROM `client`;
-- SELECT * from client__service;
-- SELECT * FROM service;
-- SELECT * FROM technical;
-- SELECT * FROM specialty;
-- SELECT * FROM incident;
-- SELECT * FROM type_problem;
-- SELECT * FROM technical__specialty;
-- SELECT * FROM type_problem__specialty;
-- SELECT * FROM service__type_problem;
