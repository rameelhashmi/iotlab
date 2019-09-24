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
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `sno` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `user_password` varchar(256) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`sno`, `unique_id`, `name`, `email`, `user_password`, `salt`, `created_at`) VALUES
(16, '5c3dc094e34990.80175175', 'rameel', 'rameelh@gmail.com', '$2y$10$m2LjKpOHUCvbNSn6NrddBOYYC/rK8.uMb/2UZ.2IdO0DTaR56HaB.', '5d49772e3a', '2019-01-15 12:14:28'),
(18, '5c3f2b84756b53.75768669', 'rameel', 'rameelhashmi@yahoo.com', '$2y$10$4QKlT3/N6/rKpBNnjAO/ie1pHWdxTW3qo0jMJYCmgR1U.bK.Leb6e', '49ab3402cc', '2019-01-16 14:03:00'),
(19, '5c51a056baa636.79845224', 'rameelhashmi', 'rameelhashmi@live.com', '$2y$10$Ra5Pyhtm.u5STpWCl9jOteYnuHv2pNRpAekOFWaLyruBtLcmAmQJ2', '8445e80d89', '2019-01-30 14:02:14');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`sno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
