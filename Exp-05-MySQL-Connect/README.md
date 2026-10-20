# Experiment 5: MySQL Database Connectivity (JDBC)

## 📌 Overview
This experiment demonstrates **Java Database Connectivity (JDBC)** using the MySQL Connector/J driver. It covers establishing a database connection via `DriverManager`, executing parameterized DML SQL queries via `PreparedStatement`, and handling SQL exceptions.

---

## 🗄️ Database Setup & Prerequisites

Before running the application, ensure MySQL Server is active and create the database schema:

```sql
-- 1. Log into your MySQL console:
-- mysql -u root -p

-- 2. Create the database:
CREATE DATABASE IF NOT EXISTS college;
USE college;

-- 3. Create the student table:
CREATE TABLE IF NOT EXISTS student (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    branch VARCHAR(50) NOT NULL
);

-- 4. Verify table:
DESCRIBE student;
```

> **Note on Credentials:** `App.java` is configured with URL `jdbc:mysql://localhost:3306/college` and user `root`. Set your password via the `DB_PASSWORD` environment variable (`set DB_PASSWORD=yourpass`) or edit `src/App.java` locally.

---

## 🏗️ Architecture & Component Flow

```
   ┌─────────────────────────────────────────────────────────────┐
   │                          Java Code                          │
   │               (Experiments/Exp-05-MySQL-Connect)            │
   │                                                             │
   │  DriverManager.getConnection("jdbc:mysql://localhost:3306") │
   │  PreparedStatement.executeUpdate()                          │
   └──────────────────────────────┬──────────────────────────────┘
                                  │
                                  ▼
   ┌─────────────────────────────────────────────────────────────┐
   │               MySQL Connector/J Driver (JAR)                │
   │            (jdbc/lib/mysql-connector-j-26.7.0.jar)          │
   └──────────────────────────────┬──────────────────────────────┘
                                  │ TCP Port 3306
                                  ▼
   ┌─────────────────────────────────────────────────────────────┐
   │                 MySQL Database Server Engine                │
   │                      (college.student)                      │
   └─────────────────────────────────────────────────────────────┘
```

---

## 🚀 How to Compile and Run

### Prerequisites
- JDK 17 or higher.
- MySQL Server 8.0+ running on port `3306`.
- The MySQL Connector JAR is located in `jdbc/lib/mysql-connector-j-26.7.0.jar`.

### Compilation & Execution Commands

```bash
cd Experiments/Exp-05-MySQL-Connect/jdbc

# 1. Compile the Java source with the MySQL connector on the classpath:
javac -cp "lib/mysql-connector-j-26.7.0.jar" -d bin src/App.java

# 2. Run the application (use ; for Windows, : for Linux/macOS):
# Windows:
java -cp "bin;lib/mysql-connector-j-26.7.0.jar" App

# Linux / macOS:
# java -cp "bin:lib/mysql-connector-j-26.7.0.jar" App
```

*Expected Output:*
```
Connected to database
```

*Verify row in MySQL:*
```sql
SELECT * FROM college.student;
```
```
+----+--------+--------+
| id | name   | branch |
+----+--------+--------+
|  5 | Aniket | CSE    |
+----+--------+--------+
```

---

## 💡 Key Concepts Covered
- **JDBC Driver Loading**: Using the type-4 pure Java driver (`com.mysql.cj.jdbc.Driver`).
- **Connection Management**: Opening connections via `DriverManager.getConnection()`.
- **PreparedStatement**: Safeguarding against SQL injection through pre-compiled queries with positional parameter binding (`?`).
- **Classpath Resolution**: Incorporating external `.jar` libraries in compilation and runtime (`-cp`).
