package com.alpha.push_down_td7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DBConnection dbConnection;

    public DataRetriever(DBConnection dbConnection) {
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
    public List<CandidateVoteCount> countValidVotesByCandidate() throws SQLException {
        List<CandidateVoteCount> result = new ArrayList<>();
        String sql = "SELECT c.name as candidate_name, " +
                "COUNT(CASE WHEN v.vote_type = 'VALID' THEN v.id END) as valid_vote " +
                "FROM candidate c " +
                "LEFT JOIN vote v ON c.id = v.candidate_id " +
                "GROUP BY c.id, c.name " +
                "ORDER BY c.name";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new CandidateVoteCount(
                        rs.getString("candidate_name"),
                        rs.getLong("valid_vote")
                ));
            }
        }
        return result;
    }

    public VoteSummary computeVoteSummary() throws SQLException {
        String sql = "SELECT " +
                "COUNT(CASE WHEN vote_type = 'VALID' THEN id END) as valid_count, " +
                "COUNT(CASE WHEN vote_type = 'BLANK' THEN id END) as blank_count, " +
                "COUNT(CASE WHEN vote_type = 'NULL' THEN id END) as null_count " +
                "FROM vote";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new VoteSummary(
                        rs.getLong("valid_count"),
                        rs.getLong("blank_count"),
                        rs.getLong("null_count")
                );
            }
            return new VoteSummary(0L, 0L, 0L);
        }
    }


    public double computeTurnoutRate() throws SQLException {
        String sql = "SELECT " +
                "(SELECT COUNT(id) FROM vote)::float / " +
                "(SELECT COUNT(id) FROM voter) * 100.0 as turnout_rate";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("turnout_rate");
            }
            return 0.0;
        }
    }
    public ElectionResult findWinner() throws SQLException {
        String sql = "SELECT c.name as candidate_name, " +
                "COUNT(CASE WHEN v.vote_type = 'VALID' THEN v.id END) as valid_vote_count " +
                "FROM candidate c " +
                "LEFT JOIN vote v ON c.id = v.candidate_id " +
                "GROUP BY c.id, c.name " +
                "ORDER BY valid_vote_count DESC " +
                "LIMIT 1";

        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new ElectionResult(
                        rs.getString("candidate_name"),
                        rs.getLong("valid_vote_count")
                );
            }
            return new ElectionResult("Aucun candidat", 0L);
        }
    }
}
