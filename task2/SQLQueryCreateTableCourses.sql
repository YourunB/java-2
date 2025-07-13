CREATE TABLE Courses (
    CourseCode NVARCHAR(10) PRIMARY KEY,
    CourseName NVARCHAR(100),
    Credits INT,
    Active BIT
);

INSERT INTO Courses VALUES
('CS101', '���������������� �� Java', 4, 1),
('DB202', '���� ������', 3, 1),
('MATH01', '���������� ��� ��', 2, 0);
