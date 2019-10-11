-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: royal_academy
-- ------------------------------------------------------
-- Server version	8.0.17


--
-- Table structure for table `carrera_curso`
--

DROP TABLE IF EXISTS `carrera_curso`;
CREATE TABLE `carrera_curso` (
  `id_curso` int(11) NOT NULL,
  `id_carrera` int(11) NOT NULL,
  PRIMARY KEY (`id_curso`,`id_carrera`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `carrera_curso`
--

LOCK TABLES `carrera_curso` WRITE;
/*!40000 ALTER TABLE `carrera_curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrera_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carreras`
--

DROP TABLE IF EXISTS `carreras`;
CREATE TABLE `carreras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
INSERT INTO `carreras` VALUES (6,'Licenciatura en Geografia'),(7,'Licenciatura Cientifica');
/*!40000 ALTER TABLE `carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso_usuario`
--

DROP TABLE IF EXISTS `curso_usuario`;
CREATE TABLE `curso_usuario` (
  `id_usuario` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_curso`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `curso_usuario`
--

LOCK TABLES `curso_usuario` WRITE;
/*!40000 ALTER TABLE `curso_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `curso_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursoexamen`
--

DROP TABLE IF EXISTS `cursoexamen`;
CREATE TABLE `cursoexamen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_examen` int(11) DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cursoexamen`
--

LOCK TABLES `cursoexamen` WRITE;
/*!40000 ALTER TABLE `cursoexamen` DISABLE KEYS */;
/*!40000 ALTER TABLE `cursoexamen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
CREATE TABLE `cursos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(40) DEFAULT NULL,
  `criterioAprobacion` decimal(10,0) DEFAULT NULL,
  `id_carrera` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (5,'Geografia',0,6),(6,'Ciencia',0,7);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
CREATE TABLE `examenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(60) DEFAULT NULL,
  `id_usuario_creador` int(11) DEFAULT NULL,
  `criterioAprobacion` decimal(10,0) DEFAULT NULL,
  `fechaCreacion` date DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `examenes`
--

LOCK TABLES `examenes` WRITE;
/*!40000 ALTER TABLE `examenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenresolucion`
--

DROP TABLE IF EXISTS `examenresolucion`;
CREATE TABLE `examenresolucion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_alumno` int(11) NOT NULL,
  `id_curso_examen` int(11) NOT NULL,
  `id_respuesta` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `examenresolucion`
--

LOCK TABLES `examenresolucion` WRITE;
/*!40000 ALTER TABLE `examenresolucion` DISABLE KEYS */;
/*!40000 ALTER TABLE `examenresolucion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funciones`
--

DROP TABLE IF EXISTS `funciones`;
CREATE TABLE `funciones` (
  `id_funcion` int(11) NOT NULL,
  `ds_funcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_funcion`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `funciones`
--

LOCK TABLES `funciones` WRITE;
/*!40000 ALTER TABLE `funciones` DISABLE KEYS */;
INSERT INTO `funciones` VALUES (1,'Logged Admin'),(2,'Logged Alumno'),(3,'Perfiles'),(4,'Usuarios'),(5,'Sedes'),(6,'Carrera'),(7,'Cursos'),(8,'Examenes'),(9,'Preguntas'),(10,'Admin'),(11,'Docente'),(12,'Alumno'),(13,'Perfil Alumno'),(14,'Calendario'),(15,'Correccion Examen');
/*!40000 ALTER TABLE `funciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funciones_perfil`
--

DROP TABLE IF EXISTS `funciones_perfil`;
CREATE TABLE `funciones_perfil` (
  `id_funcion` int(11) NOT NULL,
  `id_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_funcion`,`id_rol`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `funciones_perfil`
--

LOCK TABLES `funciones_perfil` WRITE;
/*!40000 ALTER TABLE `funciones_perfil` DISABLE KEYS */;
INSERT INTO `funciones_perfil` VALUES (1,'1'),(1,'13'),(1,'15'),(1,'3'),(2,'2'),(3,'1'),(4,'1'),(5,'1'),(6,'1'),(7,'1'),(8,'1'),(8,'15'),(9,'1'),(9,'15'),(10,'1'),(11,'15'),(12,'2'),(13,'2'),(14,'1'),(15,'1'),(15,'15'),(15,'2');
/*!40000 ALTER TABLE `funciones_perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripcion_examen`
--

DROP TABLE IF EXISTS `inscripcion_examen`;
CREATE TABLE `inscripcion_examen` (
  `id_cursoexamen` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `entregado` tinyint(4) DEFAULT '0',
  `aprobado` tinyint(4) DEFAULT '0',
  `resultado` decimal(10,0) DEFAULT '0',
  PRIMARY KEY (`id_cursoexamen`,`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `inscripcion_examen`
--

LOCK TABLES `inscripcion_examen` WRITE;
/*!40000 ALTER TABLE `inscripcion_examen` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripcion_examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `native`
--

DROP TABLE IF EXISTS `native`;
CREATE TABLE `native` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `native`
--

LOCK TABLES `native` WRITE;
/*!40000 ALTER TABLE `native` DISABLE KEYS */;
INSERT INTO `native` VALUES (4),(4),(4),(4),(4),(4),(4),(4),(4),(4),(4);
/*!40000 ALTER TABLE `native` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notas`
--

DROP TABLE IF EXISTS `notas`;
CREATE TABLE `notas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nota` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
/*!40000 ALTER TABLE `notas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opciones_pregunta`
--

DROP TABLE IF EXISTS `opciones_pregunta`;
CREATE TABLE `opciones_pregunta` (
  `id_opcion` int(11) NOT NULL AUTO_INCREMENT,
  `id_pregunta` int(11) NOT NULL,
  `respuesta_correcta` tinyint(4) DEFAULT NULL,
  `respuesta` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_opcion`,`id_pregunta`)
) ENGINE=MyISAM AUTO_INCREMENT=164 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `opciones_pregunta`
--

LOCK TABLES `opciones_pregunta` WRITE;
/*!40000 ALTER TABLE `opciones_pregunta` DISABLE KEYS */;
INSERT INTO `opciones_pregunta` VALUES (94,31,0,'Hot Springs'),(93,31,1,'Little Rock'),(92,31,0,'Kansas'),(91,30,0,'Ninguna es correcta'),(90,30,0,'CB'),(89,30,1,'CU'),(88,30,0,'CA'),(87,29,0,'Ninguna es correcta'),(86,29,0,'CB'),(85,29,0,'CU'),(84,29,1,'CA'),(83,28,1,'Alemania'),(82,28,0,'Mongolia'),(81,28,0,'Chile'),(80,28,0,'España'),(79,27,0,'Ninguna es correcta'),(78,27,0,'Portugal'),(77,27,1,'Australia'),(76,27,0,'Estados Unidos'),(75,26,0,'Hungria'),(74,26,0,'Moldavia'),(73,26,0,'Armenia'),(72,26,1,'Liechtenstein'),(95,31,0,'Washington'),(96,32,0,'Veranos secos y calurosos'),(97,32,0,'Es un tipo de clima templado'),(98,32,1,'Lluvias muy abundantes'),(99,32,0,'Variables temperaturas en primavera'),(100,33,0,'La hamburguesa'),(101,33,0,'El pastel de cangrejo'),(102,33,0,'La tarta de manzana'),(103,33,1,'Todas son típicas'),(104,34,0,'Unión Austrohúngara'),(105,34,1,'Unión Africana'),(106,34,0,'Unión Americana'),(107,34,0,'Unión Afroamericana'),(108,35,0,'Tres'),(109,35,0,'Cuatro'),(110,35,1,'Cinco'),(111,35,0,'Seis'),(112,36,1,'Berna'),(113,36,0,'Zurich'),(114,36,0,'Ginebra'),(115,36,0,'Basilea'),(116,37,0,'Chile'),(117,37,1,'Argentina'),(118,37,0,'Sudáfrica'),(119,37,0,'Nueva Zelanda'),(120,38,0,'Mauritania'),(121,38,0,'Senegal'),(122,38,0,'Gambia'),(123,38,1,'Todas tienen costa'),(124,39,0,'En el Karakórum'),(125,39,1,'En el Himalaya'),(126,39,0,'En los Andes'),(127,39,0,'En las Montañas Rocosas'),(128,40,1,'Bolivia y Perú'),(129,40,0,'Bolivia y Paraguay'),(130,40,0,'Bolivia y Brasil'),(131,40,0,'Bolivia y Argentina'),(132,41,1,'Islotes'),(133,41,0,'Península'),(134,41,0,'Archipiélago'),(135,41,0,'Meandro'),(136,42,1,'Budismo'),(137,42,0,'Taoísmo'),(138,42,0,'Confucianismo'),(139,42,0,'Cristianismo'),(140,43,1,'Un acantilado'),(141,43,0,'Un barranco'),(142,43,0,'Una montaña'),(143,43,0,'Un puente'),(144,44,0,'Crystal Island'),(145,44,0,'Torre Eiffel'),(146,44,1,'Burjal Arab'),(147,44,0,'Crosgrove'),(148,45,0,'Oceanía'),(149,45,0,'América'),(150,45,1,'Asia'),(151,45,0,'Antártida'),(152,46,0,'Frankfurt'),(153,46,1,'Berlín'),(154,46,0,'Múnich'),(155,46,0,'Dublin'),(156,47,0,'Lima'),(157,47,1,'Quito'),(158,47,0,'Bogota'),(159,47,0,'Guayaquil'),(160,48,0,'Japón'),(161,48,0,'Jamaica'),(162,48,0,'Madagascar'),(163,48,1,'Ghana');
/*!40000 ALTER TABLE `opciones_pregunta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `id_pais` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_pais`)
) ENGINE=MyISAM AUTO_INCREMENT=599 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (54,'Argentina'),(58,'Colombia'),(598,'Uruguay'),(56,'Chile');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisofunciones`
--

DROP TABLE IF EXISTS `permisofunciones`;
CREATE TABLE `permisofunciones` (
  `id_funcion` int(11) NOT NULL,
  `ds_funcion` varchar(255) DEFAULT NULL,
  `habilitada` int(11) NOT NULL,
  PRIMARY KEY (`id_funcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permisofunciones`
--

LOCK TABLES `permisofunciones` WRITE;
/*!40000 ALTER TABLE `permisofunciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisofunciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntas`
--

DROP TABLE IF EXISTS `preguntas`;
CREATE TABLE `preguntas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pregunta` varchar(200) DEFAULT NULL,
  `id_carrera` int(11) DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `preguntas`
--

LOCK TABLES `preguntas` WRITE;
/*!40000 ALTER TABLE `preguntas` DISABLE KEYS */;
INSERT INTO `preguntas` VALUES (30,'¿Cuál es el código internacional para Cuba?',6,5),(29,'¿Cuál es el código internacional para Cuba?',6,5),(28,'¿En cuál de los siguientes países NO hay ningún desierto?',6,5),(27,'¿A qué país pertenece la isla de Tasmania?',6,5),(26,'¿Cuál es el país menos turístico de Europa?',6,5),(31,'¿Cuál es la capital del estado de Arkansas?',6,5),(32,'¿Cuál de estas características no pertenece al clima Mediterráneo?',6,5),(33,'¿Cuál de las siguientes especialidades NO es típica de la cocina estadounidense?',6,5),(34,'¿Qué es la UA?',6,5),(35,'¿Con cuántos países limita Argentina?',6,5),(36,'¿Cuál es la capital de Suiza?',6,5),(37,'¿En qué país está Ushuaia, la ciudad más al sur del mundo?',6,5),(38,'¿Cuál de estos países africanos no tiene costa?',6,5),(39,'¿En qué cordillera están la mayoría de las grandes montañas?',6,5),(40,'¿Entre qué dos países está el lago Titicaca? ',6,5),(41,'¿Qué accidente geográfico se define como un conjunto de islas, islotes y diminutas masas de tierra cercanas entre sí?',6,5),(42,'¿Cuál es la religión mayoritaria en China?',6,5),(43,'¿Que accidente geográfico se define como una pendiente vertical abrupta?',6,5),(44,'¿Cuál es el edificio más famoso en Dubai?',6,5),(45,'¿En qué continente está la India?',6,5),(46,'¿Cúal es la capital de Alemania?',6,5),(47,'¿Cuál es la capital de Ecuador?',6,5),(48,'¿Cuál de estos países no está en una isla?',6,5);
/*!40000 ALTER TABLE `preguntas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntaxexamen`
--

DROP TABLE IF EXISTS `preguntaxexamen`;
CREATE TABLE `preguntaxexamen` (
  `id_examen` int(11) NOT NULL,
  `id_pregunta` int(11) NOT NULL,
  PRIMARY KEY (`id_examen`,`id_pregunta`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `preguntaxexamen`
--

LOCK TABLES `preguntaxexamen` WRITE;
/*!40000 ALTER TABLE `preguntaxexamen` DISABLE KEYS */;
/*!40000 ALTER TABLE `preguntaxexamen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respuestas`
--

DROP TABLE IF EXISTS `respuestas`;
CREATE TABLE `respuestas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `respuesta` varchar(40) DEFAULT NULL,
  `id_pregunta` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `respuestas`
--

LOCK TABLES `respuestas` WRITE;
/*!40000 ALTER TABLE `respuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `respuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'Alumno'),(15,'Docente'),(1,'Admin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sede_carrera`
--

DROP TABLE IF EXISTS `sede_carrera`;
CREATE TABLE `sede_carrera` (
  `id_carrera` int(11) NOT NULL,
  `id_sede` int(11) NOT NULL,
  PRIMARY KEY (`id_carrera`,`id_sede`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sede_carrera`
--

LOCK TABLES `sede_carrera` WRITE;
/*!40000 ALTER TABLE `sede_carrera` DISABLE KEYS */;
INSERT INTO `sede_carrera` VALUES (6,2),(6,3),(6,4),(7,2),(7,3),(7,4);
/*!40000 ALTER TABLE `sede_carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sede_usuario`
--

DROP TABLE IF EXISTS `sede_usuario`;
CREATE TABLE `sede_usuario` (
  `id_usuario` int(11) NOT NULL,
  `id_sede` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_sede`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sede_usuario`
--

LOCK TABLES `sede_usuario` WRITE;
/*!40000 ALTER TABLE `sede_usuario` DISABLE KEYS */;
INSERT INTO `sede_usuario` VALUES (3,2),(3,3),(3,4),(8,3);
/*!40000 ALTER TABLE `sede_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sedes`
--

DROP TABLE IF EXISTS `sedes`;
CREATE TABLE `sedes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pais` int(11) DEFAULT NULL,
  `sede` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sedes`
--

LOCK TABLES `sedes` WRITE;
/*!40000 ALTER TABLE `sedes` DISABLE KEYS */;
INSERT INTO `sedes` VALUES (3,598,'Sede Puerto Madero'),(2,54,'Sede Lanús'),(4,598,'Sede Punta del Este');
/*!40000 ALTER TABLE `sedes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(40) DEFAULT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `apellido` varchar(40) DEFAULT NULL,
  `telefono` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `dni` varchar(40) DEFAULT NULL,
  `verificado` tinyint(1) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','Gonzalo','Berro','1535949261','berro.gonza2195@gmail.com','12345678',1,1,'1995-12-21'),(2,'admin','admin','admin','admin','admin','admin',1,1,'2019-09-23'),(3,'39594954','JUAN','BOTTEGA','1536880928','juanpbottega@gmail.com','39594954',1,2,'2019-09-16'),(5,'38856962','Milton','Echague','15452145','miltonyoelechague@gmail.com','38856962',1,1,'2019-09-27'),(6,'36826344','Nico','Gianni','42142525','nicolasgianni11@gmail.com','36826344',0,1,'2019-09-27'),(7,'elputi','Ignacio','Ledesma','1414141414','ledignacio@gmail.com','elputi',0,2,'2019-09-27'),(15,'dawda','awdwa','awdwad','awdwa','\';drop database royal_academy;\'','dawda',0,2,'2019-10-15'),(16,'awdwad','adwad','awdawd','awdawd','\';(drop database royal_academy;)\'','awdwad',0,2,'2019-10-08');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'royal_academy'
--
/*!50003 DROP PROCEDURE IF EXISTS `cursosDisponiblesUsuario` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cursosDisponiblesUsuario`(IN id_usuario INT, IN id_carrera INT)
BEGIN
SELECT * from cursos s
 where (NOT EXISTS (select * from curso_usuario sc where sc.id_curso = s.id and sc.id_usuario = id_usuario) and (id_carrera=s.id_carrera or id_carrera = 0));

END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `cursosHabilitadasUsuario` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cursosHabilitadasUsuario`(IN id_usuario int)
BEGIN
 SELECT * from cursos s where 
 EXISTS (select * from curso_usuario sc where sc.id_curso = s.id and sc.id_usuario = id_usuario);

END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminarPreguntasAsociadasExamen` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarPreguntasAsociadasExamen`(IN id_examen INT)
BEGIN
	DELETE FROM preguntaxexamen pe where preguntaxexamen.id_examen = id_examen;
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `funcionesDisponibles` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `funcionesDisponibles`(IN id_rol INT)
BEGIN
 SELECT * from funciones f where (0=id_rol or NOT EXISTS (select * from funciones_perfil fp where fp.id_funcion = f.id_funcion and fp.id_rol = id_rol));
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `funcionesHabilitadas` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `funcionesHabilitadas`(IN id_rol INT)
BEGIN
 SELECT * from funciones f where EXISTS (select * from funciones_perfil fp where fp.id_funcion = f.id_funcion and fp.id_rol = id_rol);
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `getPermisoFunciones` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPermisoFunciones`(IN id_rol int)
BEGIN
select id_funcion,
ds_funcion
,CASE
    WHEN (select id_funcion from funciones_perfil fp where fp.id_rol=id_rol and fp.id_funcion = f.id_funcion) then 1 else 0 end habilitada
 from funciones f;
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `preguntasDisponibles` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `preguntasDisponibles`(IN id_examen INT)
BEGIN
SELECT * from preguntas s
 where 0=id_examen or (s.id_curso = (select id_curso from examenes e where e.id = id_examen) and 
 NOT EXISTS (select * from preguntaxexamen sc where sc.id_examen = id_examen and s.id = sc.id_pregunta));
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `preguntasHabilitadas` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `preguntasHabilitadas`(IN id_examen int)
BEGIN
 SELECT * from preguntas s where 
 EXISTS (select * from preguntaxexamen sc where sc.id_pregunta = s.id and sc.id_examen = id_examen);
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `sedesDisponibles` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sedesDisponibles`(IN id_carrera INT)
BEGIN
SELECT * from sedes s
 where (0=id_carrera or NOT EXISTS (select * from sede_carrera sc where sc.id_sede = s.id and sc.id_carrera = id_carrera));
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `sedesDisponiblesUsuario` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sedesDisponiblesUsuario`(IN id_usuario INT)
BEGIN
SELECT * from sedes s
 where (0=id_usuario or NOT EXISTS (select * from sede_usuario sc where sc.id_sede = s.id and sc.id_usuario = id_usuario));
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `sedesHabilitadas` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sedesHabilitadas`(IN id_carrera int)
BEGIN
 SELECT * from sedes s where 
 EXISTS (select * from sede_carrera sc where sc.id_sede = s.id and sc.id_carrera = id_carrera);
END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `sedesHabilitadasUsuario` */;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sedesHabilitadasUsuario`(IN id_usuario int)
BEGIN
 SELECT * from sedes s where 
 EXISTS (select * from sede_usuario sc where sc.id_sede = s.id and sc.id_usuario = id_usuario);
END ;;

DELIMITER ;


-- Dump completed on 2019-10-11 16:41:05
