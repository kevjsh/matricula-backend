package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;
import Matricula.Logic.Course;
import java.util.ArrayList;

public class CoursesService extends Service {

    private static final String ADDCOURSE = "{call Matricula_InsertCourse(?,?,?,?,?)}";
    private static final String FINDCOURSE = "{? = call Matricula_FindCourse(?,?,?,?,?)}";
    private static final String UPDATECOURSE = "{call Matricula_UpdateCourse(?,?,?,?,?,?)}";
    private static final String DELETECOURSE = "{call Matricula_DeleteCourse(?)}";

    public CoursesService() {

    }
    
    public void AddCourse(Course course) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexion.prepareCall(ADDCOURSE);
            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setInt(4, course.getHours());
            pstmt.setInt(5, course.getCareerId());

            pstmt.execute();
            
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

    }

    public ArrayList<Course> FindCourse(int id, String code, String name, int careerId, int status) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            pstmt = conexion.prepareCall(FINDCOURSE);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);
            pstmt.setString(3, code);
            pstmt.setString(4, name);
            pstmt.setInt(5, careerId);
            
            // Status = 0 specific search, status = 1 all records 
            pstmt.setInt(6, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("code"), 
                        rs.getString("name"), 
                        rs.getInt("credits"), 
                        rs.getInt("hours"), 
                        rs.getInt("careerId")
                ));
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

        return courses;

    }
    
    public void UpdateCourse(Course course) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexion.prepareCall(UPDATECOURSE);
            pstmt.setInt(1, course.getId());
            pstmt.setString(2, course.getCode());
            pstmt.setString(3, course.getName());
            pstmt.setInt(4, course.getCredits());
            pstmt.setInt(5, course.getHours());
            pstmt.setInt(6, course.getCareerId());

            System.out.println(course);
            
            pstmt.execute();
            
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

    }
    
    public void DeleteCourse(int id) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conexion.prepareCall(DELETECOURSE);
            pstmt.setInt(1, id);

            pstmt.execute();
            
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

    }
    
    
    
}
