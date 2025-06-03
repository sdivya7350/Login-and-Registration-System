
# 🔐 Login and Registration System

This is a **Java Swing-based desktop application** for user login and registration, connected to a **MySQL database** using **JDBC**.  
It demonstrates secure password storage using **SHA-256 encryption**, basic GUI design, and session-based dashboard navigation.

---

## 📁 Project Structure

```

loginapp/
├── DBConnection.java        # Handles database connection to MySQL
├── LoginRegisterGUI.java    # Login & Register window with password hashing
├── Dashboard.java           # Dashboard displayed after successful login
├── PasswordUtil.java        # Utility to hash passwords with SHA-256

````

---

## 🛠 How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/sdivya7350/Login-Registration-System.git
cd Login-Registration-System
````

### 2. Create MySQL Database & Table

```sql
CREATE DATABASE mydb;

USE mydb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
```

### 3. Update DB Credentials in Code

In `DBConnection.java`, set your database username and password:

```java
return DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/mydb", "root", "your_mysql_password"
);
```

---

## ▶️ How to Run the App

Compile and run using your terminal or IDE:

```bash
javac loginapp/*.java
java loginapp.LoginRegisterGUI
```

---

## 📷 Sample Output (Console and GUI)

* On app start, the **Login & Register** GUI is displayed.
* You can register a user, then login.
* After login, a **Dashboard** window appears.
* MySQL stores credentials (with SHA-256 hash).

**Sample MySQL Output:**

```sql
SELECT * FROM users;

+----+----------+------------------------------------------------------------------+
| id | username | password                                                         |
+----+----------+------------------------------------------------------------------+
|  1 | root     | 819bdce9adf97218dd97c83367f20a91c7e91945b638932544fe4b393a4cbdde |
|  2 | divya    | 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92 |
+----+----------+------------------------------------------------------------------+
```

---

## ✨ Features

* ✅ GUI-based login & registration
* ✅ Password encryption using SHA-256
* ✅ MySQL backend with JDBC
* ✅ Dashboard on successful login
* ✅ Logout functionality
* ✅ Clean separation of logic across classes

---

## 💡 Optional

You can disable SHA-256 encryption during testing by replacing the hashed password with the plain one (not recommended for real apps).

---

## 📦 Export as JAR

```bash
javac loginapp/*.java
jar cfe LoginApp.jar loginapp.LoginRegisterGUI loginapp/*.class
java -jar LoginApp.jar
```

---

## 👨‍💻 Author

**Subrahmanyam Divya**
Java Full Stack Developer in Training
GitHub: [sdivya7350](https://github.com/sdivya7350)

---

## 📄 License

This project is licensed under the **MIT License** and is intended for educational use.
```

