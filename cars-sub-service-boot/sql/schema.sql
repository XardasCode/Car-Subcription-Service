DROP TABLE IF EXISTS car_statuses CASCADE;
DROP TABLE IF EXISTS cars CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS subscription_statuses CASCADE;
DROP TABLE IF EXISTS subscriptions CASCADE;


CREATE TABLE IF NOT EXISTS car_statuses
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cars
(
    id                INT AUTO_INCREMENT,
    name              VARCHAR(255) NOT NULL,
    model             VARCHAR(255) NOT NULL,
    brand             VARCHAR(255) NOT NULL,
    production_year   INT          NOT NULL,
    color             VARCHAR(255) NOT NULL,
    price             INT          NOT NULL,
    fuel_type         VARCHAR(255) NOT NULL,
    chassis_number    VARCHAR(255) NOT NULL,
    reg_number        VARCHAR(255) NOT NULL,
    reg_date          VARCHAR(255) NOT NULL,
    mileage           INT          NOT NULL,
    last_service_date VARCHAR(255) NOT NULL,
    status_id         INT          NOT NULL,
    create_date       VARCHAR(255) NOT NULL,
    last_update_date  VARCHAR(255) NOT NULL,
    image_path        VARCHAR(255),

    PRIMARY KEY (id),
    KEY fk_car_statuses (status_id)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id                INT AUTO_INCREMENT,
    name              VARCHAR(255) NOT NULL,
    surname           VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    phone             VARCHAR(255),
    is_verified       BOOLEAN      NOT NULL DEFAULT FALSE,
    is_blocked        BOOLEAN      NOT NULL DEFAULT FALSE,
    verification_code VARCHAR(255),
    create_date       VARCHAR(255) NOT NULL,
    last_update_date  VARCHAR(255) NOT NULL,
    role_id           INT          NOT NULL,

    PRIMARY KEY (id),
    KEY fk_users_roles (role_id),

    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS subscription_statuses
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subscriptions
(
    id               INT AUTO_INCREMENT,
    is_active        BOOLEAN      NOT NULL DEFAULT TRUE,
    start_date       VARCHAR(255) NOT NULL,
    month_price      INT          NOT NULL,
    total_price      INT          NOT NULL,
    total_months     INT          NOT NULL,
    create_date      VARCHAR(255) NOT NULL,
    last_update_date VARCHAR(255) NOT NULL,
    pass_number      VARCHAR(255) NOT NULL,
    ipn_number       VARCHAR(255) NOT NULL,
    soc_media_link   VARCHAR(255) NOT NULL,
    last_pay_date    VARCHAR(255),

    user_id          INT,
    car_id           INT,
    manager_id       INT,
    status_id        INT,

    PRIMARY KEY (id),
    KEY fk_subscriptions_users (user_id),
    KEY fk_subscriptions_cars (car_id),
    KEY fk_subscriptions_managers (manager_id),
    KEY fk_subscriptions_statuses (status_id),

    UNIQUE (user_id),
    UNIQUE (car_id)
);