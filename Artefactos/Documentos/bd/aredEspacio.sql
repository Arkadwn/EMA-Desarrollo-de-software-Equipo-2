-- MySQL dump 10.13  Distrib 5.5.51, for Win64 (x86)
--
-- Host: localhost    Database: aredEspacio
-- ------------------------------------------------------
-- Server version	5.5.51-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumnos` (
  `matricula` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `imagen` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`matricula`)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(400) NOT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaboradores`
--

DROP TABLE IF EXISTS `colaboradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colaboradores` (
  `idColaborador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `imagen` varchar(1000) DEFAULT NULL,
  `montoApagar` varchar(1000) DEFAULT NULL,
  `tipoPago` varchar(50) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idColaborador`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `colaboradores_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaboradores`
--

LOCK TABLES `colaboradores` WRITE;
/*!40000 ALTER TABLE `colaboradores` DISABLE KEYS */;
/*!40000 ALTER TABLE `colaboradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directores`
--

DROP TABLE IF EXISTS `directores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `directores` (
  `idDirector` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(400) NOT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `imagen` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idDirector`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `directores_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directores`
--

LOCK TABLES `directores` WRITE;
/*!40000 ALTER TABLE `directores` DISABLE KEYS */;
/*!40000 ALTER TABLE `directores` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupos` (
  `idGrupo` int(11) NOT NULL AUTO_INCREMENT,
  `idColaborador` int(11) DEFAULT NULL,
  `tipoDeBaile` varchar(30) DEFAULT NULL,
  `cupo` int(11) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `horario_asignado` int(1) DEFAULT NULL,
  PRIMARY KEY (`idGrupo`),
  KEY `idColaborador` (`idColaborador`),
  CONSTRAINT `grupos_ibfk_1` FOREIGN KEY (`idColaborador`) REFERENCES `colaboradores` (`idColaborador`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos`
--

LOCK TABLES `grupos` WRITE;
/*!40000 ALTER TABLE `grupos` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupos` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `inscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inscripciones` (
  `idInscripcion` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupo` int(11) DEFAULT NULL,
  `idAlumno` int(11) DEFAULT NULL,
  `fechaInscripcion` date DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `precioInscripcion` int(11) DEFAULT NULL,
  `pagoMensual` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInscripcion`),
  KEY `idAlumno` (`idAlumno`),
  KEY `idGrupo` (`idGrupo`),
  CONSTRAINT `inscripciones_ibfk_1` FOREIGN KEY (`idAlumno`) REFERENCES `alumnos` (`matricula`),
  CONSTRAINT `inscripciones_ibfk_2` FOREIGN KEY (`idGrupo`) REFERENCES `grupos` (`idGrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripciones`
--

LOCK TABLES `inscripciones` WRITE;
/*!40000 ALTER TABLE `inscripciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(30) NOT NULL,
  `contrasenia` varchar(100) NOT NULL,
  `tipo` int(1) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentas`
--

DROP TABLE IF EXISTS `rentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rentas` (
  `idRenta` int(11) NOT NULL AUTO_INCREMENT,
  `monto` int(11) DEFAULT NULL,
  `fecha` varchar(15) DEFAULT NULL,
  `horaIni` int(11) DEFAULT NULL,
  `horaFin` int(11) DEFAULT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idRenta`),
  KEY `idCliente` (`idCliente`),
  CONSTRAINT `rentas_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentas`
--

LOCK TABLES `rentas` WRITE;
/*!40000 ALTER TABLE `rentas` DISABLE KEYS */;
INSERT INTO `rentas` VALUES (6,15000,'2/06/2018',100,100,2,''),(7,16000,'7/12/2018',100,100,2,'');
/*!40000 ALTER TABLE `rentas` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-29  0:22:11

DROP TABLE IF EXISTS `egresosfacebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `egresosfacebook` (
  `idEgresoFacebook` int(11) NOT NULL AUTO_INCREMENT,
  `creador` varchar(200) DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `descripcion` varchar(40000) DEFAULT NULL,
  `link` varchar(10000) DEFAULT NULL,
  `costo` double(10,2) DEFAULT NULL,
  `activa` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idEgresoFacebook`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egresosfacebook`
--

LOCK TABLES `egresosfacebook` WRITE;
/*!40000 ALTER TABLE `egresosfacebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `egresosfacebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promociones`
--

DROP TABLE IF EXISTS `promociones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promociones` (
  `idPromocion` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_promocion` varchar(50) NOT NULL,
  `aplica_descuento` int(1) DEFAULT NULL,
  `porcentaje_descuento` varchar(3) DEFAULT NULL,
  `idColaborador` int(11) DEFAULT NULL,
  `fecha_ini` varchar(15) DEFAULT NULL,
  `fecha_fin` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`idPromocion`),
  KEY `idColaborador` (`idColaborador`),
  CONSTRAINT `promociones_ibfk_1` FOREIGN KEY (`idColaborador`) REFERENCES `colaboradores` (`idColaborador`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promociones`
--

LOCK TABLES `promociones` WRITE;
/*!40000 ALTER TABLE `promociones` DISABLE KEYS */;
/*!40000 ALTER TABLE `promociones` ENABLE KEYS */;
UNLOCK TABLES;

create user 'userAredEspacio'@'%' identified by 'limonhj2018';
Grant insert on aredEspacio.* to 'userAredEspacio'@'%';
Grant select on aredEspacio.* to 'userAredEspacio'@'%';
Grant delete on aredEspacio.* to 'userAredEspacio'@'%';
Grant update on aredEspacio.* to 'userAredEspacio'@'%';