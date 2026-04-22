# FlyNow — Online Airline Reservation System

A full-stack Java web application built with Servlets, JDBC, JSP, and MySQL using the MVC/DAO architecture pattern.

Live Demo: https://airline-reservation-dzxr.onrender.com

> Note: Free Render instances spin down after inactivity. First load may take up to 50 seconds.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java Servlets (Jakarta EE) |
| Frontend | JSP, HTML, CSS |
| Database | MySQL |
| Server | Apache Tomcat 10.1 |
| Build Tool | Maven |
| Hosting (optional) | Render (Docker) + Aiven MySQL |

---

## Project Structure

```
Airline-Reservation-System/
├── Dockerfile
├── pom.xml
├── database.sql
└── src/
    └── main/
        ├── java/com/airline/
        │   ├── util/DBConnection.java
        │   ├── model/
        │   │   ├── User.java
        │   │   ├── Flight.java
        │   │   └── Booking.java
        │   ├── dao/
        │   │   ├── UserDAO.java
        │   │   ├── FlightDAO.java
        │   │   └── BookingDAO.java
        │   └── servlet/
        │       ├── RegisterServlet.java
        │       ├── LoginServlet.java
        │       ├── LogoutServlet.java
        │       ├── SearchFlightServlet.java
        │       ├── BookFlightServlet.java
        │       ├── ViewBookingsServlet.java
        │       └── CancelBookingServlet.java
        └── webapp/
            ├── index.jsp
            ├── css/style.css
            ├── jsp/
            │   ├── login.jsp
            │   ├── register.jsp
            │   ├── search.jsp
            │   ├── results.jsp
            │   └── bookings.jsp
            └── WEB-INF/web.xml
```

---

## Option 1 — Run Locally (Mac)

### Prerequisites

| Tool | Version |
|------|---------|
| Java JDK | 17 |
| Apache Tomcat | 10.1 |
| MySQL | 8.0+ |
| Maven | 3.9+ |
| VS Code | Latest |

### Step 1 — Install dependencies (Mac with Homebrew)

```bash
brew install openjdk@17
echo 'export JAVA_HOME=/opt/homebrew/opt/openjdk@17' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
source ~/.zshrc

brew install maven
brew install tomcat@10
brew install mysql
brew services start mysql
```

### Step 2 — Set up the database

```bash
mysql -u root -p
```

Once inside MySQL, paste the contents of `database.sql` to create the tables and insert sample flights. Then exit:

```sql
exit
```

### Step 3 — Configure DBConnection.java

