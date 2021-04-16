-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2021 at 07:33 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `epub_reader`
--
CREATE DATABASE IF NOT EXISTS `epub_reader` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `epub_reader`;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(300) NOT NULL,
  `author` varchar(300) NOT NULL,
  `image` text NOT NULL,
  `summary` longtext NOT NULL,
  `year` int(11) NOT NULL,
  `publisher` varchar(300) NOT NULL,
  `narrator` varchar(300) NOT NULL,
  `category` text NOT NULL,
  `epub` text NOT NULL,
  `audio` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `image`, `summary`, `year`, `publisher`, `narrator`, `category`, `epub`, `audio`) VALUES
(11, 'Големи и Мали', 'Бошко Смаќоски', 'uploads/Големи и Мали (Насловна).jpg', 'Авантурите на големите и малите на улицата „Караорманска“ во XX век.', 1966, 'Нова Македонија', 'Марио Крстевски-Липковски', 'Лектира', 'uploads/Големи и Мали.epub', 'uploads/Големи и Мали 01.mp3'),
(12, 'Шеќерна Приказна', 'Славко Јаневски', 'uploads/Насловна.jpg', 'Авантурите на шеќерното момче кое откако е направенод од слаткарот Марко, оди на прошетка низ светот.', 1952, 'Детска Радост', 'Марио Крстевски-Липковски', 'Лектира', 'uploads/Шеќерна Приказна.epub', 'uploads/Шеќерна Приказна.mp3'),
(13, 'Гоце Делчев', 'Ванчо Николески', 'uploads/Гоце Делчев (Насловна).jpg', 'Животот на највлијателниот македонски револуционер Гоце Делчев.', 1964, 'Нова Македонија', 'Марио Крстевски-Липковски', 'Лектира', 'uploads/Гоце Делчев.epub', 'uploads/Гоце Делчев 01.mp3'),
(14, 'Робинзон Крусо', 'Даниел Дефо', 'uploads/Робинсон Крусо (Насловна).png', 'Авантурите на Робинзон Крусо.', 1719, 'Вилијам Тејлор', 'Марио Крстевски-Липковски', 'Авантура', 'uploads/Робинсон Крусо.epub', 'uploads/Робинсон Крусо 01.mp3');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(300) NOT NULL,
  `fullname` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `fullname`) VALUES
(2, 'mario', '$2y$10$qc/j5ezRSHtj3sUQ1SkB.eFK4NcNcI0DbL7PRrxNMexRaJ2oyfIqO', 'me@home.com', 'Mario'),
(3, 'jimmy', '$2y$10$vWR5V7HxTDhLPCO6DXm/2OBWKNLlBk4s947u76IIsPZGtMNFx33am', 'me@home.net', 'Jimmy');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_name` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;