package com.alpha.push_down_td7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/vote_db";
    private static final String DB_USER = "vote_db_user";
    private static final String DB_PASSWORD = "12345678";

    private Connection connection;  // 👈 Variable d'instance manquante

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Driver PostgreSQL chargé");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver PostgreSQL non trouvé!");
        }
    }

    public DBConnection() {
        // Constructeur par défaut
    }

    public DBConnection(String url, String user, String password) {
        // Ce constructeur existe mais n'utilise pas les paramètres
        // Si vous voulez utiliser des paramètres dynamiques, modifiez les constantes
        System.out.println("⚠️ Constructeur avec paramètres - utilisation des constantes par défaut");
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Connexion établie à " + DB_URL);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("🔌 Connexion fermée");
                }
            } catch (SQLException e) {
                System.err.println("❌ Erreur fermeture: " + e.getMessage());
            }
        }
    }

    // Méthode utilitaire pour vérifier l'état
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}