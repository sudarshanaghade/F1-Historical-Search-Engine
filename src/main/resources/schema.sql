CREATE TABLE IF NOT EXISTS seasons (
    year INTEGER PRIMARY KEY,
    url TEXT
);

CREATE TABLE IF NOT EXISTS races (
    raceId INTEGER PRIMARY KEY,
    year INTEGER NOT NULL,
    round INTEGER NOT NULL,
    name TEXT NOT NULL,
    date TEXT,
    circuitName TEXT,
    FOREIGN KEY (year) REFERENCES seasons(year)
);

CREATE TABLE IF NOT EXISTS drivers (
    driverId INTEGER PRIMARY KEY,
    driverRef TEXT UNIQUE NOT NULL,
    code TEXT,
    forename TEXT NOT NULL,
    surname TEXT NOT NULL,
    dob TEXT,
    nationality TEXT
);

CREATE TABLE IF NOT EXISTS constructors (
    constructorId INTEGER PRIMARY KEY,
    constructorRef TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    nationality TEXT
);

CREATE TABLE IF NOT EXISTS results (
    resultId INTEGER PRIMARY KEY,
    raceId INTEGER NOT NULL,
    driverId INTEGER NOT NULL,
    constructorId INTEGER NOT NULL,
    grid INTEGER,
    position INTEGER,
    points REAL,
    winner BOOLEAN,
    FOREIGN KEY (raceId) REFERENCES races(raceId),
    FOREIGN KEY (driverId) REFERENCES drivers(driverId),
    FOREIGN KEY (constructorId) REFERENCES constructors(constructorId)
);
