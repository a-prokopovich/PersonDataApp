CREATE SCHEMA IF NOT EXISTS persons_db;

CREATE TABLE IF NOT EXISTS persons_db.persons (
    id SERIAL PRIMARY KEY,
    full_name CHARACTER VARYING(50) NOT NULL,
    phone CHARACTER VARYING(15) NOT NULL,
    email CHARACTER VARYING(20) NOT NULL UNIQUE

    CHECK (
        (full_name !='')
        AND (length(full_name) >= 3)
        AND (phone !='')
        AND (email !='')
    )
);

CREATE TABLE IF NOT EXISTS persons_db.passport_data (
    id SERIAL PRIMARY KEY,
    person_id INTEGER NOT NULL UNIQUE,
    passport_number CHARACTER VARYING(12) NOT NULL,
    identification_number CHARACTER VARYING(20) NOT NULL UNIQUE,

    CONSTRAINT "FK_passport_data_persons" FOREIGN KEY (person_id)
        REFERENCES persons_db.persons (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CHECK (
        (passport_number !='')
        AND (identification_number !='')
    )
);