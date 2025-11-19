import java.sql.*;
import java.util.Scanner;

public class Proyecto_ROGER {
    
    private static Connection conn;
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== MENU ONE PIECE =====");
            System.out.println("1. Conectar a la base de datos");
            System.out.println("2. DML (INSERT, UPDATE, DELETE)");
            System.out.println("3. Consultas (LIKE, JOIN, GROUP BY)");
            System.out.println("4. Procedimientos y funciones");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer
            
            switch (opcion) {
                case 1 -> conectarBD();
                case 2 -> menuDML();
                case 3 -> menuConsultas();
                case 4 -> menuProcedimientosFunciones();
                case 5 -> cerrarConexion();
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    // ------------------ CONEXIÓN ------------------
    private static void conectarBD() {
        try {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Ya hay una conexión activa.");
                return;
            }
            String url = "jdbc:mysql://localhost:3306/ONEPIECE";
            String user = "root";
            String pass = "1234"; 
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado correctamente a la base de datos ONEPIECE.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    // ------------------ MENÚ DML ------------------
    private static void menuDML() {
        if (!conexionActiva()) return;
        int opcion;
        System.out.println("\n--- DML ---");
        System.out.println("1. INSERT Pirata");
        System.out.println("2. UPDATE Pirata");
        System.out.println("3. DELETE Pirata");
        System.out.print("Elige opción: ");
        opcion = sc.nextInt(); sc.nextLine();

        try (Statement st = conn.createStatement()) {
            switch (opcion) {
                case 1 -> {
                    String sqlInsert = """
                        INSERT INTO PIRATA (NOMBRE, REINO_ORIGEN, ID_TRIPULACION, ID_FRUTA, ID_ARMA)
                        VALUES ('Nuevo Pirata', 'East Blue', 1, NULL, NULL)
                    """;
                    st.executeUpdate(sqlInsert);
                    System.out.println("Pirata insertado.");
                }
                case 2 -> {
                    String sqlUpdate = "UPDATE PIRATA SET NOMBRE = 'Pirata Actualizado' WHERE ID_PIRATA = 1";
                    st.executeUpdate(sqlUpdate);
                    System.out.println("Pirata actualizado.");
                }
                case 3 -> {
                    String sqlDelete = "DELETE FROM PIRATA WHERE NOMBRE = 'Nuevo Pirata'";
                    st.executeUpdate(sqlDelete);
                    System.out.println("Pirata eliminado.");
                }
                default -> System.out.println("Opción inválida.");
            }
        } catch (SQLException e) {
            System.out.println("Error en DML: " + e.getMessage());
        }
    }

    // ------------------ MENÚ CONSULTAS ------------------
    private static void menuConsultas() {
        if (!conexionActiva()) return;
        int opcion;
        System.out.println("\n--- CONSULTAS ---");
        System.out.println("1. LIKE");
        System.out.println("2. JOIN");
        System.out.println("3. GROUP BY");
        System.out.print("Elige opción: ");
        opcion = sc.nextInt(); sc.nextLine();

        try (Statement st = conn.createStatement()) {
            String query = "";
            switch (opcion) {
                case 1 -> query = "SELECT * FROM PIRATA WHERE NOMBRE LIKE 'M%'";
                case 2 -> query = """
                    SELECT p.NOMBRE AS PIRATA, t.NOMBRE AS TRIPULACION
                    FROM PIRATA p JOIN TRIPULACION t ON p.ID_TRIPULACION = t.ID_TRIPULACION
                """;
                case 3 -> query = """
                    SELECT t.NOMBRE AS TRIPULACION, COUNT(p.ID_PIRATA) AS NUMERO_PIRATAS
                    FROM PIRATA p JOIN TRIPULACION t ON p.ID_TRIPULACION = t.ID_TRIPULACION
                    GROUP BY t.NOMBRE
                """;
                default -> {
                    System.out.println("Opción inválida.");
                    return;
                }
            }

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\n--- RESULTADOS ---");
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "  ");
                }
                System.out.println();
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error en consulta: " + e.getMessage());
        }
    }

    // ------------------ MENÚ PROCEDIMIENTOS/FUNCIONES ------------------
    private static void menuProcedimientosFunciones() {
        if (!conexionActiva()) return;
        int opcion;
        System.out.println("\n--- PROCEDIMIENTOS Y FUNCIONES ---");
        System.out.println("1. Llamar procedimiento insertar_fruta()");
        System.out.println("2. Llamar función nombre_tripulacion()");
        System.out.print("Elige opción: ");
        opcion = sc.nextInt(); sc.nextLine();

        try {
            switch (opcion) {
                case 1 -> {
                    CallableStatement cs = conn.prepareCall("{CALL insertar_fruta(?, ?, ?)}");
                    cs.setString(1, "Moku Moku no Mi");
                    cs.setString(2, "Logia");
                    cs.setString(3, "Controlar el humo");
                    cs.execute();
                    System.out.println("Fruta insertada correctamente.");
                    cs.close();
                }
                case 2 -> {
                    CallableStatement cs = conn.prepareCall("{? = CALL nombre_tripulacion(?)}");
                    cs.registerOutParameter(1, Types.VARCHAR);
                    cs.setInt(2, 4);
                    cs.execute();
                    System.out.println("Tripulacion: " + cs.getString(1));
                    cs.close();
                }
                default -> System.out.println("Opcion invalida.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar procedimiento/función: " + e.getMessage());
        }
    }

    // ------------------ CERRAR CONEXIÓN ------------------
    private static void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexion cerrada correctamente.");
            } else {
                System.out.println("No hay conexion activa.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion: " + e.getMessage());
        }
    }

    private static boolean conexionActiva() {
        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("No hay conexion activa. Usa la opcion 1 primero.");
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
