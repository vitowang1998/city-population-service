CREATE TABLE IF NOT EXISTS city_population (
                                               country VARCHAR(100) NOT NULL,
    city VARCHAR(150) NOT NULL,
    population BIGINT NOT NULL,

    PRIMARY KEY (country, city)
);
