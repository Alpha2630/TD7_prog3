package com.alpha.push_down_td7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.List;
@SpringBootApplication
public class PushDownTd7Application {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(
                "jdbc:postgresql://localhost:5432/vote_db",
                "postgres",
                "password"
        );

        DataRetriever retriever = new DataRetriever(dbConnection);

        try {
            // Q1 - Nombre total de votes
            System.out.println("=== Q1: Nombre total de votes ===");
            long totalVotes = retriever.countAllVotes();
            System.out.println("totalVote=" + totalVotes);
            System.out.println();

            // Q2 - Votes par type
            System.out.println("=== Q2: Votes par type ===");
            List<VoteTypeCount> votesByType = retriever.countVotesByType();
            System.out.println(votesByType);
            System.out.println();

            // Q3 - Votes valides par candidat
            System.out.println("=== Q3: Votes valides par candidat ===");
            List<CandidateVoteCount> votesByCandidate = retriever.countValidVotesByCandidate();
            System.out.println(votesByCandidate);
            System.out.println();

            // Q4 - Synthèse globale
            System.out.println("=== Q4: Synthèse globale ===");
            VoteSummary summary = retriever.computeVoteSummary();
            System.out.println(summary);
            System.out.println();

            // Q5 - Taux de participation
            System.out.println("=== Q5: Taux de participation ===");
            double turnoutRate = retriever.computeTurnoutRate();
            System.out.println("Taux de participation = " + turnoutRate + "%");
            System.out.println();

            // Q6 - Résultat élection
            System.out.println("=== Q6: Résultat élection ===");
            ElectionResult winner = retriever.findWinner();
            System.out.println(winner);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }

}
