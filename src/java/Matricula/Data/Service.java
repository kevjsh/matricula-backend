package Matricula.Data;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

public class Service {

    protected Connection conexion = null;

    public Service() {
    }

    protected void conectar() throws SQLException, ClassNotFoundException {

        try {
            OracleDataSource oracle = new OracleDataSource();
            oracle.setURL("jdbc:oracle:thin:@localhost:1521:XE");
            oracle.setUser("system");
            oracle.setPassword("root");

            conexion = oracle.getConnection();
        } catch (SQLException e) {
            System.out.println("The exception raised is: " + e);
        }
    }

    protected void desconectar() throws SQLException {
        if (!conexion.isClosed()) {
            conexion.close();
        }
    }
}
