DROP TABLE IF EXISTS managers CASCADE;
DROP TABLE IF EXISTS car_statuses CASCADE;
DROP TABLE IF EXISTS cars CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS subscription_statuses CASCADE;
DROP TABLE IF EXISTS subscriptions CASCADE;


CREATE TABLE IF NOT EXISTS managers
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    create_date varchar(255) NOT NULL,
    last_update varchar(255) NOT NULL,

    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS car_statuses
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cars
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    model             VARCHAR(255) NOT NULL,
    brand             VARCHAR(255) NOT NULL,
    year              INT          NOT NULL,
    color             VARCHAR(255) NOT NULL,
    price             INT          NOT NULL,
    fuel_type         VARCHAR(255) NOT NULL,
    chassis_number    VARCHAR(255) NOT NULL,
    reg_number        VARCHAR(255) NOT NULL,
    reg_date          varchar(255) NOT NULL,
    mileage           INT          NOT NULL,
    last_service_date varchar(255) NOT NULL,
    status_id         INT          NOT NULL,
    create_date       varchar(255) NOT NULL,
    last_update_date  varchar(255) NOT NULL,
    image_path        VARCHAR(255),

    FOREIGN KEY (status_id) REFERENCES car_statuses (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS users
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    surname           VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    phone             VARCHAR(255),
    is_verified       BOOLEAN      NOT NULL DEFAULT FALSE,
    is_blocked        BOOLEAN      NOT NULL DEFAULT FALSE,
    verification_code VARCHAR(255),
    create_date       varchar(255) NOT NULL,
    last_update_date  varchar(255) NOT NULL,

    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS subscription_statuses
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subscriptions
(
    id               SERIAL PRIMARY KEY,
    is_active        BOOLEAN      NOT NULL DEFAULT TRUE,
    start_date       varchar(255) NOT NULL,
    month_price      INT          NOT NULL,
    total_price      INT          NOT NULL,
    total_months     INT          NOT NULL,
    create_date      varchar(255) NOT NULL,
    last_update_date varchar(255) NOT NULL,

    user_id          INT,
    car_id           INT,
    manager_id       INT,
    status_id        INT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (status_id) REFERENCES subscription_statuses (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES managers (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (user_id),
    UNIQUE (car_id)
);