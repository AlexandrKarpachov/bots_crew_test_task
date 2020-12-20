create table if not exists departments (
    id UUID PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    chief_id UUID
);

create table if not exists lecturers (
    id UUID PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    degree VARCHAR(20),
    salary DECIMAL(10,2)
);

create table if not exists departments_lecturers (
    department_id UUID NOT NULL,
    lecturer_id UUID NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id) ON DELETE CASCADE,
    UNIQUE (department_id, lecturer_id)
);
