package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;
import Matricula.Logic.Group;
import java.util.ArrayList;

public class GroupsService extends Service {

    private static final String FINDGROUP = "{? = call Matricula_FindGroup(?,?)}";
    private static final String ADDGROUP = "{call Matricula_InsertGroup(?,?,?,?,?)}";
    private static final String UPDATEGROUP = "{call Matricula_UpdateGroup(?,?,?,?,?,?)}";
    private static final String DELETEGROUP = "{call Matricula_DeleteGroup(?)}";

    public GroupsService() {

    }

    public ArrayList<Group> FindGroup(int id, int status) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Group> groups = new ArrayList<Group>();

        try {
            pstmt = conexion.prepareCall(FINDGROUP);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);

            // Status = 0 specific search, status = 1 all records 
            pstmt.setInt(3, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {

                groups.add(
                        new Group(rs.getInt("id"),
                                rs.getInt("courseId"),
                                rs.getInt("groupNumber"),
                                rs.getString("schedule"),
                                rs.getInt("cicleId"),
                                rs.getInt("professorId"))
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

        return groups;

    }

    public void SaveGroup(Group group) throws GlobalException, NoDataException {
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
            if (group.getId() != 0) {
                pstmt = conexion.prepareCall(UPDATEGROUP);
                pstmt.setInt(1, group.getId());
                pstmt.setInt(2, group.getCourseId());
                pstmt.setInt(3, group.getGroupNumber());
                pstmt.setString(4, group.getSchedule());
                pstmt.setInt(5, group.getCicleId());
                pstmt.setInt(6, group.getProfessorId());
            } else {
                pstmt = conexion.prepareCall(ADDGROUP);
                pstmt.setInt(1, group.getCourseId());
                pstmt.setInt(2, group.getGroupNumber());
                pstmt.setString(3, group.getSchedule());
                pstmt.setInt(4, group.getCicleId());
                pstmt.setInt(5, group.getProfessorId());
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

    public void DeleteGroup(int id) throws GlobalException, NoDataException {
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
            pstmt = conexion.prepareCall(DELETEGROUP);
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
