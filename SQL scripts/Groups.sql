CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

/* Groups */
DROP SEQUENCE sec_pk_group;
DROP TABLE Matricula_Groups CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_group START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Groups(
        id NUMBER,
        courseId NUMBER,
        groupNumber NUMBER(1),
        schedule VARCHAR2(20),
        cicleId NUMBER NULL,
        professorId NUMBER,
        
        CONSTRAINT pk_group PRIMARY KEY (id),
        CONSTRAINT ck_group UNIQUE(courseId, groupNumber, cicleId),
        
        CONSTRAINT fk_group_cicle FOREIGN KEY (cicleId) REFERENCES Matricula_Cicles(id) ON DELETE CASCADE,
        CONSTRAINT fk_group_course FOREIGN KEY (courseId) REFERENCES Matricula_Courses(id) ON DELETE CASCADE,
        CONSTRAINT fk_group_user FOREIGN KEY (professorId) REFERENCES Matricula_Users(id) ON DELETE CASCADE
);

/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindGroup(idIn IN NUMBER, status IN NUMBER)
RETURN Types.ref_cursor 
AS 
        group_cursor types.ref_cursor; 
BEGIN 
  OPEN group_cursor FOR 
       SELECT * FROM Matricula_Groups WHERE id = idIn OR status = 1; 
RETURN group_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertGroup(
        courseId NUMBER,
        groupNumber NUMBER,
        schedule VARCHAR2,
        cicleIdIn NUMBER,
        professorId NUMBER
)
IS
    countRecords NUMBER;
BEGIN

    
    
    IF groupNumber = 0 THEN
        SELECT COUNT(id) INTO countRecords FROM Matricula_Groups WHERE cicleId = cicleIdIn;
        INSERT INTO Matricula_Groups VALUES(sec_pk_group.nextval, courseId, countRecords + 1 , schedule, cicleIdIn, professorId);
    ELSE
        INSERT INTO Matricula_Groups VALUES(sec_pk_group.nextval, courseId, groupNumber, schedule, cicleIdIn, professorId);
    END IF;
	
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateGroup (
        idIn IN NUMBER,
        courseIdIn NUMBER,
        groupNumberIn NUMBER,
        scheduleIn VARCHAR2,
        cicleIdIn NUMBER,
        professorIdIn NUMBER
)
AS
BEGIN
        UPDATE Matricula_Groups SET courseId = courseIdIn, groupNumber = groupNumberIn, schedule = scheduleIn, cicleId = cicleIdIn, professorId = professorIdIn WHERE id = idIn;
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteGroup(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Groups WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
EXECUTE Matricula_InsertGroup(1, 1, '8am a 9:40am' , 1, 3);
EXECUTE Matricula_InsertGroup(3, 0, '10am a 11:40am', 2 , 3);
COMMIT;
SELECT * FROM Matricula_Groups;