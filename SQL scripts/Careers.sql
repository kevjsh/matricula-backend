CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

/* COURSES */
DROP SEQUENCE sec_pk_course;
DROP TABLE Matricula_Careers CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_course START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Careers(
    id NUMBER,
    code VARCHAR2(10),
    name VARCHAR2(50),
    title VARCHAR2(50),
    CONSTRAINT pk_carrera PRIMARY KEY (id)
);


/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindCareer(codeIn IN VARCHAR2, nameIn VARCHAR2, status IN NUMBER)
RETURN Types.ref_cursor 
AS 
        career_cursor types.ref_cursor; 
BEGIN 
  OPEN career_cursor FOR 
       SELECT * FROM Matricula_Careers WHERE code = codeIn OR name like nameIn OR status = 1; 
RETURN career_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertCareer(
        code IN VARCHAR2,
        name IN VARCHAR2,
        title IN VARCHAR2
)

AS
BEGIN
	INSERT INTO Matricula_Careers VALUES(sec_pk_course.nextval, code, name, title);
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateCareer (
        idIn NUMBER,
        codeIn IN VARCHAR2,
        nameIn IN VARCHAR2,
        titleIn IN VARCHAR2
)
AS
BEGIN
        UPDATE Matricula_Careers SET code = codeIn, nameIn = name, title = titleIn WHERE id = idIn;
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteCicle(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Careers WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
EXECUTE Matricula_InsertCareer('INFO2022','Ingeniería en Sistema de Información', 'Bachillerato Universitario');
EXECUTE Matricula_InsertCareer('HISTO2022','Enseñanza de la Historia', 'Bachillerato Universitario');
COMMIT;
SELECT * FROM Matricula_Careers;