Open `src/main/java/com/airline/util/DBConnection.java` and set your MySQL credentials:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/airline_db?useSSL=false&serverTimezone=UTC";
private static final String USER     = "root";
private static final String PASSWORD = "your_mysql_password";
```

### Step 4 — Build the project

```bash
cd /path/to/Airline-Reservation-System
mvn clean package
```

You should see `BUILD SUCCESS` and a WAR file at `target/AirlineReservationSystem.war`.

### Step 5 — Deploy and run

```bash
catalina stop
rm -rf /opt/homebrew/Cellar/tomcat@10/10.1.52/libexec/webapps/AirlineReservationSystem*
cp target/AirlineReservationSystem.war /opt/homebrew/Cellar/tomcat@10/10.1.52/libexec/webapps/
catalina start
```

Open your browser at:

```
http://localhost:8080/AirlineReservationSystem/
```

### Every time you make a code change

Run this single command to rebuild and redeploy:

```bash
cd /path/to/Airline-Reservation-System && mvn clean package && catalina stop && rm -rf /opt/homebrew/Cellar/tomcat@10/10.1.52/libexec/webapps/AirlineReservationSystem* && cp target/AirlineReservationSystem.war /opt/homebrew/Cellar/tomcat@10/10.1.52/libexec/webapps/ && catalina start
```

---

## Option 2 — Host Online (Render + Aiven)

This option keeps the app and database running 24/7 in the cloud for free.

### Step 1 — Set up Aiven MySQL

1. Go to aiven.io and create a free account
2. Click **Create Service** and select **MySQL**
3. Choose the **Free plan** and any region
4. Wait for the service status to show **Running**
5. From the **Connection Information** panel, note down the host, port, user, and password

Connect via Terminal and create the tables:

```bash
mysql --host=YOUR_AIVEN_HOST --port=YOUR_PORT --user=avnadmin --password --ssl-mode=REQUIRED
```

Once connected:

```sql
USE defaultdb;
```

Then paste the full contents of `database.sql` to create the tables and insert sample data.

### Step 2 — Update DBConnection.java

Replace the database connection details with your Aiven credentials:

```java
private static final String URL      = "jdbc:mysql://YOUR_AIVEN_HOST:YOUR_PORT/defaultdb?useSSL=true&sslMode=REQUIRED&serverTimezone=UTC";
private static final String USER     = "avnadmin";
private static final String PASSWORD = System.getenv("DB_PASSWORD");
```

The password is read from an environment variable so it is never stored in the code.

### Step 3 — Make sure the Dockerfile exists

The `Dockerfile` should be in the root of your project with this content:

```dockerfile
FROM tomcat:10.1-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/AirlineReservationSystem.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
```

### Step 4 — Build and push to GitHub

```bash
mvn clean package
git add .
git commit -m "Ready for Render deployment"
git push
```

Make sure the `target/AirlineReservationSystem.war` file is committed and visible in your GitHub repo.

### Step 5 — Deploy on Render

1. Go to render.com and log in
2. Click **New** and select **Web Service**
3. Connect your GitHub repository
4. Fill in the settings:
   - Name: `airline-reservation`
   - Language: `Docker`
   - Branch: `main`
   - Build Command: leave empty
   - Start Command: leave empty
5. Scroll to **Environment Variables** and add:

   | Key | Value |
   |-----|-------|
   | DB_PASSWORD | Your Aiven MySQL password |

6. Click **Create Web Service** and wait 3-5 minutes for the build

Your app will be live at the URL Render assigns.

### Redeploying after code changes

```bash
mvn clean package
git add .
git commit -m "describe your change"
git push
```

Render automatically detects the push and redeploys. You can also trigger it manually from the Render dashboard under **Manual Deploy**.

---

## Testing the Application

| Feature | Steps |
|---------|-------|
| Register | Click "Create an account" and fill in the form |
| Login | Use your registered email and password |
| Search flights | From: Delhi, To: Mumbai, Date: 2026-08-10 |
| Book a flight | Click Book on any search result |
| View bookings | Click My Bookings in the navbar |
| Cancel booking | Click Cancel on any confirmed booking |

---

## URL Reference

| URL | Method | Description |
|-----|--------|-------------|
| `/` | GET | Redirects based on session |
| `/jsp/login.jsp` | GET | Login form |
| `/login` | POST | Process login |
| `/jsp/register.jsp` | GET | Register form |
| `/register` | POST | Process registration |
| `/logout` | GET | End session |
| `/jsp/search.jsp` | GET | Flight search form |
| `/searchFlight` | POST | Run search, show results |
| `/bookFlight?flightId=X` | GET | Book a flight |
| `/bookings` | GET | View my bookings |
| `/cancelBooking?bookingId=X` | GET | Cancel a booking |

---

## Troubleshooting

| Problem | Fix |
|---------|-----|
| First load on Render is slow | Normal on free plan — instance sleeps after inactivity, wait ~50 seconds |
| Registration fails | Check that tables exist in the database and DBConnection URL is correct |
| Build fails on Render | Confirm `target/AirlineReservationSystem.war` is committed to GitHub |
| DB connection refused locally | Make sure MySQL is running: `brew services start mysql` |
| DB connection error on Render | Verify DB_PASSWORD environment variable is set correctly |
| Old code still showing | Hard refresh with Cmd+Shift+R or clear browser cache |
| Port 8080 already in use | Run `catalina stop` then `catalina start` |

---

## Author

beastontop-xxx — https://github.com/beastontop-xxx/Airline-Reservation-System
