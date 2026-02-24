package com.alpha.push_down_td7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DatabaseConnection dbConnection;

    public DataRetriever(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


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
    public List<VoteTypeCount> countVotesByType() throws SQLException {
        List<VoteTypeCount> result = new ArrayList<>();
        String sql = "SELECT vote_type::text, COUNT(id) as count " +
                "FROM vote " +
                "GROUP BY vote_type " +
                "ORDER BY vote_type";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new VoteTypeCount(
                        rs.getString("vote_type"),
                        rs.getLong("count")
                ));
            }
        }
        return result;
    }
}
