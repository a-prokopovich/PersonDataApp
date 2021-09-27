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