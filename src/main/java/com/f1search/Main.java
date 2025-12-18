package com.f1search;

import com.f1search.dao.SearchDAO.SearchResult;
import com.f1search.service.SearchService;
import com.f1search.util.DataSeeder;
import com.f1search.util.DatabaseSetup;
import com.f1search.web.WebInterface;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   Formula 1 Searching Engine");
        System.out.println("==========================================");

        // 1. Initialize Database
        System.out.println("[Init] Setting up database...");
        DatabaseSetup.initializeDatabase();

        // 2. Seed Data
        System.out.println("[Init] Seeding mock data...");
        DataSeeder.seedData();

        // 3. Start Search Service
        SearchService searchService = new SearchService();
        Scanner scanner = new Scanner(System.in);

        // 4. Start Web Interface
        try {
            // Serve from bin/web (where we will copy resources)
            new WebInterface(searchService, "bin/web").start();
        } catch (IOException e) {
            System.err.println("Failed to start web server: " + e.getMessage());
        }

        // 5. Demo Loop
        if (args.length > 0) {
            String query = args[0];
            performSearch(searchService, query);
        } else {
            System.out.println("CLI access available. App is running. Visit localhost:8080");
            System.out.println("\nEnter search term (e.g., 'Hamilton', 'Ferrari', 'Schumacher'):");
            if (scanner.hasNextLine()) {
                String query = scanner.nextLine();
                performSearch(searchService, query);
            }
        }
    }

    private static void performSearch(SearchService service, String query) {
        System.out.println("\nSearching for: '" + query + "'");
        long startTime = System.currentTimeMillis();

        List<SearchResult> results = service.search(query);

        long endTime = System.currentTimeMillis();
        System.out.println("Found " + results.size() + " results in " + (endTime - startTime) + "ms\n");

        System.out.println("Rank | Type        | Name                  | Wins | Podiums | Score");
        System.out.println("-------------------------------------------------------------------");
        int rank = 1;
        for (SearchResult r : results) {
            System.out.printf("%-4d | %-11s | %-21s | %-4d | %-7d | %s\n",
                    rank++, r.type, r.name, r.wins, r.podiums, "Calculated");
        }
    }
}
