package com.f1search.service;

import com.f1search.dao.SearchDAO;
import com.f1search.dao.SearchDAO.SearchResult;
import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private SearchDAO searchDAO;
    private RankingEngine rankingEngine;

    public SearchService() {
        this.searchDAO = new SearchDAO();
        this.rankingEngine = new RankingEngine();
    }

    public List<SearchResult> search(String query) {
        List<SearchResult> results = new ArrayList<>();

        // 1. Search Drivers
        results.addAll(searchDAO.searchDriversWithStats(query));

        // 2. Search Constructors
        results.addAll(searchDAO.searchConstructorsWithStats(query));

        // 3. Rank Results
        rankingEngine.rankResults(results, query);

        return results;
    }
}
