
INSERT INTO USER_ACCOUNT (LOGIN, PASSWORD, DATE_REGISTERED, LAST_SEEN, EMAIL, ACCOUNT_ACTIVE, NAME, SURNAME, HEIGHT, WEIGHT) VALUES
('Marcus', '$2a$10$3/7x.Gk5teL/CxbIHFMUOuAKWjTGfSI3spipholfZTy.uTgr6oQDK', '2008-01-01', '2010-01-01', 'swedish@wp.eu', TRUE, 'Marc', 'Nacht', 189, 85);
INSERT INTO USER_ACCOUNT (LOGIN, PASSWORD, DATE_REGISTERED, LAST_SEEN, EMAIL, ACCOUNT_ACTIVE, NAME, SURNAME, HEIGHT, WEIGHT) VALUES
('user', 'user', '2008-01-01', '2010-01-01', 'user@wp.eu', TRUE, 'user', 'user', 178, 72);
INSERT INTO USER_ACCOUNT (LOGIN, PASSWORD, DATE_REGISTERED, LAST_SEEN, EMAIL, ACCOUNT_ACTIVE, NAME, SURNAME, HEIGHT, WEIGHT) VALUES
('test', '$2a$10$3/7x.Gk5teL/CxbIHFMUOuAKWjTGfSI3spipholfZTy.uTgr6oQDK', null, null, 'aplikacjatreningoswa@gmail.com', TRUE, 'test', 'user', 192, 102);

/* Hasło - useruseruser */

insert into training_form (id, description, from_date, name, to_date, user_id) values (1, 'Opis1', '2021-06-16 16:00', 'Meeting1', '2021-06-16 17:00', 3);
insert into training_form (id, description, from_date, name, to_date, user_id) values (2, 'Opis2', '2021-06-17 11:30', 'Meeting2', '2021-06-17 12:30', 3);

insert into training_form (id, description, from_date, name, to_date, user_id) values (null, 'asd', '2021-05-25 16:30', 'asd', '2021-05-25 17:30', 1);
insert into training (id, description, name, user_id) values (null, 'a', 'a', 1);
insert into PARAMETRIZED_EXERCISE (ID, EXERCISE_ID, TRAINING_ID, TRAINING_FORM_ID) values (null, 2, 1, null);
insert into VALUED_PARAMETER (ID, VALUE, PARAMETER_ID, PARAMETRIZED_EXERCISE_ID) values (null, 123, 1, 1);
