package com.mycompany.onepiece;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BBDD {

    private static Connection conexion = null;

    private static final String URL = "jdbc:mysql://localhost:3306/onepiece2";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection conectar() {
        if (conexion != null) {
            return conexion;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
            JOptionPane.showMessageDialog(null, "Conectado correctamente a la BBDD OnePiece2");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    public static void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                JOptionPane.showMessageDialog(null, "Conexión cerrada correctamente.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al desconectar: " + ex.getMessage());
            }
        }
    }

    public static Connection getConexion() {
           if (conexion == null) {
        conectar();  // Se asegura de abrir la conexión si está cerrada
    }
    return conexion;
    }
}
