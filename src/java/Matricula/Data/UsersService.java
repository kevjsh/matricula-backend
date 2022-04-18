package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import Matricula.Logic.User;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.internal.OracleTypes;

public class UsersService extends Service {

    private static final String FINDUSER = "{? = call Matricula_FindUser(?,?,?, ?)}";

    public UsersService() {

    }

    public ArrayList<User> FindUser(String personId, String name, int careerId, int status) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<User> users = new ArrayList<User>();

        try {
            pstmt = conexion.prepareCall(FINDUSER);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, personId);
            pstmt.setString(3, name);
            pstmt.setInt(4, careerId);
            
            // Status = 0 specific search, status = 1 all records
            pstmt.setInt(5, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {

                users.add(
                        new User(rs.getInt("id"),
                                rs.getString("personId"),
                                rs.getString("name"),
                                rs.getInt("telephone"),
                                rs.getDate("bithday"),
                                rs.getInt("careerId"),
                                rs.getInt("roleId"),
                                rs.getString("email"),
                                rs.getString("password")
                        )
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

        return users;
    }
}
