CREATE TABLE Students (
    StudentID INT PRIMARY KEY,
    FullName NVARCHAR(100),
    Age INT,
    EnrollmentDate DATE
);

INSERT INTO Students VALUES
(1, 'Артем Петров', 20, '2023-09-01'),
(2, 'Игорь Смирнов', 21, '2022-09-01'),
(3, 'Олег Лаптев', 19, '2024-01-15');
