/*    curso   */

CREATE TABLE curso(
codigo VARCHAR(10),
nombre VARCHAR(50),
creditos INT,
horas_semanales INT,
carrera VARCHAR(10),
CONSTRAINT pk_curso PRIMARY KEY (codigo),
CONSTRAINT fk_curso_carrera FOREIGN KEY (carrera) REFERENCES carrera(codigo)
);

CREATE OR REPLACE PROCEDURE insertar_curso(codigo IN curso.codigo%TYPE,nombre IN curso.nombre%TYPE,
creditos IN curso.creditos%TYPE,horas_semanales IN curso.horas_semanales%TYPE,carrera IN curso.carrera%TYPE)
AS
BEGIN
	INSERT INTO curso VALUES(codigo,nombre,creditos,horas_semanales,carrera);
END;
/

CREATE OR REPLACE PROCEDURE modificar_curso(codigoin IN curso.codigo%TYPE,nombrein IN curso.nombre%TYPE,
creditosin IN curso.creditos%TYPE,horas_semanalesin IN curso.horas_semanales%TYPE,carrerain IN curso.carrera%TYPE)
AS
BEGIN
UPDATE curso SET nombre=nombrein,creditos=creditosin,horas_semanales=horas_semanalesin,carrera=carrerain WHERE codigo=codigoin;
END;
/


