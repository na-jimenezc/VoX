package com.vox.proyecto.DatabaseManagement;

//import com.vox.proyecto.DatabaseManagement.DataBaseConnection;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    // Conectar a la base de datos
    public Connection connect() throws SQLException {
        return DataBaseConnection.getConnection();
    }

    // Cerrar la conexión a la base de datos
    public void closeConnection(Connection connection, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}