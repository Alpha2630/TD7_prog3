package com.alpha.push_down_td7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DatabaseConnection dbConnection;

    public DataRetriever(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Q1 - Nombre total de votes
    public long countAllVotes() throws SQLException {
        String sql = "SELECT COUNT(id) as total_votes FROM vote";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong("total_votes");
            }
            return 0L;
        }
    }
}
