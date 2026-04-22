# ✈ Online Airline Reservation System
### Java Servlets · JDBC · JSP · MySQL · Apache Tomcat · MVC/DAO

---

## 📁 Eclipse Project Folder Structure

```
AirlineReservationSystem/               ← Eclipse "Dynamic Web Project" root
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── airline/
│                   ├── util/
│                   │   └── DBConnection.java          ← JDBC connection helper
│                   ├── model/
│                   │   ├── User.java
│                   │   ├── Flight.java
│                   │   └── Booking.java
│                   ├── dao/
│                   │   ├── UserDAO.java
│                   │   ├── FlightDAO.java
│                   │   └── BookingDAO.java
│                   └── servlet/
│                       ├── RegisterServlet.java
│                       ├── LoginServlet.java
│                       ├── LogoutServlet.java
│                       ├── SearchFlightServlet.java
│                       ├── BookFlightServlet.java
│                       ├── ViewBookingsServlet.java
│                       └── CancelBookingServlet.java
│
└── src/
    └── main/
        └── webapp/                     ← "WebContent" in Eclipse
            ├── index.jsp               ← Smart redirect (login or search)
            ├── css/
            │   └── style.css           ← Global stylesheet
            ├── jsp/
            │   ├── login.jsp
            │   ├── register.jsp
            │   ├── search.jsp
            │   ├── results.jsp
            │   └── bookings.jsp
            └── WEB-INF/
                ├── web.xml
                └── lib/
                    └── mysql-connector-j-8.x.x.jar   ← ⚠ YOU MUST ADD THIS
```

---

## ⚙️ Step-by-Step Setup Guide

### 1. Prerequisites
| Tool | Version |
|------|---------|
| Java JDK | 17 or 21 |
| Eclipse IDE | Eclipse IDE for Enterprise Java (2023-06+) |
| Apache Tomcat | 10.1.x (supports Jakarta EE 10 / Servlet 6.0) |
| MySQL Server | 8.0+ |
| MySQL Connector/J | 8.0+ JAR |

> ⚠️ **Tomcat 10+ uses `jakarta.servlet.*`** (not `javax.servlet.*`).  
> If you're on Tomcat 9, change all `jakarta.servlet` imports to `javax.servlet`.

---

### 2. MySQL Database Setup
```sql
-- Open MySQL Workbench or run via terminal:
mysql -u root -p < database.sql
```
Or paste the contents of `database.sql` directly into MySQL Workbench and execute.

---

### 3. Eclipse Project Setup

**a) Create the project:**
1. File → New → **Dynamic Web Project**
2. Name it: `AirlineReservationSystem`
3. Target Runtime: **Apache Tomcat 10.1**
4. Check "Generate web.xml deployment descriptor"

**b) Set source folder:**
- Right-click project → Build Path → Configure Build Path
- Source tab → ensure `src/main/java` is listed

**c) Add MySQL JDBC Driver:**
1. Download `mysql-connector-j-8.x.x.jar` from [mysql.com](https://dev.mysql.com/downloads/connector/j/)
2. Copy the JAR to: `WebContent/WEB-INF/lib/`
3. Eclipse auto-adds it to the classpath

**d) Create the package structure** under `src/main/java`:
```
com.airline.util
com.airline.model
com.airline.dao
com.airline.servlet
```

**e) Copy all files** from this project into their respective locations as shown in the folder tree above.

---

### 4. Configure Database Credentials

Open `DBConnection.java` and update:
```java
private static final String URL      = "jdbc:mysql://localhost:3306/airline_db?useSSL=false&serverTimezone=UTC";
private static final String USER     = "root";      // ← your MySQL username
private static final String PASSWORD = "your_pass"; // ← your MySQL password
```

---

### 5. Run the Project
1. Right-click project → **Run As → Run on Server**
2. Select your Tomcat 10.1 server → Finish
3. Browser opens at: `http://localhost:8080/AirlineReservationSystem/`

---

## 🔄 Application Flow

```
/index.jsp
    │
    ├─ Not logged in → /jsp/login.jsp  ──→ POST /login ──→ /jsp/search.jsp
    │                       │
    │                       └── New user? → /jsp/register.jsp ──→ POST /register
    │
    └─ Logged in → /jsp/search.jsp
                        │
                        └── POST /searchFlight ──→ /jsp/results.jsp
                                                        │
                                                        └── GET /bookFlight?flightId=X
                                                                    │
                                                                    └── redirect → GET /bookings
                                                                                    │
                                                                                    └── GET /cancelBooking?bookingId=Y
```

---

## 📋 URL Reference

| URL | Method | Description |
|-----|--------|-------------|
| `/` | GET | Redirect based on session |
| `/jsp/login.jsp` | GET | Login form |
| `/login` | POST | Process login |
| `/jsp/register.jsp` | GET | Register form |
| `/register` | POST | Process registration |
| `/logout` | GET | Invalidate session |
| `/jsp/search.jsp` | GET | Flight search form |
| `/searchFlight` | POST | Execute search → results.jsp |
| `/bookFlight?flightId=X` | GET | Book a flight |
| `/bookings` | GET | View my bookings |
| `/cancelBooking?bookingId=X` | GET | Cancel a booking |

---

## 🏗 Architecture Overview (MVC/DAO)

```
Browser  ──→  Servlet (Controller)  ──→  DAO (Data Layer)  ──→  MySQL
                    │                          │
                    └──→  JSP (View)    Model (POJO) ←──────────┘
```

| Layer | Classes |
|-------|---------|
| **View** | `*.jsp` files |
| **Controller** | `*Servlet.java` files |
| **Model** | `User.java`, `Flight.java`, `Booking.java` |
| **Data Access** | `UserDAO.java`, `FlightDAO.java`, `BookingDAO.java` |
| **Utility** | `DBConnection.java` |

---

## 🔒 Security Notes (Production Checklist)
- [ ] Hash passwords with **BCrypt** (`org.mindrot:jbcrypt`) before storing
- [ ] Use **PreparedStatements** (already done – prevents SQL injection)
- [ ] Add **CSRF tokens** to all forms
- [ ] Enable **HTTPS** on Tomcat
- [ ] Move DB credentials to environment variables or `context.xml`
- [ ] Validate and sanitize all user inputs server-side

---

## 🐛 Common Issues & Fixes

| Problem | Fix |
|---------|-----|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | JAR missing from `WEB-INF/lib/` |
| `HTTP 404` on servlet | Check `@WebServlet` URL matches the form action |
| `javax.servlet` not found | Switch to Tomcat 10+ OR change imports to `jakarta.servlet` |
| DB connection refused | MySQL not running, or wrong port/credentials in `DBConnection.java` |
| Blank page after booking | Check Tomcat console for stack trace |
