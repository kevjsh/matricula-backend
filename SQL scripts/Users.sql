CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/


/* ROLES */
DROP TABLE Matricula_Roles CASCADE CONSTRAINTS;

CREATE TABLE Matricula_Roles(
    id NUMBER,
    description VARCHAR2(20),

    CONSTRAINT pk_rol PRIMARY KEY (id)
);

INSERT INTO Matricula_Roles VALUES (1, 'Administrador');
INSERT INTO Matricula_Roles VALUES (2, 'Matriculador');
INSERT INTO Matricula_Roles VALUES (3, 'Profesor');
INSERT INTO Matricula_Roles VALUES (4, 'Alumno');
COMMIT;


/* USERS */
DROP SEQUENCE sec_pk_user;
DROP TABLE Matricula_Users CASCADE CONSTRAINTS;

CREATE SEQUENCE sec_pk_user START WITH 1 increment BY 1 cache 2;

CREATE TABLE Matricula_Users(
    id NUMBER,
    personId VARCHAR2(20),
    name VARCHAR2(75),
    telephone NUMBER,
    birthday DATE NULL,
    careerId NUMBER NULL,
    roleId NUMBER(1),
    email VARCHAR2(100),
    password VARCHAR2(100),

    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT ck_personId UNIQUE(personId),
    CONSTRAINT fk_user_career FOREIGN KEY (careerId) REFERENCES Matricula_Careers(id),
    CONSTRAINT fk_user_rol FOREIGN KEY (roleId) REFERENCES Matricula_Roles(id)
);


/* *************************************************************** */

CREATE OR REPLACE FUNCTION Matricula_FindUser(idIn NUMBER, personIdIn VARCHAR2, status NUMBER)
RETURN Types.ref_cursor 
AS 
        user_cursor types.ref_cursor; 
BEGIN 
  OPEN user_cursor FOR 
       SELECT * FROM Matricula_Users WHERE id = idIn OR personId = personIdIn OR status = 1;
RETURN user_cursor; 
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_InsertUser(
        personId VARCHAR2,
        name VARCHAR2,
        telephone NUMBER,
        birthday DATE,
        careerId NUMBER,
        roleId NUMBER,
        email VARCHAR2,
        password VARCHAR2
)

AS
BEGIN

        IF careerId = 0 OR roleId = 1 OR roleId = 2  OR roleId = 3 THEN
            INSERT INTO Matricula_Users VALUES(sec_pk_user.nextval, personId, name, telephone, birthday, null, roleId, email, password);
        ELSE
            INSERT INTO Matricula_Users VALUES(sec_pk_user.nextval, personId, name, telephone, birthday, careerId, roleId, email, password);
        END IF;
	
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_UpdateUser (
        idIn NUMBER,
        personIdIn VARCHAR2,
        nameIn VARCHAR2,
        telephoneIn NUMBER,
        birthdayIn DATE,
        careerIdIn NUMBER,
        roleIdIn NUMBER,
        emailIn VARCHAR2,
        passwordIn VARCHAR2
)
AS
BEGIN

        IF careerIdIn = 0 OR roleIdIn = 1 OR roleIdIn = 2  OR roleIdIn = 3 THEN
            UPDATE Matricula_Users SET personId = personIdIn, name = nameIn, telephone = telephoneIn,
            birthday = birthdayIn, careerId = null, roleId = roleIdIn, email = emailIn, password = passwordIn
            WHERE id = idIn;
        ELSE
            UPDATE Matricula_Users SET personId = personIdIn, name = nameIn, telephone = telephoneIn,
            birthday = birthdayIn, careerId = careerIdIn, roleId = roleIdIn, email = emailIn, password = passwordIn
            WHERE id = idIn;
        END IF;
        
        COMMIT;
END;
/

/* *************************************************************** */

CREATE OR REPLACE PROCEDURE Matricula_DeleteUser(idIn IN NUMBER)
AS
BEGIN
    DELETE FROM Matricula_Users WHERE id = idIn;
    COMMIT;
END;
/

/* Only for testing porpuses */
EXECUTE Matricula_InsertUser('1', 'Administrador', 88888888, to_date('2000-01-01','yyyy-mm-dd'), null, 1, 'email@email.com', '1');
EXECUTE Matricula_InsertUser('2', 'Matriculador', 77777777, to_date('2000-01-01','yyyy-mm-dd'), null, 2, 'email@email.com', '2');
EXECUTE Matricula_InsertUser('3', 'Profesor', 66666666, to_date('2000-01-01','yyyy-mm-dd'), null, 3, 'email@email.com', '3');
EXECUTE Matricula_InsertUser('4', 'Estudiante', 55555555, to_date('2000-01-01','yyyy-mm-dd'), 1, 4, 'email@email.com', '4');
COMMIT;
SELECT * FROM Matricula_Users;