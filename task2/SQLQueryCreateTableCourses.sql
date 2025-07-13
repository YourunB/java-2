CREATE TABLE Courses (
    CourseCode NVARCHAR(10) PRIMARY KEY,
    CourseName NVARCHAR(100),
    Credits INT,
    Active BIT
);

INSERT INTO Courses VALUES
('CS101', 'Программирование на Java', 4, 1),
('DB202', 'Базы данных', 3, 1),
('MATH01', 'Математика для ИТ', 2, 0);
