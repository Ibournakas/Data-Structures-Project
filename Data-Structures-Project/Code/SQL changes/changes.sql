-- Ayta yparxoun edw giati den mporeis na kaneis allages stous pinakes ama exartountai apo alloys opote prepei na kaneisdrop ta dependancies kai meta na kaneis allages

drop table if exists series_inventory;
drop table if exists series_info;
drop table if exists series_actor;
drop table if exists series_category;
drop table if exists series;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS administrator;
DROP TABLE IF EXISTS employees;


CREATE TABLE employees (

  employee_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(50) DEFAULT NULL,
  address_id SMALLINT UNSIGNED NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  create_date DATETIME NOT NULL,
  PRIMARY KEY  (employee_id),
  CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE

  )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- create table administrator
CREATE TABLE administrator (
  administrator_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  employee_id SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY  (administrator_id),
  CONSTRAINT fk_administrator_employee FOREIGN KEY (employee_id) REFERENCES employees (employee_id) ON DELETE RESTRICT ON UPDATE CASCADE
  )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- create table subscription
  CREATE TABLE subscription (
    subscription_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    subscription_type SMALLINT DEFAULT NULL,
    amount  DECIMAL(5,2) NOT NULL,
    customer_id SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY  (subscription_id),
    CONSTRAINT fk_subscription_costumer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE payment (
  payment_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id SMALLINT UNSIGNED NOT NULL,
  rental_id INT DEFAULT NULL,
  amount DECIMAL(5,2) NOT NULL,
  payment_date DATETIME NOT NULL,
  PRIMARY KEY  (payment_id),
  CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES rental (rental_id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_payment_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




CREATE TABLE series (
  series_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(128) NOT NULL,
  description TEXT DEFAULT NULL,
  release_year YEAR DEFAULT NULL,
  language_id TINYINT UNSIGNED NOT NULL,
  original_language_id TINYINT UNSIGNED DEFAULT NULL,
  length_per_episode SMALLINT UNSIGNED DEFAULT NULL,
  rating ENUM('G', 'TV-PG', 'TV-Y', 'TV-Y7', 'TV-14', 'TV-MA') DEFAULT 'G',
  special_features SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
  PRIMARY KEY  (series_id),
  CONSTRAINT fk_series_language FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_series_language_original FOREIGN KEY (original_language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE series_actor (
  series_id SMALLINT UNSIGNED NOT NULL,
  actor_id SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY  (series_id, actor_id),
  CONSTRAINT fk_series_actor_series FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_series_actor_actor FOREIGN KEY (actor_id) REFERENCES actor (actor_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE series_category (
  series_id SMALLINT UNSIGNED NOT NULL,
  category_id TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (series_id, category_id),
  CONSTRAINT fk_series_category_film FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_series_category_category FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE series_info (
    series_id SMALLINT UNSIGNED NOT NULL,
    total_episodes SMALLINT UNSIGNED NOT NULL,
    total_seasons SMALLINT UNSIGNED NOT NULL,
    episode_per_season SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (series_id),
    CONSTRAINT fk_series_info_series FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE RESTRICT ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- rename inventory table to film_inventory table


CREATE TABLE series_inventory (
  inventory_id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
  series_id SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY  (inventory_id),
  CONSTRAINT fk_series_inventory_series FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE RESTRICT ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


alter table rental add series_inventory_id MEDIUMINT UNSIGNED default NULL;
ALTER TABLE `rental` CHANGE `inventory_id` `film_inventory_id` MEDIUMINT(8) UNSIGNED NULL;
ALTER TABLE `rental` ADD CONSTRAINT `fk_rental_film_inventory` FOREIGN KEY (`film_inventory_id`) REFERENCES `film_inventory`(`inventory_id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE `rental` ADD CONSTRAINT `fk_rental_series_inventory` FOREIGN KEY (`series_inventory_id`) REFERENCES `series_inventory`(`inventory_id`) ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE data (
 id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
 timestamp TIMESTAMP,
 data1 VARCHAR(255) NOT NULL,
 data2 DECIMAL(5,2) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;  
    

INSERT INTO `employees` (`employee_id`, `first_name`, `last_name`, `email`, `address_id`, `active`, `create_date`) VALUES
(40, 'BILL', 'PETERSON', 'BILL.PETERSON@sakilaemployees.org', 108, 1, '2006-02-14 22:05:37'),
(65, 'JOHN', 'VEGA', 'JOHN.VEGA@sakilaemployees.org', 89, 0, '2006-02-14 22:05:36'),
(14, 'STEVE', 'GOMEZ', 'STEVE.GOMEZ@sakilaemployees.org', 56, 1, '2006-02-14 22:05:36'),
(44, 'MIKE', 'DANIELS', 'MIKE.DANIELS@sakilaemployees.org', 225, 1, '2006-02-14 22:05:36'),
(49, 'PHIL', 'IBARRA', 'PHIL.IBARRA@sakilaemployees.org', 314, 0, '2006-02-14 22:05:36'),
(92, 'SARAH', 'FRANKLIN', 'SARAH.FRANKLIN@sakilaemployees.org', 453, 1, '2006-02-14 22:05:37'),
(94, 'ANN', 'HUNT', 'ANN.HUNT@sakilaemployees.org', 146, 1, '2006-02-14 22:05:36'),
(24, 'MARIE', 'BARTLETT', 'MARIE.BARTLETT@sakilaemployees.org', 474, 1, '2006-02-14 22:05:36'),
(5, 'LIZ', 'SUTTON', 'LIZ.SUTTON@sakilaemployees.org', 20, 1, '2006-02-14 22:05:36'),
(7, 'STEPHANIE', 'RICE', 'STEPHANIE.RICE@sakilaemployees.org', 298, 1, '2006-02-14 22:05:36');

INSERT INTO `administrator` (`administrator_id`,`employee_id`) VALUES
(1,5);

INSERT INTO `subscription` (`subscription_id`,`subscription_type`,`amount`,`customer_id`) VALUES
(1,0,0.4,402),
(2,0,0.4,162),
(3,0,0.4,104),
(4,0,0.4,35),
(5,0,0.4,114),
(6,0,0.4,448),
(7,0,0.4,251),
(8,0,0.4,196),
(9,0,0.4,227),
(10,0,0.4,195),
(11,0,0.4,201),
(12,0,0.4,3),
(13,1,0.2,596),
(14,1,0.2,33 ),
(15,1,0.2,439),
(16,1,0.2,309),
(17,1,0.2,499),
(18,1,0.2,541),
(19,1,0.2,221),
(20,1,0.2,525),
(21,1,0.2,52 ),
(22,1,0.2,293),
(23,1,0.2,394),
(24,1,0.2,469),
(25,2,0.3,549),
(26,2,0.3,85 ),
(27,2,0.3,142),
(28,2,0.3,252),
(29,2,0.3,512),
(30,2,0.3,583),
(31,2,0.3,16 ),
(32,2,0.3,556),
(33,2,0.3,477),
(34,2,0.3,497);



INSERT INTO `series` (`series_id`, `title`, `description`, `release_year`, `language_id`, `original_language_id`, `length_per_episode`, `rating`, `special_features`) VALUES
(1, 'The Simpsons', 'The Simpsons is a satirical parody of the life of the late 20th century American family', 1989, 1, NULL, 25, 'TV-PG', 'Deleted Scenes'),
(2, 'Breaking Bad', 'A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his familys future.', 2008, 1, NULL, 49, 'TV-MA', 'Trailers,Deleted Scenes,Behind the Scenes'),
(3, 'The Big Bang Theory', 'The Big Bang Theory is a comedy about the lives of a group of college students and their shared interest in the same subject.', 2007, 1, NULL, 25, 'TV-PG', 'Behind the Scenes'),
(4, 'The Office', 'The Office is a mockumentary-like series about a group of office workers who are forced to deal with life-altering events in the workplace, including the end of the American government.', 2005, 1, NULL, 25, 'TV-PG', 'Behind the Scenes'),
(5, 'The Walking Dead', 'The Walking Dead is an American post-apocalyptic horror drama television series developed by Frank Darabont.', 2010, 1, NULL, 25, 'TV-MA', 'Behind the Scenes'),
(6, 'Chernobyl','In April 1986, an explosion at the Chernobyl nuclear power plant in the Union of Soviet Socialist Republics becomes one of the worlds worst man-made catastrophes', 2019,1,NULL, 65, 'TV-MA', 'Behind the Scenes'),
(7, 'Game Of Thrones','Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.', 2011, 1 ,NULL, 57, 'TV-MA', 'Behind the Scenes'),
(8, 'Rick and Morty','An animated series that follows the exploits of a super scientist and his not-so-bright grandson.', 2013, 1 ,NULL, 23, 'TV-MA', 'Behind the Scenes,Deleted Scenes'),
(9, 'The Last Dance','Charting the rise of the 1990s Chicago Bulls, led by Michael Jordan, one of the most notable dynasties in sports history.', 2020, 1 ,NULL, 50, 'TV-MA', 'Behind the Scenes'),
(10, 'Sherlock','A modern update finds the famous sleuth and his doctor partner solving crime in 21st-century London.', 2010, 1 ,NULL, 90, 'TV-14', 'Behind the Scenes'),
(11, 'Arcane','Set in utopian Piltover and the oppressed underground of Zaun, the story follows the origins of two iconic League champions-and the power that will tear them apart.', 2021, 1 ,NULL, 41, 'TV-14', 'Deleted Scenes'),
(12, 'The Flash','The Flash is an American superhero-superhero television series, created by DC Comics creator Bill Laimbeer and first broadcast on The CW on September 10, 2014.', 2014, 1 ,NULL, 45, 'TV-PG', 'Behind the Scenes'),
(13, 'True Detective','Seasonal anthology series in which police investigations unearth the personal and professional secrets of those involved, both within and outside the law.', 2014, 1 ,NULL, 55, 'TV-MA', 'Behind the Scenes'),
(14, 'Fargo','Various chronicles of deception, intrigue and murder in and around frozen Minnesota. Yet all of these tales mysteriously lead back one way or another to Fargo, North Dakota.', 2014, 1 ,NULL, 53, 'TV-MA', 'Behind the Scenes'),
(15, 'Friends','Follows the personal and professional lives of six twenty to thirty-something-year-old friends living in Manhattan.', 1994, 1 ,NULL, 22, 'TV-14', 'Behind the Scenes'),
(16, 'Better Call Saul','The trials and tribulations of criminal lawyer Jimmy McGill in the time before he established his strip-mall law office in Albuquerque, New Mexico as Saul Goodman.', 2015, 1 ,NULL, 46, 'TV-MA', 'Behind the Scenes'),
(17, 'Black Mirror','An anthology series exploring a twisted, high-tech multiverse where humanitys greatest innovations and darkest instincts collide.', 2011, 1 ,NULL, 60, 'TV-MA', 'Behind the Scenes'),
(18, 'Narcos','A chronicled look at the criminal exploits of Colombian drug lord Pablo Escobar, as well as the many other drug kingpins who plagued the country through the years.', 2015, 1 ,NULL, 49, 'TV-MA', 'Behind the Scenes'),
(19, 'BoJack Horseman','BoJack Horseman was the star of the hit television show Horsin Around in the 80s and 90s, but now hes washed up, living in Hollywood, complaining about everything, and wearing colorful sweaters.', 2014, 1 ,NULL, 25, 'TV-MA', 'Behind the Scenes'),
(20, 'Succession','The Roy family is known for controlling the biggest media and entertainment company in the world. However, their world changes when their father steps down from the company.', 2018, 1 ,NULL, 60, 'TV-MA', 'Behind the Scenes'),
(21, 'Peaky Blinders','A gangster family epic set in 1900s England, centering on a gang who sew razor blades in the peaks of their caps, and their fierce boss Tommy Shelby.', 2013, 1 ,NULL, 60, 'TV-MA', 'Behind the Scenes'),
(22, 'The Mandalorian','The travels of a lone bounty hunter in the outer reaches of the galaxy, far from the authority of the New Republic.', 2019, 1 ,NULL, 40, 'TV-14', 'Behind the Scenes'),
(23, 'The Boys','A group of vigilantes set out to take down corrupt superheroes who abuse their superpowers.', 2019, 1 ,NULL, 60, 'TV-MA', 'Behind the Scenes'),
(24, 'House M.D.','An antisocial maverick doctor who specializes in diagnostic medicine does whatever it takes to solve puzzling cases that come his way using his crack team of doctors and his wits.', 2015, 1 ,NULL, 44, 'TV-PG', 'Behind the Scenes'),
(25, 'Stranger Things','When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces in order to get him back.', 2016, 1 ,NULL, 51, 'TV-14', 'Behind the Scenes'),
(26, 'Mad Men','A drama about one of New Yorks most prestigious ad agencies at the beginning of the 1960s, focusing on one of the firms most mysterious but extremely talented ad executives, Donald Draper.', 2007, 1 ,NULL, 47, 'TV-MA', 'Behind the Scenes');



INSERT INTO actor VALUES 
(10011,'DAN','CASTELLANETA'),
(10012,'BRYAN','CRANSTON'),
(10013,'JIM','PARSONS'),
(10014,'KALEY','CUOCO'),
(10015,'STEVE','CARELL'),
(10016,'NORMAN','REEDUS'),
(10017,'JARED','HARRIS'),
(10018,'EMILIA','CLARKE'),
(10019,'SOPHIE','TURNER'),
(10020,'JUSTIN','ROILAND'),
(10021,'MICHAEL','JORDAN'),
(10022,'BENEDICT','CUMBERBATCH'),
(10023,'HAILEE','STEINFELD'),
(10024,'GRANT','GUSTIN'),
(10025,'ALEXANDRA','DADDARIO'),
(10026,'MATTHEW','MCCONAUGHEY'),
(10027,'MARTIN','FREEMAN'),
(10028,'JENNIFER','ANISTON'),
(10029,'BOB','ODENKIRK'),
(10030,'HAYLEY','ATWELL'),
(10031,'WAGNER','MOURA'),
(10032,'AARON','PAUL'),
(10033,'JEREMY','STRONG'),
(10034,'CILLIAN','MURPHY'),
(10035,'PEDRO','PASCAL'),
(10036,'ANTONY','STARR'),
(10037,'HUGH','LAURIE'),
(10038,'FINN','WOLFHARD'),
(10039,'JON','HAMM'),
(10040,'JANUARY','JONES');







INSERT INTO `series_actor` (`actor_id`, `series_id`) VALUES
(10011,1),
(10012,2),
(10032,2),
(10013,3),
(10014,3),
(10015,4),
(10016,5),
(10017,6),
(10018,7),
(10019,7),
(10020,8),
(10021,9),
(10022,10),
(10027,10),
(10023,11),
(10024,12),
(10025,13),
(10026,13),
(10027,14),
(10028,15),
(10029,16),
(10030,17),
(10031,18),
(10032,19),
(10033,20),
(10034,21),
(10035,22),
(10036,23),
(10037,24),
(10038,25),
(10039,26),
(10040,26);

INSERT INTO `series_category` (`series_id`, `category_id`) VALUES
(1,2),
(2,7),
(3,5),
(4,5),
(5,11),
(6,7),
(7,7),
(8,2),
(9,6),
(10,7),
(11,2),
(12,14),
(13,7),
(14,5),
(15,5),
(16,7),
(17,14),
(18,7),
(19,2),
(20,5),
(21,7),
(22,14),
(23,1),
(24,7),
(25,7),
(26,7);

INSERT INTO `series_inventory` (`inventory_id`, `series_id`) VALUES
(7,1),
(8,2),
(9,3),
(10,4),
(11,5),
(12,6),
(13,7),
(14,8),
(15,9),
(16,10),
(17,11),
(18,12),
(19,13),
(20,14),
(21,15),
(22,16),
(23,17),
(24,18),
(25,19),
(26,20),
(27,21),
(28,22),
(29,23),
(30,24),
(31,25),
(32,26);


INSERT INTO `rental` (`rental_id`, `rental_date`, `film_inventory_id`, `customer_id`, `series_inventory_id`) VALUES
(0,     '2022-08-27 16:51:10' ,1323,  448, NULL),
(0,     '2022-08-27 16:51:11' ,3319,  35, NULL),
(0,     '2022-08-27 16:51:12' ,1323,  251, NULL),
(0,     '2022-08-27 16:51:13' ,2807,  583, NULL),
(0,     '2022-08-27 16:51:14' ,2601,  477, NULL),
(0,     '2022-08-27 16:51:15' ,2601,  114, NULL),
(0,     '2022-08-27 16:51:16' ,1323,  104, NULL),
(0,     '2022-08-27 16:51:17' ,3456,  402, NULL),
(0,     '2022-08-27 16:51:18' ,3840,  3, NULL),
(0,     '2022-08-27 16:51:19' ,2220,  85, NULL),
(0,     '2022-08-27 16:51:20' ,361,  85, NULL),
(0,     '2022-08-27 16:51:21' ,507,  35, NULL),
(0,     '2022-08-27 16:51:22' ,2220,  251, NULL),
(0,     '2022-08-27 16:51:23' ,1061,  583, NULL),
(0,     '2022-08-27 16:51:24' ,1557,  477, NULL),
(0,     '2022-08-27 16:51:25' ,2601,  16, NULL),
(0,     '2022-08-27 16:51:26' ,1324,  85, NULL),
(0,     '2022-08-27 16:51:27' ,2225,  497, NULL),
(0,     '2022-08-27 16:51:28' ,3840,  252, NULL),
(0,     '2022-08-27 16:51:29' ,2220,  195, NULL),
(0,     '2022-08-27 16:51:30' ,NULL,  52, 9),
(0,     '2022-08-27 16:51:31' ,NULL,  293, 15),
(0,     '2022-08-27 16:51:32' ,NULL,  221, 10),
(0,     '2022-08-27 16:51:33' ,NULL,  16, 21),
(0,     '2022-08-27 16:51:34' ,NULL,  556, 24),
(0,     '2022-08-27 16:51:35' ,NULL,  85, 18),
(0,     '2022-08-27 16:51:36' ,NULL,  549, 15),
(0,     '2022-08-27 16:51:37' ,NULL,  142, 10),
(0,     '2022-08-27 16:51:38' ,NULL,  497, 10),
(0,     '2022-08-27 16:51:39' ,NULL,  33, 24),
(0,     '2022-08-27 16:51:40' ,NULL,  52, 20),
(0,     '2022-08-27 16:51:41' ,NULL,  394, 21),
(0,     '2022-08-27 16:51:42' ,NULL,  142, 22),
(0,     '2022-08-27 16:51:43' ,NULL,  549, 21),
(0,     '2022-08-27 16:51:44' ,NULL,  497, 24),
(0,     '2022-08-27 16:51:45' ,NULL,  469, 9),
(0,     '2022-08-27 16:51:46' ,NULL,  52, 10),
(0,     '2022-08-27 16:51:47' ,NULL,  512, 7),
(0,     '2022-08-27 16:51:48' ,NULL,  583, 11),
(0,     '2022-08-27 16:51:4' ,NULL,  33, 17),
(16575, '2005-06-15 01:25:08' , NULL, 596, 7),
(16576, '2005-06-16 01:25:08' , NULL, 33 , 8),
(16577, '2005-06-16 01:25:08' , NULL, 439, 9),
(16578, '2005-06-17 01:25:08' , NULL, 309, 10),
(16579, '2005-06-17 01:25:08' , NULL, 499, 11),
(16580, '2005-06-15 01:25:08' , NULL, 541, 12),
(16581, '2005-06-17 01:25:08' , NULL, 221, 13),
(16582, '2005-06-17 01:25:08' , NULL, 525, 14),
(16583, '2005-06-15 01:25:08' , NULL, 52 , 15),
(16584, '2005-06-19 01:25:08' , NULL, 293, 16),
(16585, '2005-06-20 01:25:08' , NULL, 394, 17),
(16586, '2005-06-15 01:25:08' , NULL, 469, 18),
(16587, '2005-06-17 01:25:08' , NULL, 549, 19),
(16588, '2005-06-16 01:25:08' , NULL, 85 , 20),
(16589, '2005-06-16 01:25:08' , NULL, 142, 21),
(16590, '2005-06-17 01:25:08' , NULL, 252, 22),
(16591, '2005-06-19 01:25:08' , NULL, 512, 23),
(16592, '2005-06-19 01:25:08' , NULL, 583, 24),
(16593, '2005-06-20 01:25:08' , NULL, 16 , 25),
(16594, '2005-06-15 01:25:08' , NULL, 556, 26),
(16595, '2005-06-22 01:25:08' , NULL, 477, 27),
(16596, '2005-06-20 01:25:08' , NULL, 497, 28),
(16597, '2005-06-29 01:25:08' , NULL, 596, 29),
(16598, '2005-06-27 01:25:08' , NULL, 33 , 30),
(16599, '2005-07-02 01:25:08' , NULL, 439, 31),
(16600, '2005-07-02 01:25:08' , NULL, 309, 32),
(16601, '2005-06-17 01:25:08' , NULL, 499, 31),
(16602, '2005-06-17 01:25:08' , NULL, 541, 31),
(16603, '2005-06-19 01:25:08' , NULL, 221, 31),
(16604, '2005-06-20 01:25:08' , NULL, 525, 31),
(16605, '2005-06-15 01:25:08' , NULL, 52 , 31),
(16606, '2005-06-20 01:25:08' , NULL, 293, 31),
(16607, '2005-06-15 01:25:08' , NULL, 394, 29),
(16608, '2005-06-23 01:25:08' , NULL, 469, 29),
(16609, '2005-06-22 01:25:08' , NULL, 549, 29),
(16610, '2005-06-21 01:25:08' , NULL, 85 , 29),
(16611, '2005-06-22 01:25:08' , NULL, 142, 27),
(16612, '2005-06-20 01:25:08' , NULL, 252, 27),
(16613, '2005-06-15 01:25:08' , NULL, 512, 27),
(16614, '2005-06-24 01:25:08' , NULL, 583, 27),
(16615, '2005-06-26 01:25:08' , NULL, 16 , 27),
(16616, '2005-06-29 01:25:08' , NULL, 556, 22),
(16617, '2005-07-01 01:25:08' , NULL, 477, 22),
(16618, '2005-06-01 01:25:08' , NULL, 497, 13),
(16619, '2005-07-02 01:25:08' , NULL, 596, 7),
(16620, '2005-07-03 01:25:08' , NULL, 33 , 7),
(16621, '2005-07-04 01:25:08' , NULL, 439, 9),
(16622, '2005-07-05 01:25:08' , NULL, 309, 10),
(16623, '2005-07-06 01:25:08' , NULL, 499, 15),
(16624, '2005-07-07 01:25:08' , NULL, 541, 15),
(16625, '2005-07-08 01:25:08' , NULL, 221, 13),
(16626, '2005-07-09 01:25:08' , NULL, 525, 13),
(16627, '2005-07-10 01:25:08' , NULL, 52 , 14),
(16628, '2005-07-11 01:25:08' , NULL, 293, 14),
(16629, '2005-07-12 01:25:08' , NULL, 394, 14),
(16630, '2005-07-13 01:25:08' , NULL, 469, 15),
(16631, '2005-08-04 01:25:08' , NULL, 549, 17),
(16632, '2005-08-05 01:25:08' , NULL, 85 , 17),
(16633, '2005-08-06 01:25:08' , NULL, 142, 17),
(16634, '2005-08-07 01:25:08' , NULL, 252, 18),
(16635, '2005-08-08 01:25:08' , NULL, 512, 19),
(16636, '2005-08-09 01:25:08' , NULL, 583, 19),
(16637, '2005-08-10 01:25:08' , NULL, 16 , 22),
(16638, '2005-08-11 01:25:08' , NULL, 556, 22),
(16639, '2005-08-12 01:25:08' , NULL, 477,  24),
(16640, '2005-08-05 01:25:08' , NULL, 497, 24),
(16641, '2005-08-06 01:25:08' , NULL, 596, 26),
(16642, '2005-08-07 01:25:08' , NULL, 33 , 25),
(16643, '2005-08-08 01:25:08' , NULL, 439, 27),
(16644, '2005-08-09 01:25:08' , NULL, 309, 13),
(16645, '2005-08-10 01:25:08' , NULL, 499, 12),
(16646, '2005-08-11 01:25:08' , NULL, 541, 29),
(16647, '2005-08-12 01:25:08' , NULL, 221,  30),
(16648, '2005-08-15 01:25:08' , NULL, 525, 14),
(16649, '2005-08-19 01:25:08' , NULL, 52 , 24),
(16650, '2005-08-22 01:25:08' , NULL, 293, 24),
(16651, '2005-08-25 01:25:08' , NULL, 394,  21),
(16652, '2005-08-29 01:25:08' , NULL, 469, 21),
(16653, '2005-08-30 01:25:08' , NULL, 549, 29),
(16654, '2005-09-02 01:25:08' , NULL, 85 , 26),
(16655, '2005-09-03 01:25:08' , NULL, 142, 27),
(16656, '2005-09-03 01:25:08' , NULL, 252, 11),
(16657, '2005-09-05 01:25:08' , NULL, 512, 10),
(16658, '2005-09-07 01:25:08' , NULL, 583, 31),
(16659, '2005-09-12 01:25:08' , NULL, 16 ,  21),
(16660, '2005-09-15 01:25:08' , NULL, 556, 21),
(16661, '2005-09-17 01:25:08' , NULL, 477, 29),
(16662, '2005-09-18 01:25:08' , NULL, 497, 17),
(16663, '2005-09-20 01:25:08' , NULL, 596, 19),
(16664, '2005-09-21 01:25:08' , NULL, 33 , 30),
(16665, '2005-09-28 01:25:08' , NULL, 439,  24),
(16666, '2005-10-02 01:25:08' , NULL, 309, 23),
(16667, '2005-10-03 01:25:08' , NULL, 499, 25),
(16668, '2005-10-03 01:25:08' , NULL, 541, 12),
(16669, '2005-10-05 01:25:08' , NULL, 221, 16),
(16670, '2005-10-07 01:25:08' , NULL, 525, 31),
(16671, '2005-10-12 01:25:08' , NULL, 52 , 23),
(16672, '2005-10-15 01:25:08' , NULL, 293, 29),
(16673, '2005-10-17 01:25:08' , NULL, 394, 23),
(16674, '2005-10-18 01:25:08' , NULL, 469, 17),
(16675, '2005-10-20 01:25:08' , NULL, 549, 12),
(16676, '2005-10-21 01:25:08' , NULL, 85 , 31),
(16677, '2005-10-28 01:25:08' , NULL, 142, 28),
(16678, '2005-11-02 01:25:08' , NULL, 252, 23),
(16679, '2005-11-03 01:25:08' , NULL, 512, 25),
(16680, '2005-11-03 01:25:08' , NULL, 583, 12),
(16681, '2005-11-05 01:25:08' , NULL, 16 , 16),
(16682, '2005-11-07 01:25:08' , NULL, 556, 31),
(16683, '2005-11-12 01:25:08' , NULL, 477, 23),
(16684, '2005-11-15 01:25:08' , NULL, 497, 29),
(16685, '2005-11-17 01:25:08' , NULL, 541, 23),
(16686, '2005-11-18 01:25:08' , NULL, 221, 17),
(16687, '2005-11-20 01:25:08' , NULL, 525, 12),
(16688, '2005-11-21 01:25:08' , NULL, 52 , 31),
(16689, '2005-11-28 01:25:08' , NULL, 293,  28),
(16690, '2005-12-02 01:25:08' , NULL, 394, 21),
(16691, '2005-12-03 01:25:08' , NULL, 469, 28),
(16692, '2005-12-03 01:25:08' , NULL, 549, 16),
(16693, '2005-12-05 01:25:08' , NULL, 85 , 14),
(16694, '2005-12-07 01:25:08' , NULL, 142, 22),
(16695, '2005-12-12 01:25:08' , NULL, 252, 27),
(16696, '2005-12-15 01:25:08' , NULL, 512, 28),
(16697, '2005-12-17 01:25:08' , NULL, 583, 22),
(16698, '2005-12-18 01:25:08' , NULL, 16 , 14),
(16699, '2005-12-20 01:25:08' , NULL, 556, 18),
(16700, '2005-12-21 01:25:08' , NULL, 477, 30),
(16701, '2005-12-28 01:25:08' , NULL, 497,  23);

INSERT INTO `payment` (`payment_id`, `customer_id`, `rental_id`, `amount`, `payment_date`) VALUES
(0, 596, 16575, '0.2', '2005-07-20 01:25:08'),
(0, 33 , 16576, '0.2', '2005-07-20 01:25:08'),
(0, 439, 16577, '0.2', '2005-07-20 01:25:08'),
(0, 309, 16578, '0.2', '2005-07-20 01:25:08'),
(0, 499, 16579, '0.2', '2005-07-20 01:25:08'),
(0, 541, 16580, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16581, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16582, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16583, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16584, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16585, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16586, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16587, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16588, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16589, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16590, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16591, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16592, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16593, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16594, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16595, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16596, '0.1', '2005-07-20 01:25:08'),
(0, 596, 16597, '0.2', '2005-07-20 01:25:08'),
(0, 33 , 16598, '0.2', '2005-07-20 01:25:08'),
(0, 439, 16599, '0.2', '2005-07-20 01:25:08'),
(0, 309, 16600, '0.2', '2005-07-20 01:25:08'),
(0, 499, 16601, '0.2', '2005-07-20 01:25:08'),
(0, 541, 16602, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16603, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16604, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16605, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16606, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16607, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16608, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16609, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16610, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16611, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16612, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16613, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16614, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16615, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16616, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16617, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16618, '0.1', '2005-07-20 01:25:08'),
(0, 596, 16619, '0.2', '2005-07-20 01:25:08'),
(0, 33 , 16620, '0.2', '2005-07-20 01:25:08'),
(0, 439, 16621, '0.2', '2005-07-20 01:25:08'),
(0, 309, 16622, '0.2', '2005-07-20 01:25:08'),
(0, 499, 16623, '0.2', '2005-07-20 01:25:08'),
(0, 541, 16624, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16625, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16626, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16627, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16628, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16629, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16630, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16631, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16632, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16633, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16634, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16635, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16636, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16637, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16638, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16639, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16640, '0.1', '2005-07-20 01:25:08'),
(0, 596, 16641, '0.2', '2005-07-20 01:25:08'),
(0, 33 , 16642, '0.2', '2005-07-20 01:25:08'),
(0, 439, 16643, '0.2', '2005-07-20 01:25:08'),
(0, 309, 16644, '0.2', '2005-07-20 01:25:08'),
(0, 499, 16645, '0.2', '2005-07-20 01:25:08'),
(0, 541, 16646, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16647, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16648, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16649, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16650, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16651, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16652, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16653, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16654, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16655, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16656, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16657, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16658, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16659, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16660, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16661, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16662, '0.1', '2005-07-20 01:25:08'),
(0, 596, 16663, '0.2', '2005-07-20 01:25:08'),
(0, 33 , 16664, '0.2', '2005-07-20 01:25:08'),
(0, 439, 16665, '0.2', '2005-07-20 01:25:08'),
(0, 309, 16666, '0.2', '2005-07-20 01:25:08'),
(0, 499, 16667, '0.2', '2005-07-20 01:25:08'),
(0, 541, 16668, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16669, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16670, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16671, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16672, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16673, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16674, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16675, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16676, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16677, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16678, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16679, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16680, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16681, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16682, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16683, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16684, '0.1', '2005-07-20 01:25:08'),
(0, 541, 16685, '0.2', '2005-07-20 01:25:08'),
(0, 221, 16686, '0.2', '2005-07-20 01:25:08'),
(0, 525, 16687, '0.2', '2005-07-20 01:25:08'),
(0, 52 , 16688, '0.2', '2005-07-20 01:25:08'),
(0, 293, 16689, '0.2', '2005-07-20 01:25:08'),
(0, 394, 16690, '0.2', '2005-07-20 01:25:08'),
(0, 469, 16691, '0.2', '2005-07-20 01:25:08'),
(0, 549, 16692, '0.1', '2005-07-20 01:25:08'),
(0, 85 , 16693, '0.1', '2005-07-20 01:25:08'),
(0, 142, 16694, '0.1', '2005-07-20 01:25:08'),
(0, 252, 16695, '0.1', '2005-07-20 01:25:08'),
(0, 512, 16696, '0.1', '2005-07-20 01:25:08'),
(0, 583, 16697, '0.1', '2005-07-20 01:25:08'),
(0, 16 , 16698, '0.1', '2005-07-20 01:25:08'),
(0, 556, 16699, '0.1', '2005-07-20 01:25:08'),
(0, 477, 16700, '0.1', '2005-07-20 01:25:08'),
(0, 497, 16701, '0.1', '2005-07-20 01:25:08');



INSERT INTO `series_info` (`series_id`, `total_episodes`, `total_seasons`, `episode_per_season`) VALUES
(1,728,34,21),
(2,62,5,12),
(3,279,12,23),
(4,201,9,22),
(5,147,10,15),
(6,5,1,5),
(7,73,8,9),
(8,51,5,10),
(9,10,1,10),
(10,13,4,3),
(11,9,1,9),
(12,171,8,21),
(13,24,3,8),
(14,20,4,5),
(15,236,10,23),
(16,61,6,10),
(17,22,5,4),
(18,30,3,10),
(19,77,6,13),
(20,20,3,7),
(21,36,6,6),
(22,16,2,8),
(23,24,3,8),
(24,177,8,22),
(25,34,4,8),
(26,92,7,13);


DROP PROCEDURE IF EXISTS TopSellers;
DELIMITER $
CREATE PROCEDURE TopSellers(choice VARCHAR(), num INT,startDate DATETIME,endDate DATETIME)
BEGIN
  
	if choice='m'
    begin
      select

	SELECT money;
END$
DELIMITER ;

-- DROP PROCEDURE IF EXISTS MostRented;
-- DELIMITER $
-- CREATE PROCEDURE MostRented(num INT,startDate DATE,endDate DATE)
-- BEGIN
  
--   SELECT film_inventory_id,count(film_inventory_id) as count
--   FROM rental
--   WHERE rental_date between startDate and endDate
--   GROUP BY film_inventory_id
--   ORDER BY count DESC
--   LIMIT num;
-- END$
-- DELIMITER ;

CREATE TABLE rental (
  rental_id INT NOT NULL AUTO_INCREMENT,
  rental_date DATETIME NOT NULL,
  film_inventory_id MEDIUMINT UNSIGNED default NULL,
  series_inventory_id MEDIUMINT UNSIGNED default NULL,
  customer_id SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (rental_id),
  UNIQUE KEY  (rental_date,inventory_id,customer_id),
  CONSTRAINT fk_rental_inventory FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--create procedure that finds the films that were rented the most from thier id in rental 

DROP PROCEDURE IF EXISTS MostRented;
DELIMITER $
CREATE PROCEDURE MostRented( choice CHAR, num INT,startDate DATETIME,endDate DATEtime)
BEGIN

  if(choice='m')
  THEN
    select title,film.film_id,count(*) as num_rented from rental inner join  film_inventory on film_inventory_id = inventory_id inner join film on film_inventory.film_id = film.film_id where rental_date between startDate and endDate group by film_inventory_id order by num_rented desc limit num;
  end if;
  if (choice='s')
  then
    select title,series.series_id,count(*) as num_rented from rental inner join series_inventory on series_inventory_id = inventory_id inner join series on series_inventory.series_id = series.series_id where rental_date between startDate and endDate group by series_inventory_id order by num_rented desc limit num;

  end if;

END$
DELIMITER ;
--ΚΟΙΤΑΞΩ ΜΕΤΑ ΑΜΑ ΒΑΛΩ ΤΟ COUNT ΝΑ ΦΑΙΝΕΤΑΙ ΣΑΝ ΑΠΟΤΕΛΕΣΜΑ Η ΟΧΙ 
call MostRented('m',3,'2005-07-05', '2005-07-30');
call MostRented('s',3,'2005-07-05', '2005-07-30');


--create procedure that given the email of the customer and a date it shows the number of rentals that customer has made on that date

  DROP PROCEDURE IF EXISTS CustomerRental;
  DELIMITER $
  CREATE PROCEDURE CustomerRental(email VARCHAR(255),date_1 datetime)
  BEGIN
    
    select count(rental_id) as num_rented from rental inner join customer on rental.customer_id=customer.customer_id where  DATEDIFF(date_1,rental_date)=0  and email=customer.email;
  END$
  DELIMITER ;

  call CustomerRental('JULIE.SANCHEZ@sakilacustomer.org','2005-06-15');


-- create procedure shows the count of rental_id for each month given a date


DROP PROCEDURE IF EXISTS Revenue;
DELIMITER $
CREATE PROCEDURE Revenue(date_1 datetime)
BEGIN
  declare films_only_sub decimal(10,2);
  declare series_only_sub decimal(10,2);
  declare series_on_movies_and_series_sub decimal(10,2);
  declare films_on_movies_and_series_sub decimal(10,2);

  declare series_revenue  decimal(10,2);
  declare films_revenue  decimal(10,2);

  select count(*) into films_only_sub from rental inner join customer on rental.customer_id=customer.customer_id inner join subscription on customer.customer_id=subscription.customer_id where DATE_FORMAT(rental_date,'%Y-%m')=DATE_FORMAT(date_1,'%Y-%m') and subscription_type=0;
  select count(*) into series_only_sub from rental inner join customer on rental.customer_id=customer.customer_id inner join subscription on customer.customer_id=subscription.customer_id where DATE_FORMAT(rental_date,'%Y-%m')=DATE_FORMAT(date_1,'%Y-%m') and subscription_type=1;
  select count(series_inventory_id) into series_on_movies_and_series_sub from rental inner join customer on rental.customer_id=customer.customer_id inner join subscription on customer.customer_id=subscription.customer_id where DATE_FORMAT(rental_date,'%Y-%m')=DATE_FORMAT(date_1,'%Y-%m') and subscription_type=2;
  select count(film_inventory_id) into films_on_movies_and_series_sub from rental inner join customer on rental.customer_id=customer.customer_id inner join subscription on customer.customer_id=subscription.customer_id where DATE_FORMAT(rental_date,'%Y-%m')=DATE_FORMAT(date_1,'%Y-%m') and subscription_type=2;
  
  select monthname(date_1) as month,0.2*series_only_sub + 0.1*series_on_movies_and_series_sub as series_revenue, 0.3*films_on_movies_and_series_sub + 0.4*films_only_sub as films_revenue;
END$
DELIMITER ;

call Revenue('2005-07-10');


CREATE INDEX ind_1 ON actor(last_name);  

--create procedure that prints all the last names of the actors between two string inputs

DROP PROCEDURE IF EXISTS NameSpace;
DELIMITER $
CREATE PROCEDURE NameSpace(name_1 VARCHAR(255),name_2 VARCHAR(255))
BEGIN
declare num int;
  select first_name,last_name from actor where last_name between name_1 and name_2;
  select count(*) into num from actor where last_name between name_1 and name_2;
  select num as count;
END$
DELIMITER ;

call NameSpace('Ab','Ale');

--create procedure that prints all the firt_names of the actors with a last name that is  input
DROP PROCEDURE IF EXISTS NameFind;
DELIMITER $
CREATE PROCEDURE NameFind(name_1 VARCHAR(255))
BEGIN
declare num int;
  select first_name,last_name from actor where last_name=name_1;
  select count(*) into num from actor where last_name=name_1;
  select num as count;
END$
DELIMITER ;

call NameFind('Abbott');


CREATE TABLE data_log (
   action VARCHAR(255),
   action_time   TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
   user mediumtext  NULL,
   success BOOLEAN,
   table_name VARCHAR(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;  


DELIMITER $$
CREATE TRIGGER bi_rental BEFORE INSERT ON rental FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('insert', NOW(), user(), FALSE,'rental');
END$$

DELIMITER $$
CREATE TRIGGER ai_rental AFTER INSERT ON rental FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'insert' AND table_name = 'rental';
END$$

DELIMITER $$
CREATE TRIGGER bu_rental BEFORE UPDATE ON rental FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('update', NOW(), user(), FALSE,'rental');
END$$

DELIMITER $$
CREATE TRIGGER au_rental AFTER UPDATE ON rental FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'update' AND table_name = 'rental';
END$$

DELIMITER $$
CREATE TRIGGER bd_rental BEFORE DELETE ON rental FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('delete', NOW(), user(), FALSE,'rental');
END$$

DELIMITER $$
CREATE TRIGGER ad_rental AFTER DELETE ON rental FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'delete' AND table_name = 'rental';
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER bi_payment BEFORE INSERT ON payment FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('insert', NOW(), user(), FALSE,'payment');
END$$

DELIMITER $$
CREATE TRIGGER ai_payment AFTER INSERT ON payment FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'insert' AND table_name = 'payement';
END$$

DELIMITER $$
CREATE TRIGGER bu_payment BEFORE UPDATE ON payment FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('update', NOW(), user(), FALSE,'payment');
END$$

DELIMITER $$
CREATE TRIGGER au_payment AFTER UPDATE ON payment FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'update' AND table_name = 'payment';
END$$

DELIMITER $$
CREATE TRIGGER bd_payment BEFORE DELETE ON payment FOR EACH ROW
BEGIN
  INSERT INTO data_log (action, action_time, user, success, table_name) 
  VALUES('delete', NOW(), user(), FALSE,'payment');
END$$

DELIMITER $$
CREATE TRIGGER ad_payment AFTER DELETE ON payment FOR EACH ROW
BEGIN
  UPDATE data_log   
  SET success = TRUE
  WHERE action = 'delete' AND table_name = 'payment';
END$$
DELIMITER ;



--create trigger after insert in rental takes elemeents and  insert into payment
DROP TRIGGER IF EXISTS auto_rental_payment;
DELIMITER $
CREATE TRIGGER auto_rental_payment AFTER INSERT ON rental FOR EACH ROW
BEGIN
  declare plan Int;

  select subscription_type into plan from rental inner join subscription on rental.customer_id=subscription.customer_id where rental_id = new.rental_id;

  if plan=0  then
    insert into payment (payment_id,rental_id,payment_date,customer_id,amount) values (0,new.rental_id,new.rental_date,new.customer_id,0.4);  
  end if;
  if plan=1  then
    insert into payment (payment_id,rental_id,payment_date,customer_id,amount) values (0,new.rental_id,new.rental_date,new.customer_id,0.2);  
  end if;
  if plan=2 and new.film_inventory_id IS NOT NULL then
    insert into payment (payment_id,rental_id,payment_date,customer_id,amount) values (0,new.rental_id,new.rental_date,new.customer_id,0.3);  
  end if;
  if plan=2 and new.series_inventory_id IS NOT NULL then
    insert into payment (payment_id,rental_id,payment_date,customer_id,amount) values (0,new.rental_id,new.rental_date,new.customer_id,0.1);  
  end if;
  
END$
DELIMITER ;

-- create trigger after delete in rental delete in payment

DROP TRIGGER IF EXISTS auto_rental_payment_delete;
DELIMITER $
CREATE TRIGGER auto_rental_payment_delete before DELETE ON rental FOR EACH ROW
BEGIN
  delete from payment where rental_id = old.rental_id ;
END$
DELIMITER ;


-- create trigger that after 3 inserts  with the same payment_date on payment it changes the amount field

DROP TRIGGER IF EXISTS after_3_payment;
DELIMITER $
CREATE TRIGGER after_3_payment before INSERT ON payment FOR EACH ROW
BEGIN
  declare num int;
  select count(*) into num from payment where DATEDIFF(payment_date,new.payment_date)=0 and customer_id=new.customer_id;
  set num = num + 1;
  if mod(num,3)=0 and num>0 then
     set new.amount=0.5* new.amount;
  end if;
END$
DELIMITER ;


INSERT INTO `rental` (`rental_id`, `rental_date`, `film_inventory_id`, `customer_id`, `series_inventory_id`) VALUES (0, NOW(), NULL, '583', '23');
INSERT INTO `rental` (`rental_id`, `rental_date`, `film_inventory_id`, `customer_id`, `series_inventory_id`) VALUES (0, NOW(), NULL, '583', '24');
INSERT INTO `rental` (`rental_id`, `rental_date`, `film_inventory_id`, `customer_id`, `series_inventory_id`) VALUES (0, NOW(), NULL, '583', '25');



-- create trigger that prevents change in some elements on the  customer table 

DROP TRIGGER IF EXISTS customer_update;
DELIMITER $
CREATE TRIGGER customer_update BEFORE UPDATE ON customer FOR EACH ROW
BEGIN
  if old.customer_id != new.customer_id then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot change customer_id';
  end if;
  if old.email != new.email then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot change email';
  end if;
  if old.active != new.active then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot change active';
  end if;
  if old.create_date != new.create_date then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot change create_date';
  end if;
END$
DELIMITER ;