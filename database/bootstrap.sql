--liquibase formatted sql

--changeset VitorBertazoli:user_table (dbms:mysql failOnError:true)
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstname varchar(45),
  lastname varchar(45),
  username varchar(45),
  email varchar(50),
  password varchar(100),
  salt varchar(50),
  dob date,
  activated boolean,
  createdOn timestamp,
  activatedOn timestamp,
  active boolean,
  userRole int(5),
  PRIMARY KEY (id),
  UNIQUE KEY UNIQUE_USER (username, email)
);

CREATE TABLE user_token (
  id int(11) NOT NULL AUTO_INCREMENT,
  userId int(11) NOT NULL,
  token varchar(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);
--rollback drop table user_token;
--rollback drop table user;

--changeset VitorBertazoli:charity_table (dbms:mysql failOnError:true)
CREATE TABLE charity (
  id int(11) NOT NULL AUTO_INCREMENT,
  registrationNumber varchar(20) NOT NULL,
  name varchar(200),
  status int(11) NOT NULL,
  sanction int(11),
  designationCode int(11),
  effectiveDateOfStatus date,
  category int(11),
  PRIMARY KEY (id),
  UNIQUE KEY registrationNumber (registrationNumber)
);
--rollback drop table charity;

--changeset VitorBertazoli:address (dbms:mysql failOnError:true)
CREATE TABLE country (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  code varchar(4),
  PRIMARY KEY(id)
);

CREATE TABLE state (
  id int(11) NOT NULL AUTO_INCREMENT,
  countryId int(11) NOT NULL,
  code varchar(4),
  name varchar(45) NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY (countryId) REFERENCES country(id) ON DELETE CASCADE 
);

CREATE TABLE charity_address (
  id int(11) NOT NULL AUTO_INCREMENT,
  charityId int(11) NOT NULL,
  countryId int(11),
  stateId int(11),
  street varchar(100),
  city varchar(45),
  postalCode varchar(10),
  PRIMARY KEY (id),
  FOREIGN KEY (charityId) REFERENCES charity(id) ON DELETE CASCADE,
  FOREIGN KEY (countryId) REFERENCES country(id) ON DELETE CASCADE,
  FOREIGN KEY (stateId) REFERENCES state(id) ON DELETE CASCADE
);
--rollback drop table charity_address;
--rollback drop table state;
--rollback drop table country;

--changeset VitorBertazoli:createDrawTable (dbms:mysql failOnError:true)
CREATE TABLE draw (
  id int(11) NOT NULL AUTO_INCREMENT,
  drawDateStart timestamp NOT NULL,
  drawDateEnd timestamp NOT NULL,
  status varchar(20) NOT NULL,
  active boolean DEFAULT FALSE,
  PRIMARY KEY (id)
);
--rollback DROP TABLE draw;

--changeset VitorBertazoli:createDonationTable (dbms:mysql failOnError:true)
CREATE TABLE donation (
  id int(11) NOT NULL AUTO_INCREMENT,
  userId int(11) NOT NULL,
  drawId int(11) NOT NULL,
  charityId int(11) NOT NULL,
  donationDate timestamp NOT NULL,
  transaction varchar(30) NOT NULL,
  feeAmountCurrency varchar(3),
  feeAmountValue numeric(9,4),
  grossAmountCurrency varchar(3),
  grossAmountValue numeric(9,4),
  paymentStatus varchar(30),
  paymentType varchar(15),
  completed boolean DEFAULT false,
  paypalToken varchar(40),
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (drawId) REFERENCES draw(id) ON DELETE CASCADE,
  FOREIGN KEY (charityId) REFERENCES charity(id) ON DELETE CASCADE
);
--rollback DROP TABLE donation;

--changeset VitorBertazoli:createUserTicketsTable (dbms:mysql failOnError:true)
CREATE TABLE user_ticket (
  id int(11) NOT NULL AUTO_INCREMENT,
  donationId int(11) NOT NULL,
  ticketNumber varchar(70) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (donationId) REFERENCES donation(id) ON DELETE CASCADE
);
--rollback DROP TABLE user_ticket;

--changeset VitorBertazoli:createDonationInformationTable (dbms:mysql failOnError:true)
CREATE TABLE donation_information (
  id int(11) NOT NULL AUTO_INCREMENT,
  userId int(11) NOT NULL,
  drawId int(11) NOT NULL,
  charityId int(11) NOT NULL,
  paypalToken varchar(40),
  amountToDonate int(11),
  percentageToKeep int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (drawId) REFERENCES draw(id) ON DELETE CASCADE,
  FOREIGN KEY (charityId) REFERENCES charity(id) ON DELETE CASCADE
);
--rollback DROP TABLE donation_information;

--changeset VitorBertazoli:insertFirstUser (dbms:mysql failOnError:true)
INSERT INTO user (firstname, lastname, username, email, password, salt, dob, activated, createdOn, activatedOn, active, userRole) VALUES
('Vitor','Bertazoli','vitor','vitor@bertazoli.com','f760d8462d5bf549ca5fa042a4f8c92ef8e6cdefb07299b8c6e0b856eec9fa3e','!,B@!ij,!}Rg=Sn/oyzO  <DI:Mkdfr_u7b_E:r:ZTG_/fWJ!v','1982-03-10',TRUE,'2014-04-25 13:18:10','2014-04-25 13:20:44',TRUE, 1);
INSERT INTO user_token (userId, token) VALUES
(1, 'dAfp7PXzIxTVbwjgKH2z8GBZCJZHS2XKhcuTHUhxORIom1ccHbhl0FzvzMTO4lPADsWi7KPpIqVVFju7CdkYrjjF5r3ynwBqRNkw');

--rollback DELETE FROM user;
--rollback DELETE FROM user_token;