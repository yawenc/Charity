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

--changeset VitorBertazoli:charity_status (dbms:mysql failOnError:true)
CREATE TABLE charity_status (
  id int(11) NOT NULL AUTO_INCREMENT,
  status varchar(30),
  PRIMARY KEY (id)
);
--rollback drop table charity_status;

--changeset VitorBertazoli:charity_sanction (dbms:mysql failOnError:true)
CREATE TABLE charity_sanction (
  id int(11) NOT NULL AUTO_INCREMENT,
  sanction varchar(15),
  PRIMARY KEY (id)
);
--rollback drop table charity_sanction;

--changeset VitorBertazoli:charity_table (dbms:mysql failOnError:true)
CREATE TABLE charity (
  id int(11) NOT NULL AUTO_INCREMENT,
  registrationNumber varchar(20) NOT NULL,
  name varchar(200),
  statusId int(11) NOT NULL,
  sanctionId int(11),
  designationCode char,
  effectiveDateOfStatus date,
  categoryCode int(5),
  PRIMARY KEY (id),
  UNIQUE KEY registrationNumber (registrationNumber),
  FOREIGN KEY (statusId) REFERENCES charity_status(id) ON DELETE CASCADE
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
  countryId int(11) NOT NULL,
  stateId int(11) NOT NULL,
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

--changeset VitorBertazoli:insertFirstUser (dbms:mysql failOnError:true)
INSERT INTO user (firstname, lastname, username, email, password, salt, dob, activated, createdOn, activatedOn, active) VALUES
('Vitor','Bertazoli','vitor','vitor@bertazoli.com','f760d8462d5bf549ca5fa042a4f8c92ef8e6cdefb07299b8c6e0b856eec9fa3e','!,B@!ij,!}Rg=Sn/oyzO  <DI:Mkdfr_u7b_E:r:ZTG_/fWJ!v','1982-03-10',TRUE,'2014-04-25 13:18:10','2014-04-25 13:20:44',TRUE);
INSERT INTO user_token (userId, token) VALUES
(1, 'dAfp7PXzIxTVbwjgKH2z8GBZCJZHS2XKhcuTHUhxORIom1ccHbhl0FzvzMTO4lPADsWi7KPpIqVVFju7CdkYrjjF5r3ynwBqRNkw');

--rollback DELETE FROM user;
--rollback DELETE FROM user_token;