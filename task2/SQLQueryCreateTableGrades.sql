CREATE TABLE Grades (
    GradeID INT PRIMARY KEY,
    StudentID INT,
    CourseCode NVARCHAR(10),
    Grade CHAR(2)
);

INSERT INTO Grades VALUES
(1, 1, 'CS101', 'A'),
(2, 2, 'DB202', 'B'),
(3, 3, 'MATH01', 'C');
