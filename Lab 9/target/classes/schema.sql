DROP TABLE cities;
DROP TABLE countries;
DROP TABLE continents;

DROP SEQUENCE continents_seq;
DROP SEQUENCE countries_seq;
DROP SEQUENCE cities_seq;

-- Creează secvențe pentru ID-uri
CREATE SEQUENCE continents_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE countries_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cities_seq START WITH 1 INCREMENT BY 1;

-- Creează tabela continents
CREATE TABLE continents (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    CONSTRAINT uk_continents_name UNIQUE (name)
);

-- Trigger pentru continents
CREATE OR REPLACE TRIGGER continents_trigger
BEFORE INSERT ON continents
FOR EACH ROW
BEGIN
    SELECT continents_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Creează tabela countries
CREATE TABLE countries (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    code VARCHAR2(3) NOT NULL,
    continent_id NUMBER,
    CONSTRAINT uk_countries_name UNIQUE (name),
    CONSTRAINT uk_countries_code UNIQUE (code),
    CONSTRAINT fk_countries_continent FOREIGN KEY (continent_id) REFERENCES continents(id) ON DELETE CASCADE
);

-- Trigger pentru countries
CREATE OR REPLACE TRIGGER countries_trigger
BEFORE INSERT ON countries
FOR EACH ROW
BEGIN
    SELECT countries_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Creează tabela cities
CREATE TABLE cities (
    id NUMBER PRIMARY KEY,
    country_id NUMBER,
    name VARCHAR2(100) NOT NULL,
    capital NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT chk_capital CHECK (capital IN (0, 1)),
    latitude NUMBER,
    longitude NUMBER,
    CONSTRAINT fk_cities_country FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
);

-- Trigger pentru cities
CREATE OR REPLACE TRIGGER cities_trigger
BEFORE INSERT ON cities
FOR EACH ROW
BEGIN
    SELECT cities_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/