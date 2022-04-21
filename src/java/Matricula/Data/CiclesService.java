package Matricula.Data;

import Matricula.Excepciones.GlobalException;
import Matricula.Excepciones.NoDataException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;
import Matricula.Logic.Cicle;
import java.util.ArrayList;

public class CiclesService extends Service {

    private static final String FINDCICLE = "{? = call Matricula_FindCicle(?,?)}";
    private static final String ADDCICLE = "{call Matricula_InsertCicle(?,?,?,?)}";
    private static final String UPDATECICLE = "{call Matricula_UpdateCicle(?,?,?,?,?)}";
    private static final String DELETECICLE = "{call Matricula_DeleteCicle(?)}";
    
    public CiclesService() {

    }

    public ArrayList<Cicle> FindCicle(int id, int status) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        CallableStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Cicle> cicles = new ArrayList<Cicle>();

        try {
            pstmt = conexion.prepareCall(FINDCICLE);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, id);

            // Status = 0 specific search, status = 1 all records 
            pstmt.setInt(3, status);

            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {

                cicles.add(
                        new Cicle(rs.getInt("id"),
                                rs.getInt("year"),
                                rs.getInt("cicleNumber"),
                                rs.getDate("initDate"),
                                rs.getDate("finishDate"))
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

        return cicles;

    }
    
    public void saveCicle(Cicle cicle) throws GlobalException, NoDataException{
          try{
            conectar();
        }catch(ClassNotFoundException ex){
            throw new GlobalException("no se ha localizado el Driver");
        }catch (SQLException e){
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        
        CallableStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            if(cicle.getId() != 0){
                pstmt = conexion.prepareCall(UPDATECICLE);
                pstmt.setInt(1, cicle.getId());
                pstmt.setInt(2, cicle.getYear());
                pstmt.setInt(3, cicle.getCicleNumber());
                pstmt.setDate(4, cicle.getInitDate());
                pstmt.setDate(5, cicle.getFinishDate());
            }else{
                pstmt = conexion.prepareCall(ADDCICLE);
                pstmt.setInt(1, cicle.getYear());
                pstmt.setInt(2, cicle.getCicleNumber());
                pstmt.setDate(3, cicle.getInitDate());
                pstmt.setDate(4, cicle.getFinishDate());
            }
            
            pstmt.execute();
            
        }catch(SQLException e){
             throw new GlobalException("Sentencia no valida");
        }finally {
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
    
    public void DeleteCicle(int id) throws GlobalException, NoDataException{
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
            pstmt = conexion.prepareCall(DELETECICLE);
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
