package com.f1search.web;

import com.f1search.dao.SearchDAO.SearchResult;
import com.f1search.service.SearchService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.List;

public class WebInterface {
    private static final int PORT = 8080;
    private final SearchService searchService;
    private final String webRoot;

    public WebInterface(SearchService searchService, String webRoot) {
        this.searchService = searchService;
        this.webRoot = webRoot;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // API Endpoint
        server.createContext("/api/search", new SearchHandler());

        // Static File Server
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Web Server started on http://localhost:" + PORT);
    }

    private class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String searchTerm = "";
                if (query != null && query.startsWith("q=")) {
                    searchTerm = query.substring(2);
                }

                List<SearchResult> results = searchService.search(searchTerm);
                String jsonResponse = toJson(results);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // CORS
                exchange.sendResponseHeaders(200, jsonResponse.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(jsonResponse.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    private class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if ("/".equals(path)) {
                path = "/index.html";
            }

            File file = new File(webRoot + path);
            if (file.exists() && !file.isDirectory()) {
                String mimeType = "text/plain";
                if (path.endsWith(".html"))
                    mimeType = "text/html";
                else if (path.endsWith(".css"))
                    mimeType = "text/css";
                else if (path.endsWith(".js"))
                    mimeType = "application/javascript";

                exchange.getResponseHeaders().set("Content-Type", mimeType);
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } else {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }

    // Simple manual JSON serialization
    private String toJson(List<SearchResult> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < results.size(); i++) {
            SearchResult r = results.get(i);
            sb.append("{");
            sb.append("\"type\":\"").append(escape(r.type)).append("\",");
            sb.append("\"name\":\"").append(escape(r.name)).append("\",");
            sb.append("\"wins\":").append(r.wins).append(",");
            sb.append("\"podiums\":").append(r.podiums);
            sb.append("}");
            if (i < results.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String escape(String s) {
        if (s == null)
            return "";
        return s.replace("\"", "\\\"");
    }
}
