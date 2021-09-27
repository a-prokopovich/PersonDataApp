CREATE TABLE IF NOT EXISTS persons_db.persons (
    id SERIAL PRIMARY KEY,
    full_name CHARACTER VARYING(15) NOT NULL,
    phone CHARACTER VARYING(15) NOT NULL,
    email CHARACTER VARYING(20) NOT NULL UNIQUE

    CHECK (
        (full_name !='')
        AND (length(full_name) >= 3)
        AND (phone !='')
        AND (email !='')
    )
);