CREATE OR REPLACE FUNCTION buscar_curso(codigobuscar IN curso.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
       SELECT codigo,nombre,creditos,horas_semanales,carrera FROM curso WHERE codigo=codigobuscar; 
RETURN curso_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listar_cursos
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
       SELECT codigo,nombre,creditos,horas_semanales,carrera FROM curso ; 
RETURN curso_cursor; 
END;
/

CREATE or REPLACE procedure eliminar_curso(codigoin IN curso.codigo%TYPE)
as
begin
    delete from curso where codigo=codigoin;
end;
/


/*   grupo */
CREATE TABLE grupo(
anno INT,
numero INT,
curso VARCHAR(10),
numero_grupo INT,
horario VARCHAR(50),
profesor VARCHAR(10),
cupos INT,
cupos_libres INT,
CONSTRAINT pk_grupo PRIMARY KEY (anno,numero,curso,numero_grupo),
CONSTRAINT fk_grupo_curso FOREIGN KEY (curso) REFERENCES curso(codigo),
CONSTRAINT fk_grupo_profesor FOREIGN KEY (profesor) REFERENCES profesor(cedula),
CONSTRAINT fk_grupo_ciclo FOREIGN KEY (anno, numero) REFERENCES ciclo
);

CREATE OR REPLACE PROCEDURE insertar_grupo(anno IN grupo.anno%TYPE,numero IN grupo.numero%TYPE,
curso IN grupo.curso%TYPE,numero_grupo IN grupo.numero_grupo%TYPE,horario IN grupo.horario%TYPE,profesor IN grupo.profesor%TYPE,cupos IN grupo.cupos%TYPE,cupos_libres IN grupo.cupos_libres%TYPE)
AS
BEGIN
	INSERT INTO grupo VALUES(anno,numero,curso,numero_grupo,horario,profesor,cupos,cupos_libres);
END;
/

CREATE OR REPLACE PROCEDURE modificar_grupo(annoin IN grupo.anno%TYPE,numeroin IN grupo.numero%TYPE,
cursoin IN grupo.curso%TYPE,numero_grupoin IN grupo.numero_grupo%TYPE,horarioin IN grupo.horario%TYPE,profesorin IN grupo.profesor%TYPE,cuposin IN grupo.cupos%TYPE,cupos_libresin IN grupo.cupos_libres%TYPE)
AS
BEGIN
UPDATE grupo SET horario=horarioin,profesor=profesorin,cupos=cuposin,cupos_libres=cupos_libresin WHERE anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin;
END;
/

CREATE OR REPLACE FUNCTION buscar_grupo(annoin IN grupo.anno%TYPE,numeroin IN grupo.numero%TYPE,
cursoin IN grupo.curso%TYPE,numero_grupoin IN grupo.numero_grupo%TYPE)
RETURN Types.ref_cursor 
AS 
        grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
       SELECT anno,numero,curso,numero_grupo,horario,profesor,cupos,cupos_libres FROM grupo WHERE anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin; 
RETURN grupo_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listar_grupos
RETURN Types.ref_cursor 
AS 
       grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
       SELECT anno,numero,curso,numero_grupo,horario,profesor,cupos,cupos_libres FROM grupo ; 
RETURN grupo_cursor; 
END;
/

CREATE or REPLACE procedure eliminar_grupo(annoin IN grupo.anno%TYPE,numeroin IN grupo.numero%TYPE,
cursoin IN grupo.curso%TYPE,numero_grupoin IN grupo.numero_grupo%TYPE)
as
begin
    delete from grupo where anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin;
end;
/

/* evaluaciONes/lista de matriculados  */
CREATE TABLE matriculados(
anno INT,
numero INT,
curso VARCHAR(10),
numero_grupo INT,
alumno VARCHAR(10),
nota INT,
CONSTRAINT pk_matriculados PRIMARY KEY (anno,numero,curso,numero_grupo,alumno),
CONSTRAINT fk_matriculados_alumno FOREIGN KEY (alumno) REFERENCES alumno(cedula),
CONSTRAINT fk_matriculados_grupo FOREIGN KEY (anno, numero,curso,numero_grupo) REFERENCES grupo
);

CREATE OR REPLACE PROCEDURE insertar_matriculados(anno IN matriculados.anno%TYPE,numero IN matriculados.numero%TYPE,
curso IN matriculados.curso%TYPE,numero_grupo IN matriculados.numero_grupo%TYPE,alumno IN matriculados.alumno%TYPE,nota IN matriculados.nota%TYPE)
AS
BEGIN
	INSERT INTO matriculados VALUES(anno,numero,curso,numero_grupo,alumno,nota);
END;
/

CREATE OR REPLACE PROCEDURE modificar_matriculados(annoin IN matriculados.anno%TYPE,numeroin IN matriculados.numero%TYPE,
cursoin IN matriculados.curso%TYPE,numero_grupoin IN matriculados.numero_grupo%TYPE,alumnoin IN matriculados.alumno%TYPE,notain IN matriculados.nota%TYPE)
AS
BEGIN
UPDATE matriculados SET nota=notain WHERE anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin AND alumno=alumnoin; 
END;
/

CREATE OR REPLACE FUNCTION buscar_matriculados(annoin IN matriculados.anno%TYPE,numeroin IN matriculados.numero%TYPE,
cursoin IN matriculados.curso%TYPE,numero_grupoin IN matriculados.numero_grupo%TYPE,alumnoin IN matriculados.alumno%TYPE)
RETURN Types.ref_cursor 
AS 
        matriculados_cursor types.ref_cursor; 
BEGIN 
  OPEN matriculados_cursor FOR 
       SELECT anno,numero,curso,numero_grupo,alumno,nota FROM matriculados WHERE anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin AND alumno=alumnoin; 
RETURN matriculados_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listar_matriculados
RETURN Types.ref_cursor 
AS 
       matriculados_cursor types.ref_cursor; 
BEGIN 
  OPEN matriculados_cursor FOR 
       SELECT anno,numero,curso,numero_grupo,alumno,nota FROM matriculados ; 
RETURN matriculados_cursor; 
END;
/

CREATE or REPLACE procedure eliminar_matriculados(annoin IN matriculados.anno%TYPE,numeroin IN matriculados.numero%TYPE,
cursoin IN matriculados.curso%TYPE,numero_grupoin IN matriculados.numero_grupo%TYPE,alumnoin IN matriculados.alumno%TYPE)
as
begin
    delete from matriculados where anno=annoin AND numero=numeroin AND curso=curso AND numero_grupo=numero_grupoin AND alumno=alumnoin;
end;
/