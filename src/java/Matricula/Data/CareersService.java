package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;
import Matricula.Logic.Career;
import java.util.ArrayList;

public class CareersService extends Service {

    private static final String FINDCAREEER = "{? = call Matricula_FindCareer(?,?,?)}";

    public CareersService() {

    }

    public ArrayList<Career> FindCareer(String code, String name, int status) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Career> careers = new ArrayList<Career>();

        try {
            pstmt = conexion.prepareCall(FINDCAREEER);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, code);
            pstmt.setString(3, name);
            
            // Status = 0 specific search, status = 1 all records 
            pstmt.setInt(4, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            
            while (rs.next()) {
                careers.add(new Career(
                        rs.getInt("id"),
                        rs.getString("code"), 
                        rs.getString("name"),
                        rs.getString("title")
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

        return careers;

    }
}
