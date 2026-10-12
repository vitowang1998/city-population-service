INSERT INTO city_population (country, city, population)
VALUES
    ('Canada', 'Toronto', 3000000),
    ('Canada', 'Ottawa', 1100000),
    ('China', 'Beijing', 21500000),
    ('China', 'Shanghai', 25000000),
    ('Japan', 'Tokyo', 14000000)
ON CONFLICT (country, city) DO NOTHING;