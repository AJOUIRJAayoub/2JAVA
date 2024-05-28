-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 21 fév. 2024 à 22:55
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `istore2.0`
--

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `inventory_id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `inventory_id` (`inventory_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `inventories`
--

DROP TABLE IF EXISTS `inventories`;
CREATE TABLE IF NOT EXISTS `inventories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `store_id` (`store_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `stores`
--

DROP TABLE IF EXISTS `stores`;
CREATE TABLE IF NOT EXISTS `stores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `stores`
--

INSERT INTO `stores` (`id`, `name`) VALUES
(1, 'Carrefour'),
(5, 'Intermarché'),
(6, 'Auchan'),
(8, 'Lidl'),
(9, 'Netto');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `pseudo` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('admin','employee') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'employee',
  `store_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `pseudo`, `password`, `role`, `store_id`) VALUES
(1, 'ayoub.ajouirja@supinfo.com', 'THEAYOUB49', '123456', 'admin', NULL),
(2, 'riad.ajouirja@supinfo.fr', 'RIAD', '123456', 'employee', 'Carrefour'),
(5, 'yanis.kherouni@supinfo.com', 'YANIS', '123456', 'employee', 'Carrefour'),
(6, 'anthony.vial@supinfo.com', 'ANTHONY', '123456', 'employee', NULL),
(7, 'hugo.beroujon@supinfo.com', 'HUGO', '123456', 'employee', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `whitelist`
--

DROP TABLE IF EXISTS `whitelist`;
CREATE TABLE IF NOT EXISTS `whitelist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `added_by` int DEFAULT NULL,
  `added_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `approved` tinyint(1) DEFAULT '0',
  `approved_by` int DEFAULT NULL,
  `approved_at` timestamp NULL DEFAULT NULL,
  `rejected_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `whitelist`
--

INSERT INTO `whitelist` (`id`, `email`, `added_by`, `added_at`, `approved`, `approved_by`, `approved_at`, `rejected_reason`) VALUES
(1, 'a', NULL, '2024-02-20 15:54:36', 1, NULL, NULL, NULL),
(2, 'ayoub.ajouirja@supinfo.com', NULL, '2024-02-20 15:54:36', 1, NULL, NULL, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
