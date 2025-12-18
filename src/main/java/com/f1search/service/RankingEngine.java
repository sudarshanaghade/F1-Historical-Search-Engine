package com.f1search.service;

import com.f1search.dao.SearchDAO.SearchResult;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingEngine {

    public void rankResults(List<SearchResult> results, String query) {
        Collections.sort(results, new ResultComparator(query));
    }

    private static class ResultComparator implements Comparator<SearchResult> {
        private String query;

        public ResultComparator(String query) {
            this.query = query.toLowerCase();
        }

        @Override
        public int compare(SearchResult r1, SearchResult r2) {
            int score1 = calculateScore(r1);
            int score2 = calculateScore(r2);
            return Integer.compare(score2, score1); // Descending order
        }

        private int calculateScore(SearchResult result) {
            int score = 0;
            String name = result.name.toLowerCase();

            // 1. Relevance Score
            if (name.equals(query)) {
                score += 50; // Exact match huge bonus
            } else if (name.startsWith(query)) {
                score += 20; // Starts with bonus
            } else if (name.contains(query)) {
                score += 10; // Contains match
            }

            // 2. Success Score (Weighted)
            score += (result.wins * 2); // 2 points per win
            score += (result.podiums * 1); // 1 point per podium

            return score;
        }
    }
}
