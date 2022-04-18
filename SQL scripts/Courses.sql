CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

/* COURSES */
DROP SEQUENCE sec_pk_course;
DROP TABLE Matricula_Courses CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_course START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Courses(
        id NUMBER,
        code VARCHAR2(10),
        name VARCHAR2(50),
        credits NUMBER,
        hours NUMBER,
        careerId NUMBER,
        CONSTRAINT pk_course PRIMARY KEY (id),
        CONSTRAINT fk_course_career FOREIGN KEY (careerId) REFERENCES Matricula_Careers(id)
);

/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindCourse(idIn NUMBER, codeIn VARCHAR2, nameIn VARCHAR2, careerIdIn NUMBER, status IN NUMBER)
RETURN Types.ref_cursor 
AS 
        course_cursor types.ref_cursor; 
BEGIN 
  OPEN course_cursor FOR 
       SELECT * FROM Matricula_Courses WHERE id = idIn OR code = codeIn OR name = nameIn OR careerId = careerIdIn OR status = 1;
RETURN course_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertCourse(
        code VARCHAR2,
        name VARCHAR2,
        credits NUMBER,
        hours NUMBER,
        careerId VARCHAR2
)

AS
BEGIN
	INSERT INTO Matricula_Courses VALUES(sec_pk_course.nextval, code, name, credits, hours, careerId);
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateCourse (
        idIn NUMBER,
        codeIn VARCHAR2,
        nameIn VARCHAR2,
        creditsIn NUMBER,
        hoursIn NUMBER,
        careerIdIn VARCHAR2
)
AS
BEGIN
        UPDATE Matricula_Courses SET code = codeIn, name = nameIn, credits = creditsIn, hours = hoursIn, careerId = careerIdIn WHERE id = idIn;
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteCourse(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Courses WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
EXECUTE Matricula_InsertCourse('EIF-401', 'Sistemas I', 4, 16, 1);
EXECUTE Matricula_InsertCourse('HIS-190', 'Introducción a la Historia', 4, 12, 2);
COMMIT;
SELECT * FROM Matricula_Courses;
