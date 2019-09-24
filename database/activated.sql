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
-- Table structure for table `activated`
--

CREATE TABLE `activated` (
  `sno` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `email` varchar(80) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pass_sent` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activated`
--

INSERT INTO `activated` (`sno`, `name`, `email`, `created`, `pass_sent`) VALUES
(15, 'rameel', 'rameelh@gmail.com', '2019-01-15 11:14:03', 'Yes'),
(16, 'rameel', 'rameelhashmi@yahoo.com', '2019-01-16 12:59:48', 'Yes'),
(17, 'rameelhashmi', 'rameelhashmi@live.com', '2019-01-30 12:59:47', 'Yes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activated`
--
ALTER TABLE `activated`
  ADD PRIMARY KEY (`sno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activated`
--
ALTER TABLE `activated`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
