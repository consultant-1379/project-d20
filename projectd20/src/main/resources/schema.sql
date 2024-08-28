DROP TABLE IF EXISTS `my_build`;

CREATE TABLE `my_build` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jobid` int NOT NULL,
  `number` int NOT NULL,
  `result` varchar(255) DEFAULT NULL,
  `time_created` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `my_job`;

CREATE TABLE `my_job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);