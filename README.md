# Formula 1 Historical Records Search Engine 🏎️

A high-performance, Java-based search engine for querying historical Formula 1 data. This project features a custom-built indexing and ranking algorithm, a normalized SQLite database, and a modern Web UI.

![F1 Search Engine UI](https://via.placeholder.com/800x400?text=F1+Search+Engine+Preview)

## 🚀 Features

- **Ranked Search**: Custom algorithm scores results based on keyword relevance, wins, and podiums.
- **Dual Interface**:
  - **Web UI**: Modern, responsive dark-mode interface.
  - **CLI**: Fast command-line search tool.
- **Data Analysis**: Aggregates driver and constructor statistics (wins, podiums).
- **Zero-Dependency**: Built with standard Java libraries (and SQLite JDBC).

## 🛠️ Tech Stack

- **Language**: Java 17+
- **Database**: SQLite
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Architecture**: DAO Pattern, Service Layer, Custom HTTP Server

## 📦 Installation

Prerequisites:
- Java JDK 17 or higher
- Git

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/F1-Historical-Search-Engine.git
cd F1-Historical-Search-Engine
```

### 2. Compile the Project
Since this project uses a manual dependency setup (no Maven/Gradle required for running), use the following command to compile:

**Windows (PowerShell):**
```powershell
# Create bin directory
mkdir bin -Force

# Compile sources
javac -cp "lib/sqlite-jdbc.jar;lib/gson-2.10.1.jar" -d bin src/main/java/com/f1search/config/*.java src/main/java/com/f1search/model/*.java src/main/java/com/f1search/dao/*.java src/main/java/com/f1search/service/*.java src/main/java/com/f1search/util/*.java src/main/java/com/f1search/web/*.java src/main/java/com/f1search/Main.java

# Copy resources
Copy-Item src/main/resources/schema.sql bin/schema.sql
Copy-Item -Recurse -Force src/main/resources/web bin/
```

## 🏁 Running the Application

### Option A: Web Interface (Recommended)
Starts the web server on port 8080.

```powershell
java -cp "bin;lib/sqlite-jdbc.jar;lib/gson-2.10.1.jar" com.f1search.Main
```

- Open your browser to: **[http://localhost:8080](http://localhost:8080)**

### Option B: Command Line Search
Run a quick search directly from the terminal.

```powershell
java -cp "bin;lib/sqlite-jdbc.jar;lib/gson-2.10.1.jar" com.f1search.Main "Hamilton"
```

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/f1search/
│   │   ├── config/      # Database Connection
│   │   ├── dao/         # Data Access Objects (SQL)
│   │   ├── model/       # Domain Entities (Driver, Race, etc.)
│   │   ├── service/     # Search Logic & Ranking Engine
│   │   ├── util/        # Data Seeder & DB Setup
│   │   ├── web/         # HTTP Server Implementation
│   │   └── Main.java    # Application Entry Point
│   └── resources/
│       ├── schema.sql   # Database Schema
│       └── web/         # Frontend Assets (HTML/CSS/JS)
lib/
└── sqlite-jdbc.jar      # JDBC Driver
```

## 📝 License
This project is open source and available for educational purposes.
