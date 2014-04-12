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
  PRIMARY KEY (id),
  UNIQUE KEY email_UNIQUE (email)
);
--rollback drop table user;

--changeset VitorBertazoli:charity_table (dbms:mysql failOnError:true)
CREATE TABLE charity (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45),
  registrationNumber varchar(30) NOT NULL,
  status varchar(45),
  effectiveDateOfStatus date,
  category varchar(45),
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
  name varchar(45) NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY (countryId) REFERENCES country(id) ON DELETE CASCADE 
);

CREATE TABLE charity_address (
  id int(11) NOT NULL AUTO_INCREMENT,
  countryId int(11) NOT NULL,
  stateId int(11) NOT NULL,
  street varchar(45),
  city varchar(45),
  postalCode varchar(10),
  PRIMARY KEY (id),
  FOREIGN KEY (countryId) REFERENCES country(id) ON DELETE CASCADE,
  FOREIGN KEY (stateId) REFERENCES state(id) ON DELETE CASCADE
);
--rollback drop table charity_address;
--rollback drop table state;
--rollback drop table country;
