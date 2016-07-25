CREATE TABLE city
(
    cityId VARCHAR(255) PRIMARY KEY NOT NULL,
    cityName VARCHAR(255),
    provinceName VARCHAR(255),
    stationName VARCHAR(255),
    searchCount BIGINT(20) NOT NULL
);
CREATE TABLE weatherrecord
(
    day DATETIME(6) PRIMARY KEY NOT NULL,
    description VARCHAR(255),
    lastUpdate DATETIME(6),
    temperature VARCHAR(255),
    weatherDay INT(11) NOT NULL,
    weatherNight INT(11) NOT NULL,
    city_cityId VARCHAR(255),
    CONSTRAINT FKtk65yllirkjmlf9qryhm2h2bc FOREIGN KEY (city_cityId) REFERENCES city (cityId)
);
CREATE INDEX FKtk65yllirkjmlf9qryhm2h2bc ON weatherrecord (city_cityId);
