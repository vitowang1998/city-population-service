BEGIN;

DELETE FROM city_population
WHERE country IN ('China', 'Japan');

INSERT INTO city_population (country, city, population)
VALUES
    -- Canada: 2021 Census (municipality)
    ('Canada', 'Toronto',       2794356),
    ('Canada', 'Ottawa',        1017449),
    ('Canada', 'Montreal',      1762949),
    ('Canada', 'Vancouver',      662248),
    ('Canada', 'Calgary',       1306784),

    -- France: 2023 INSEE municipal population (commune)
    ('France', 'Paris',         2103778),
    ('France', 'Marseille',      886040),
    ('France', 'Lyon',           519127),
    ('France', 'Toulouse',       514819),
    ('France', 'Nice',           357737),

    -- USA: July 1, 2024 Census Bureau population estimates (city)
    ('USA', 'New York',        8478072),
    ('USA', 'Los Angeles',     3878704),
    ('USA', 'Chicago',        2721308),
    ('USA', 'Houston',        2390125),
    ('USA', 'Phoenix',        1673164)
ON CONFLICT (country, city)
DO UPDATE SET population = EXCLUDED.population;

COMMIT;
