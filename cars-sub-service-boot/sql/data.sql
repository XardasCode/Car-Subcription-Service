INSERT INTO car_statuses(name)
VALUES ('AVAILABLE'),
       ('UNAVAILABLE');

INSERT INTO subscription_statuses(name)
VALUES ('UNDER_CONSIDERATION'),
       ('CONFIRM_STATUS'),
       ('REJECTED_STATUS');

INSERT INTO users_roles(name)
VALUES ('ADMIN'),
       ('USER');

INSERT INTO users(name, surname, email, password, create_date, last_update_date, role_id, is_verified)
VALUES ('admin', 'manager', 'adming@gmail.com', 'admin', '2020-01-01', '2020-01-01', 1, true),
       ('user', 'user', 'user@gmail.com', 'user', '2020-01-01', '2020-01-01', 2, false);
