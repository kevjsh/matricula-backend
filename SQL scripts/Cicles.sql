CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

/* CICLES */
DROP SEQUENCE sec_pk_cicle;
DROP TABLE Matricula_Cicles CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_cicle START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Cicles(
        id NUMBER,
        year NUMBER(4),
        cicleNumber NUMBER(1),
        initDate DATE,
        finishDate DATE,
        CONSTRAINT pk_cicle PRIMARY KEY (id),
        CONSTRAINT ck_cicle UNIQUE(year, cicleNumber)
);

/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindCicle(idIn IN NUMBER, status IN NUMBER)
RETURN Types.ref_cursor 
AS 
        cicle_cursor types.ref_cursor; 
BEGIN 
  OPEN cicle_cursor FOR 
       SELECT * FROM Matricula_Cicles WHERE id = idIn OR status = 1; 
RETURN cicle_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertCicle(
        year IN NUMBER,
        cicleNumber IN NUMBER,
        initDate IN DATE,
        finishDate IN DATE
)

AS
BEGIN
	INSERT INTO Matricula_Cicles VALUES(sec_pk_cicle.nextval, year, cicleNumber, initDate, finishDate);
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateCicle (
        idIn IN NUMBER,
        yearIn IN NUMBER,
        cicleNumberIn IN NUMBER,
        initDateIn IN DATE,
        finishDateIn IN DATE
)
AS
BEGIN
        UPDATE Matricula_Cicles SET year = yearIn, cicleNumber = cicleNumberIn, initDate = initDateIn, finishDate = finishDateIn WHERE id = idIn;
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteCicle(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Cicles WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
INSERT INTO Matricula_Cicles VALUES(sec_pk_cicle.nextval, 2022, 1, to_date('2022-01-01','yyyy,mm,dd'), to_date('2022-01-01','yyyy,mm,dd'));
COMMIT;