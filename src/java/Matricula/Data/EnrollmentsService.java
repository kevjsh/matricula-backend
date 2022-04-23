package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;
import Matricula.Logic.Enrollment;
import Matricula.Logic.Group;
import Matricula.Logic.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.EncodeException;

public class EnrollmentsService extends Service {

    private static final String FINDENROLLMENT = "{? = call Matricula_FindEnrollment(?,?)}";
    private static final String ADDENROLLMENT = "{call Matricula_InsertEnrollment(?,?,?)}";
    private static final String UPDATEENROLLMENT = "{call Matricula_UpdateEnrollment(?,?,?,?)}";
    private static final String DELETEENROLLMENT = "{call Matricula_DeleteEnrollment(?)}";

    public EnrollmentsService() {

    }

    public ArrayList<Enrollment> FindEnrollment(int id, int status) throws GlobalException, NoDataException, IOException, EncodeException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();

        try {
            pstmt = conexion.prepareCall(FINDENROLLMENT);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);

            // Status = 0 specific search, status = 1 all records 
            pstmt.setInt(3, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            GroupsService groups = new GroupsService();
            UsersService users = new UsersService();

            while (rs.next()) {

                Group group = groups.FindGroup(rs.getInt("groupId"), 0).get(0);
                User user = users.FindUser(rs.getInt("userId"), null, 0).get(0);

                enrollments.add(
                        new Enrollment(
                                rs.getInt("id"),
                                group,
                                user,
                                rs.getInt("grade"))
                );
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

        return enrollments;

    }

    public void SaveEnrollment(Enrollment enrollment) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("no se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (enrollment.getId() != 0) {
                pstmt = conexion.prepareCall(UPDATEENROLLMENT);
                pstmt.setInt(1, enrollment.getId());
                pstmt.setInt(2, enrollment.getGroup().getId());
                pstmt.setInt(3, enrollment.getUser().getId());
                pstmt.setInt(4, enrollment.getGrade());
            } else {
                pstmt = conexion.prepareCall(ADDENROLLMENT);
                pstmt.setInt(1, enrollment.getGroup().getId());
                pstmt.setInt(2, enrollment.getUser().getId());
                pstmt.setInt(3, enrollment.getGrade());
            }

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

    public void DeleteEnrollment(int id) throws GlobalException, NoDataException {
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
            pstmt = conexion.prepareCall(DELETEENROLLMENT);
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
