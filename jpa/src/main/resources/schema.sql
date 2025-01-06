DROP SCHEMA PUBLIC CASCADE ;

DROP TABLE IF EXISTS isiku_telefonid CASCADE;
DROP TABLE IF EXISTS isik CASCADE;
DROP TABLE IF EXISTS aadress CASCADE;

DROP SEQUENCE IF EXISTS jada1;
CREATE SEQUENCE jada1 AS INTEGER START WITH 1 INCREMENT BY 1;

CREATE TABLE aadress (
  id BIGINT NOT NULL PRIMARY KEY,
  tanav VARCHAR(255) NOT NULL
);

CREATE TABLE isik (
  id BIGINT NOT NULL PRIMARY KEY,
  nimi VARCHAR(255) NOT NULL,
  aadressi_id BIGINT,
  FOREIGN KEY (aadressi_id)
    REFERENCES aadress(id) ON DELETE RESTRICT
);

CREATE TABLE isiku_telefonid (
  isiku_id BIGINT NOT NULL,
  number VARCHAR(255) NOT NULL,
  FOREIGN KEY (isiku_id)
    REFERENCES isik(id) ON DELETE CASCADE
);
