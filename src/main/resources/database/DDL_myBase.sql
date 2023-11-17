CREATE TABLE IF NOT EXISTS wagons (
    id UUID PRIMARY KEY,
    wagonNumber varchar(20) UNIQUE,
    loadCapacity int,
    yearOfConstruction int,
    dateOfLastService date
);