-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 31, 2019 at 08:33 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iotlab`
--

-- --------------------------------------------------------

--
-- Table structure for table `androidusers`
--

CREATE TABLE `androidusers` (
  `sno` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `androidusers`
--

INSERT INTO `androidusers` (`sno`, `name`, `email`, `code`, `status`, `date`) VALUES
(21, 'rameel', 'rameelh@gmail.com', 'e00da03b685a0dd18fb6a08af0923de0', 1, '2019-01-15 11:13:46'),
(22, 'rameel', 'rameelhashmi@yahoo.com', '6883966fd8f918a4aa29be29d2c386fb', 1, '2019-01-16 12:58:30'),
(23, 'rameelhashmi', 'rameelhashmi@live.com', 'c45147dee729311ef5b5c3003946c48f', 1, '2019-01-30 12:34:30');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `androidusers`
--
ALTER TABLE `androidusers`
  ADD PRIMARY KEY (`sno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `androidusers`
--
ALTER TABLE `androidusers`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
