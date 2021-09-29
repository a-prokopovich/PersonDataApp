CREATE SCHEMA IF NOT EXISTS persons_db;

CREATE TABLE IF NOT EXISTS persons_db.persons (
    id INTEGER PRIMARY KEY,
    full_name CHARACTER VARYING(50) NOT NULL,
    phone CHARACTER VARYING(15) NOT NULL,
    email CHARACTER VARYING(20) NOT NULL

    CHECK (
        (full_name !='')
        AND (length(full_name) >= 3)
        AND (phone !='')
        AND (email !='')
    )
);

CREATE TABLE IF NOT EXISTS persons_db.passport_data (
    id INTEGER PRIMARY KEY,
    passport_number CHARACTER VARYING(12) NOT NULL UNIQUE,
    identification_number CHARACTER VARYING(20) NOT NULL UNIQUE,

    CONSTRAINT "FK_passport_data_persons" FOREIGN KEY (id)
        REFERENCES persons_db.persons (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CHECK (
        (passport_number !='')
        AND (identification_number !='')
    )
);