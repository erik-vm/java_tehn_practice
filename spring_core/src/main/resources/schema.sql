DROP TABLE IF EXISTS person;

DROP SEQUENCE IF EXISTS person_sequence;

CREATE SEQUENCE person_sequence AS INTEGER START WITH 1;

CREATE TABLE person (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('person_sequence'),
    name VARCHAR(255) NOT NULL
);
