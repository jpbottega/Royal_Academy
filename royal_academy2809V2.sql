-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: royal_academy
-- ------------------------------------------------------
-- Server version	8.0.17

-- Table structure for table `carrera_curso`
--

create database if not exists royal_academy;
use royal_academy;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
INSERT INTO `carreras` VALUES (1,'Sistemas'),(2,'Administración'),(3,'Nutrición');
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
INSERT INTO `curso_usuario` VALUES (4,1),(4,2);
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
  `fecha_examen` date DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1,'Seguridad Informatica',0,1),(2,'Administracion de Empresas',0,2),(3,'Microeconomia',0,2),(4,'Bases de Datos',0,1);
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
CREATE TABLE `examenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(40) DEFAULT NULL,
  `id_usuario_creador` int(11) DEFAULT NULL,
  `criterioAprobacion` decimal(10,0) DEFAULT NULL,
  `fechaCreacion` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
  `id_alumno` int(11) DEFAULT NULL,
  `entregado` tinyint(1) DEFAULT NULL,
  `aprobado` tinyint(1) DEFAULT NULL,
  `id_curso_examen` int(11) NOT NULL,
  `resultado` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
INSERT INTO `funciones` VALUES (1,'Logged Admin'),(2,'Logged Alumno'),(3,'Perfiles'),(4,'Usuarios'),(5,'Sedes'),(6,'Carrera'),(7,'Cursos'),(8,'Examenes'),(9,'Preguntas'),(10,'Admin'),(11,'Docente'),(12,'Alumno');
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
INSERT INTO `funciones_perfil` VALUES (1,'1'),(1,'13'),(1,'15'),(1,'3'),(2,'2'),(3,'1'),(4,'1'),(5,'1'),(6,'1'),(7,'1'),(8,'1'),(9,'1'),(10,'1'),(11,'15'),(12,'2');
/*!40000 ALTER TABLE `funciones_perfil` ENABLE KEYS */;
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
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `opciones_pregunta`
--

LOCK TABLES `opciones_pregunta` WRITE;
/*!40000 ALTER TABLE `opciones_pregunta` DISABLE KEYS */;
INSERT INTO `opciones_pregunta` VALUES (30,4,1,'Opcion');
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
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `preguntas`
--

LOCK TABLES `preguntas` WRITE;
/*!40000 ALTER TABLE `preguntas` DISABLE KEYS */;
INSERT INTO `preguntas` VALUES (4,'Esta es una nueva',1,1);
/*!40000 ALTER TABLE `preguntas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntaxexamen`
--

DROP TABLE IF EXISTS `preguntaxexamen`;
CREATE TABLE `preguntaxexamen` (
  `id_examen` int(11) DEFAULT NULL,
  `id_pregunta` int(11) DEFAULT NULL
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
INSERT INTO `sede_carrera` VALUES (1,2),(1,3),(1,4),(2,3),(3,2);
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
INSERT INTO `sede_usuario` VALUES (3,2),(3,3),(3,4);
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
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','Gonzalo','Berro','1535949261','berro.gonza2195@gmail.com','12345678',1,1,'1995-12-21'),(2,'admin','admin','admin','admin','admin','admin',1,1,'2019-09-23'),(3,'39594954','JUAN','BOTTEGA','1536880928','juanpbottega@gmail.com','39594954',0,2,'2019-09-16'),(5,'38856962','Milton','Echague','15452145','miltonyoelechague@gmail.com','38856962',1,1,'2019-09-27'),(6,'36826344','Nico','Gianni','42142525','nicolasgianni11@gmail.com','36826344',0,1,'2019-09-27'),(7,'elputi','Ignacio','Ledesma','1414141414','ledignacio@gmail.com','elputi',0,2,'2019-09-27');
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


-- Dump completed on 2019-09-28 22:13:11
