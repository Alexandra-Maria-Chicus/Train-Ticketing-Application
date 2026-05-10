# Train Ticketing App

A REST API built with Java and Spring Boot that allows customers to search for trains and book tickets, and administrators to manage the train network and handle delays.

## How to run

You need Java 17+ and Maven installed.

```
git clone <your-repo-link>
cd Train-Ticketing-Application
mvn spring-boot:run
```

The app starts on `http://localhost:8080`. The database is in-memory so it resets every time you restart — the predefined data gets loaded automatically on startup.

For email notifications to work, you need to set your Gmail credentials in `application.properties`:
```
spring.mail.username=your@gmail.com
spring.mail.password=your-app-password
```
To get an app password, go to your Google account → Security → 2-Step Verification → App Passwords.

## Authentication

The API uses HTTP Basic Auth. Every request needs a username and password. There are two roles — CUSTOMER and ADMIN. Admin endpoints are under `/api/admin/**` and require the ADMIN role.

Two users are pre-loaded:

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@trainticket.com | admin123 |
| Customer | john@example.com | customer123 |

## Predefined data

On startup the app loads the following data so you can test immediately without setting anything up manually.

**Stations:**

| ID | Name |
|----|------|
| 1 | Cluj-Napoca |
| 2 | Sinaia |
| 3 | Brasov |
| 4 | Ploiesti |
| 5 | Bucuresti |

**Trains:**

| ID | Name | Capacity |
|----|------|----------|
| 1 | InterCity 100 | 200 |
| 2 | Regional 200 | 150 |

**Routes:**

| ID | Name | Stops |
|----|------|-------|
| 1 | Cluj - Bucuresti | Cluj-Napoca → Brasov → Bucuresti |
| 2 | Bucuresti - Brasov | Bucuresti → Ploiesti → Sinaia → Brasov |

**Schedules:**

| ID | Train | Route | Departure |
|----|-------|-------|-----------|
| 1 | InterCity 100 | Cluj - Bucuresti | 2026-06-01 08:00 |
| 2 | InterCity 100 | Cluj - Bucuresti | 2026-06-01 14:00 |
| 3 | Regional 200 | Bucuresti - Brasov | 2026-06-01 09:00 |

## API Endpoints

### Search for travel options

Finds all possible ways to get from one station to another, including connections with changeovers. Returns a list of travel options — each option is a list of schedules (1 schedule = direct, 2 schedules = changeover).

```
GET /api/bookings/search?fromStationId=1&toStationId=5
Authorization: Basic Auth (any authenticated user)
```

Example response:
```json
[
  [
    {
      "id": 1,
      "train": { "name": "InterCity 100", "capacity": 200 },
      "route": { "name": "Cluj - Bucuresti" },
      "departureTime": "2026-06-01T08:00:00",
      "delayMinutes": 0
    }
  ],
  [
    {
      "id": 2,
      "train": { "name": "InterCity 100", "capacity": 200 },
      "route": { "name": "Cluj - Bucuresti" },
      "departureTime": "2026-06-01T14:00:00",
      "delayMinutes": 0
    }
  ]
]
```

If no route exists between the two stations:
```json
"No routes found between these stations!"
```

### Book a ticket

```
POST /api/bookings
Authorization: Basic Auth (any authenticated user)
Content-Type: application/json
```

Request body:
```json
{
  "userId": 2,
  "fromStationId": 1,
  "toStationId": 5,
  "scheduleId": 1,
  "seatCount": 2
}
```

After a successful booking, a confirmation email is sent to the customer. If the train is full, you get:
```json
"Train is fully booked"
```

---

### Admin — Stations

**Add station**
```
POST /api/admin/stations
Authorization: Basic Auth (admin only)
Content-Type: application/json

{ "name": "Constanta" }
```

**Update station**
```
PUT /api/admin/stations/{id}
Authorization: Basic Auth (admin only)
Content-Type: application/json

{ "name": "Constanta Nord" }
```

**Delete station**
```
DELETE /api/admin/stations/{id}
Authorization: Basic Auth (admin only)
```

---

### Admin — Trains

**Add train**
```
POST /api/admin/trains
Authorization: Basic Auth (admin only)
Content-Type: application/json

{ "name": "Express 300", "capacity": 250 }
```

**Update train**
```
PUT /api/admin/trains/{id}
Authorization: Basic Auth (admin only)
Content-Type: application/json

{ "name": "Express 300", "capacity": 300 }
```

**Delete train**
```
DELETE /api/admin/trains/{id}
Authorization: Basic Auth (admin only)
```

**Get bookings for a train**
```
GET /api/admin/trains/{id}/bookings
Authorization: Basic Auth (admin only)
```

---

### Admin — Routes

**Add route**
```
POST /api/admin/routes
Authorization: Basic Auth (admin only)
Content-Type: application/json

{
  "name": "Cluj - Constanta",
  "stops": [
    { "stationId": 1, "arrivalOffset": 0, "departureOffset": 0 },
    { "stationId": 5, "arrivalOffset": 360, "departureOffset": 370 },
    { "stationId": 6, "arrivalOffset": 480, "departureOffset": 480 }
  ]
}
```

The `arrivalOffset` and `departureOffset` are in minutes from the schedule's departure time.

**Update route**
```
PUT /api/admin/routes/{id}
Authorization: Basic Auth (admin only)
Content-Type: application/json

(same body as add route)
```

**Delete route**
```
DELETE /api/admin/routes/{id}
Authorization: Basic Auth (admin only)
```

---

### Admin — Schedules

**Add schedule**
```
POST /api/admin/schedules
Authorization: Basic Auth (admin only)
Content-Type: application/json

{
  "trainId": 1,
  "routeId": 1,
  "departureTime": "2026-06-02T10:00:00"
}
```

**Update schedule**
```
PUT /api/admin/schedules/{id}
Authorization: Basic Auth (admin only)
Content-Type: application/json

{
  "trainId": 1,
  "routeId": 1,
  "departureTime": "2026-06-02T12:00:00"
}
```

**Delete schedule**
```
DELETE /api/admin/schedules/{id}
Authorization: Basic Auth (admin only)
```

**Set delay**

When a delay is set, all customers who have booked that schedule automatically receive an email notification.

```
PUT /api/admin/schedules/{id}/delay?delayMinutes=30
Authorization: Basic Auth (admin only)
```
