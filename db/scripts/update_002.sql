
insert into lecturers(id, name, surname, degree, salary) values
('15aeaac1-b4a4-4100-88a5-478e3e5df8be', 'Ivan', 'Petrov', 'PROFESSOR', 11000.00),
('15aeaac1-b4a4-4100-88a5-478e3e5df8b1', 'Petr', 'Ivanov', 'PROFESSOR', 10000.00),
('15aeaac1-b4a4-4100-88a5-478e3e5df8b2', 'Alex', 'Petrov', 'ASSISTANT', 5000.00);

insert into departments (id, name, chief_id) values
('25aeaac1-b4a4-4100-88a5-478e3e5df8be', 'BKVRM', '15aeaac1-b4a4-4100-88a5-478e3e5df8be');

insert into departments_lecturers (department_id, lecturer_id) values
('25aeaac1-b4a4-4100-88a5-478e3e5df8be', '15aeaac1-b4a4-4100-88a5-478e3e5df8be'),
('25aeaac1-b4a4-4100-88a5-478e3e5df8be', '15aeaac1-b4a4-4100-88a5-478e3e5df8b1'),
('25aeaac1-b4a4-4100-88a5-478e3e5df8be', '15aeaac1-b4a4-4100-88a5-478e3e5df8b2');


-- create table if not exists departments (
--                                            id BLOB PRIMARY KEY,
--                                            name VARCHAR(20) NOT NULL,
--                                            chief_id BLOB(255)
-- );
--
-- create table if not exists lecturers (
--                                          id BLOB PRIMARY KEY,
--                                          name VARCHAR(20) NOT NULL,
--                                          surname VARCHAR(20) NOT NULL,
--                                          degree VARCHAR(20),
--                                          salary DECIMAL(10,2)
-- );
--
-- create table if not exists departments_lecturers (
--                                                      department_id BLOB NOT NULL,
--                                                      lecturer_id BLOB NOT NULL,
--                                                      FOREIGN KEY (department_id) REFERENCES departments(id),
--                                                      FOREIGN KEY (lecturer_id) REFERENCES lecturers(id),
--                                                      UNIQUE (department_id, lecturer_id)
-- );
