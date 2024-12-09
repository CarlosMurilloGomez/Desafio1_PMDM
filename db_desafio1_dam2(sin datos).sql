-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-11-2024 a las 16:47:28
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_desafio1_dam2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bombardeos`
--

CREATE TABLE `bombardeos` (
  `idMision` int(11) NOT NULL,
  `objetivos` varchar(255) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caza`
--

CREATE TABLE `caza` (
  `idMision` int(11) NOT NULL,
  `objetivos` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`id`, `descripcion`) VALUES
(1, 'Preparada'),
(2, 'Conseguida'),
(3, 'Fallada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `experiencia`
--

CREATE TABLE `experiencia` (
  `id` int(11) NOT NULL,
  `limiteBajo` int(11) NOT NULL,
  `limiteAlto` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `experiencia`
--

INSERT INTO `experiencia` (`id`, `limiteBajo`, `limiteAlto`, `descripcion`) VALUES
(1, 0, 49, 'Novato'),
(2, 50, 99, 'Intermedio'),
(3, 100, 2147483647, 'Experto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mision`
--

CREATE TABLE `mision` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `exp` int(11) NOT NULL,
  `naveAsig` varchar(50) NOT NULL,
  `tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `misionasignacion`
--

CREATE TABLE `misionasignacion` (
  `id` int(11) NOT NULL,
  `idMision` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nave`
--

CREATE TABLE `nave` (
  `matricula` varchar(50) NOT NULL,
  `foto` varchar(2083) NOT NULL,
  `tipo` int(11) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Piloto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipomision`
--

CREATE TABLE `tipomision` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipomision`
--

INSERT INTO `tipomision` (`id`, `descripcion`) VALUES
(1, 'Vuelo'),
(2, 'Bombardeo'),
(3, 'Combate');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiponave`
--

CREATE TABLE `tiponave` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tiponave`
--

INSERT INTO `tiponave` (`id`, `descripcion`) VALUES
(1, 'Caza'),
(2, 'Bombardero'),
(3, 'Nave de transporte');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `foto` varchar(2083) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `experiencia` int(11) NOT NULL,
  `rol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `password`, `activo`, `foto`, `edad`, `experiencia`, `rol`) VALUES
(1, 'DarthVader', 'soyTuPadre', 1, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731165673/DarthVaderFotoPerfil_dgty49.jpg', 41, 1000000000, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelo`
--

CREATE TABLE `vuelo` (
  `idMision` int(11) NOT NULL,
  `duracion` int(11) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bombardeos`
--
ALTER TABLE `bombardeos`
  ADD PRIMARY KEY (`idMision`);

--
-- Indices de la tabla `caza`
--
ALTER TABLE `caza`
  ADD PRIMARY KEY (`idMision`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `experiencia`
--
ALTER TABLE `experiencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mision`
--
ALTER TABLE `mision`
  ADD PRIMARY KEY (`id`),
  ADD KEY `naveAsig` (`naveAsig`),
  ADD KEY `tipo` (`tipo`);

--
-- Indices de la tabla `misionasignacion`
--
ALTER TABLE `misionasignacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idMision` (`idMision`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `estado` (`estado`);

--
-- Indices de la tabla `nave`
--
ALTER TABLE `nave`
  ADD PRIMARY KEY (`matricula`),
  ADD KEY `tipo` (`tipo`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipomision`
--
ALTER TABLE `tipomision`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tiponave`
--
ALTER TABLE `tiponave`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `experiencia` (`experiencia`),
  ADD KEY `rol` (`rol`);

--
-- Indices de la tabla `vuelo`
--
ALTER TABLE `vuelo`
  ADD PRIMARY KEY (`idMision`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `experiencia`
--
ALTER TABLE `experiencia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `mision`
--
ALTER TABLE `mision`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `misionasignacion`
--
ALTER TABLE `misionasignacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipomision`
--
ALTER TABLE `tipomision`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tiponave`
--
ALTER TABLE `tiponave`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bombardeos`
--
ALTER TABLE `bombardeos`
  ADD CONSTRAINT `bombardeos_ibfk_1` FOREIGN KEY (`idMision`) REFERENCES `mision` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `caza`
--
ALTER TABLE `caza`
  ADD CONSTRAINT `caza_ibfk_1` FOREIGN KEY (`idMision`) REFERENCES `mision` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `mision`
--
ALTER TABLE `mision`
  ADD CONSTRAINT `mision_ibfk_1` FOREIGN KEY (`naveAsig`) REFERENCES `nave` (`matricula`) ON DELETE CASCADE,
  ADD CONSTRAINT `mision_ibfk_2` FOREIGN KEY (`tipo`) REFERENCES `tipomision` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `misionasignacion`
--
ALTER TABLE `misionasignacion`
  ADD CONSTRAINT `misionasignacion_ibfk_1` FOREIGN KEY (`idMision`) REFERENCES `mision` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `misionasignacion_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `misionasignacion_ibfk_3` FOREIGN KEY (`estado`) REFERENCES `estado` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `nave`
--
ALTER TABLE `nave`
  ADD CONSTRAINT `nave_ibfk_1` FOREIGN KEY (`tipo`) REFERENCES `tiponave` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `vuelo`
--
ALTER TABLE `vuelo`
  ADD CONSTRAINT `vuelo_ibfk_1` FOREIGN KEY (`idMision`) REFERENCES `mision` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
