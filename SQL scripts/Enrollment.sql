CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

/* Enrollments */
DROP SEQUENCE sec_pk_enrollment;
DROP TABLE Matricula_Enrollments CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_enrollment START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Enrollments(
        id NUMBER,
        groupId NUMBER,
        userId NUMBER,
        grade NUMBER(3),
        
        CONSTRAINT pk_enrollment PRIMARY KEY (id),
        CONSTRAINT ck_enrollment UNIQUE(groupId, userId),
        
        CONSTRAINT fk_enrollment_group FOREIGN KEY (groupId) REFERENCES Matricula_Groups(id),
        CONSTRAINT fk_enrollment_user FOREIGN KEY (userId) REFERENCES Matricula_Users(id)
);

/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindEnrollment(idIn IN NUMBER, status IN NUMBER)
RETURN Types.ref_cursor 
AS 
        enrollment_cursor types.ref_cursor; 
BEGIN 
  OPEN enrollment_cursor FOR 
       SELECT * FROM Matricula_Enrollments WHERE id = idIn OR status = 1; 
RETURN enrollment_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertEnrollment(
        groupId NUMBER,
        userId NUMBER,
        grade NUMBER
)
AS
BEGIN

    INSERT INTO Matricula_Enrollments VALUES(sec_pk_enrollment.nextval, groupId, userId, grade);	
    COMMIT;

END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateEnrollment (
        idIn IN NUMBER,
        groupIdIn NUMBER,
        userIdIn NUMBER,
        gradeIn NUMBER
)
AS
BEGIN
        UPDATE Matricula_Enrollments SET groupId = groupIdIn, userId = userIdIn, grade = gradeIn WHERE id = idIn;
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteEnrollment(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Enrollments WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
EXECUTE Matricula_Insertenrollment(1, 4, 100);
EXECUTE Matricula_Insertenrollment(2, 4, 80);
COMMIT;
SELECT * FROM Matricula_Enrollments;