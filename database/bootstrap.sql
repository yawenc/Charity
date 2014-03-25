--liquibase formatted sql

--changeset VitorBertazoli:initial (dbms:mysql failOnError:true)
SELECT 1;
--rollback SELECT 1;