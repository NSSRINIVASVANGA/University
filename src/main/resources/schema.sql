CREATE TABLE if not exists professor(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    department TEXT
);

CREATE TABLE if not exists course(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    credits INTEGER,
    professorId INTEGER,
    FOREIGN KEY (professorId) REFERENCES professor(id)
);

CREATE TABLE if not exists student(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    email TEXT
);

CREATE TABLE if not exists course_student(
    studentId INTEGER,
    courseId INTEGER,
    PRIMARY KEY(studentId,courseId),
    FOREIGN KEY (studentId) REFERENCES student(id),
    FOREIGN KEY (courseId) REFERENCES course(id)
);