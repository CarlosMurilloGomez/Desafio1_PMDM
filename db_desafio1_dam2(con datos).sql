-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2024 a las 18:31:37
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

--
-- Volcado de datos para la tabla `bombardeos`
--

INSERT INTO `bombardeos` (`idMision`, `objetivos`, `carga`, `pasajeros`) VALUES
(4, '5', 1, 1),
(7, '7', 0, 0),
(9, '10', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caza`
--

CREATE TABLE `caza` (
  `idMision` int(11) NOT NULL,
  `objetivos` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `caza`
--

INSERT INTO `caza` (`idMision`, `objetivos`) VALUES
(8, '5'),
(11, '9'),
(12, '13');

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

--
-- Volcado de datos para la tabla `mision`
--

INSERT INTO `mision` (`id`, `nombre`, `exp`, `naveAsig`, `tipo`) VALUES
(1, 'Vuelo por Alderaan', 15, 'SH1100', 1),
(2, 'Vuelo por Endor', 20, 'SH0011', 1),
(4, 'Bombardeo base Endor', 40, 'BM1111', 2),
(6, 'Vuelo por Naboo', 20, 'SH0011', 1),
(7, 'Bombardeo base Naboo', 35, 'BM0000', 2),
(8, 'Combate en Naboo', 50, 'TI1111', 3),
(9, 'Bombardeo base Tatooine', 55, 'BM1100', 2),
(11, 'Combate en Tatooine', 90, 'TI2222', 3),
(12, 'Combate en Dagobah', 130, 'TI3333', 3);

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

--
-- Volcado de datos para la tabla `misionasignacion`
--

INSERT INTO `misionasignacion` (`id`, `idMision`, `idUsuario`, `estado`) VALUES
(1, 1, 3, 1),
(2, 1, 5, 1),
(3, 2, 7, 1),
(4, 4, 8, 1),
(5, 6, 4, 2),
(6, 4, 6, 1),
(7, 7, 7, 3),
(8, 9, 3, 1),
(9, 12, 8, 1),
(10, 1, 4, 1),
(11, 4, 7, 1),
(12, 7, 5, 2),
(13, 9, 8, 2),
(14, 6, 3, 3),
(15, 6, 3, 2),
(16, 12, 4, 1),
(17, 8, 4, 3),
(18, 4, 5, 1),
(19, 11, 5, 3),
(20, 4, 6, 1),
(21, 7, 6, 3),
(22, 1, 6, 2),
(23, 4, 7, 3);

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

--
-- Volcado de datos para la tabla `nave`
--

INSERT INTO `nave` (`matricula`, `foto`, `tipo`, `carga`, `pasajeros`) VALUES
('BM0000', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/TIE_Bomber_zzqmlf.png', 2, 0, 0),
('BM0011', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/TIE_Bomber_zzqmlf.png', 2, 0, 1),
('BM1100', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/TIE_Bomber_zzqmlf.png', 2, 1, 0),
('BM1111', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/TIE_Bomber_zzqmlf.png', 2, 1, 1),
('SH0000', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/shuttle_pi8nyj.jpg', 3, 0, 0),
('SH0011', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/shuttle_pi8nyj.jpg', 3, 0, 1),
('SH1100', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/shuttle_pi8nyj.jpg', 3, 1, 0),
('SH1111', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/shuttle_pi8nyj.jpg', 3, 1, 1),
('TI1111', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342951/TIE_fighter_u8fwd8.png', 1, 0, 0),
('TI2222', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342951/TIE_fighter_u8fwd8.png', 1, 0, 0),
('TI3333', 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342951/TIE_fighter_u8fwd8.png', 1, 0, 0);

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
(1, 'DarthVader', 'soyTuPadre', 1, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731165673/DarthVaderFotoPerfil_dgty49.jpg', 41, 1000000000, 1),
(3, 'Sheev_Palpatine', '1234', 1, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733765086/Desafio1/zmro6sexlrncwb4rxq6x.jpg', 80, 110, 2),
(4, 'Mas_Amedda', '1234', 0, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731946150/stormtrooperFotoPerfil_qv3hnw.jpg', 45, 56, 2),
(5, 'Rae_Sloane', '1234', 0, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731946150/stormtrooperFotoPerfil_qv3hnw.jpg', 42, 75, 2),
(6, 'Gallius_Rax', '1234', 0, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731946150/stormtrooperFotoPerfil_qv3hnw.jpg', 29, 42, 2),
(7, 'Jar_Jar', '1234', 1, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1733765443/Desafio1/tmqj7x14t66wiwqitvuv.jpg', 24, 0, 2),
(8, 'Brendol_Hux', '1234', 0, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731946150/stormtrooperFotoPerfil_qv3hnw.jpg', 35, 30, 2);

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
-- Volcado de datos para la tabla `vuelo`
--

INSERT INTO `vuelo` (`idMision`, `duracion`, `carga`, `pasajeros`) VALUES
(1, 40, 1, 0),
(2, 45, 0, 1),
(6, 25, 0, 1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `misionasignacion`
--
ALTER TABLE `misionasignacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
