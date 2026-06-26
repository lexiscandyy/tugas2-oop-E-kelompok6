# 🎫 Event & Ticketing Management REST API

REST API berbasis Java untuk sistem **Event & Ticketing Management** yang dibuat sebagai tugas mata kuliah **Pemrograman Berorientasi Objek (OOP)**.

API ini digunakan untuk mengelola data **User**, **Venue**, **Event**, dan **Ticket**, serta menerapkan konsep-konsep OOP seperti:

- Abstract Class
- Interface
- Inheritance
- Polymorphism
- Encapsulation
- Exception Handling

Database menggunakan **SQLite**, sedangkan komunikasi data menggunakan format **JSON**.

---

# 📦 Teknologi yang Digunakan

- Java 11
- SQLite JDBC
- Jackson Databind
- HTTP Server (Template Dosen)

---

# 🚀 Cara Menjalankan Project

## 1. Clone Repository

```bash
git clone https://github.com/lexiscandyy/tugas2-oop-E-kelompok6.git
```

Masuk ke folder project

```bash
cd tugas2-oop-E-kelompok6
```

---

## 2. Pastikan Library Sudah Ada

Folder `lib/` harus berisi:

```
jackson-annotations
jackson-core
jackson-databind
sqlite-jdbc
```

---

## 3. Compile Project

### Windows

```bash
cd src

javac -cp ".;../lib/*" App.java model/*.java service/*.java repository/*.java handler/*.java exception/*.java database/*.java server/*.java
```

### Linux / Mac

```bash
cd src

javac -cp ".:../lib/*" App.java model/*.java service/*.java repository/*.java handler/*.java exception/*.java database/*.java server/*.java
```

---

## 4. Jalankan Server

### Windows

```bash
java -cp ".;../lib/*" App
```

### Linux / Mac

```bash
java -cp ".:../lib/*" App
```

Apabila berhasil, server akan berjalan pada

```
http://localhost:8080
```

Gunakan **Postman** atau aplikasi serupa untuk menguji seluruh endpoint.

---

# 📁 Struktur Project

```
src
│
├── App.java
│
├── database
│   └── DatabaseManager.java
│
├── exception
│   ├── EventNotFoundException.java
│   ├── RefundNotAllowedException.java
│   └── TicketSoldOutException.java
│
├── handler
│   ├── EventHandler.java
│   ├── TicketHandler.java
│   ├── UserHandler.java
│   └── VenueHandler.java
│
├── model
│   ├── Event.java
│   ├── Concert.java
│   ├── Seminar.java
│   ├── SportMatch.java
│   ├── Ticket.java
│   ├── User.java
│   ├── Venue.java
│   └── Refundable.java
│
├── repository
│   ├── EventRepository.java
│   ├── TicketRepository.java
│   ├── UserRepository.java
│   └── VenueRepository.java
│
├── service
│   ├── EventService.java
│   ├── TicketService.java
│   ├── UserService.java
│   └── VenueService.java
│
└── server
    ├── Request.java
    ├── Response.java
    ├── RouteHandler.java
    └── Server.java
```

---

# 📌 API Endpoint

## 👤 User

| Method | Endpoint |
|---------|----------|
| GET | `/api/users` |
| GET | `/api/users/{id}` |
| POST | `/api/users` |
| PUT | `/api/users/{id}` |

### Contoh Request

POST `/api/users`

```json
{
  "name":"John Doe",
  "email":"john@email.com",
  "phone":"08123456789",
  "role":"buyer"
}
```

### Contoh Response

```json
{
  "status":"success",
  "data":{
    "id":"USR-001",
    "name":"John Doe",
    "email":"john@email.com",
    "phone":"08123456789",
    "role":"buyer"
  }
}
```

---

## 🏟 Venue

| Method | Endpoint |
|---------|----------|
| GET | `/api/venues` |
| GET | `/api/venues/{id}` |
| POST | `/api/venues` |
| PUT | `/api/venues/{id}` |

### Contoh Request

POST `/api/venues`

```json
{
  "name":"GWK Cultural Park",
  "address":"Bali",
  "maxCapacity":8000
}
```

### Contoh Response

```json
{
  "status":"success",
  "data":{
    "id":"VNU-001",
    "name":"GWK Cultural Park",
    "address":"Bali",
    "maxCapacity":8000
  }
}
```

---

## 🎤 Event

| Method | Endpoint |
|---------|----------|
| GET | `/api/events` |
| GET | `/api/events/{id}` |
| POST | `/api/events` |
| PUT | `/api/events/{id}` |

### Contoh Request

POST `/api/events`

```json
{
  "type":"concert",
  "name":"Bali Music Festival",
  "venueId":"VNU-001",
  "organizerId":"USR-001",
  "date":"2026-08-15",
  "basePrice":250000
}
```

### Contoh Response

```json
{
  "status":"success",
  "data":{
    "id":"EVT-001",
    "type":"concert",
    "name":"Bali Music Festival",
    "date":"2026-08-15",
    "basePrice":250000
  }
}
```

---

## 🎫 Ticket

| Method | Endpoint |
|---------|----------|
| GET | `/api/tickets` |
| GET | `/api/tickets/{id}` |
| POST | `/api/tickets` |
| PUT | `/api/tickets/{id}/refund` |

### Contoh Request

POST `/api/tickets`

```json
{
  "eventId":"EVT-001",
  "userId":"USR-001",
  "category":"vip",
  "quantity":2
}
```

### Contoh Response

```json
{
  "status":"success",
  "data":{
    "id":"TKT-001",
    "category":"vip",
    "quantity":2,
    "unitPrice":750000,
    "totalPrice":1500000,
    "status":"active"
  }
}
```

---

## 📊 Report

| Method | Endpoint |
|---------|----------|
| GET | `/api/events/price-summary` |
| GET | `/api/reports/sales?eventId={id}` |

### Contoh Response

```json
{
  "status":"success",
  "data":{
    "totalQuantity":120,
    "totalRevenue":30000000,
    "refundCount":5
  }
}
```

---

# 📄 Format Response

## Success

```json
{
  "status":"success",
  "data":{}
}
```

## Error

```json
{
  "status":"error",
  "message":"Error message"
}
```

---

# 💡 Konsep OOP yang Diterapkan

- Abstract Class (`Event`)
- Interface (`Refundable`)
- Inheritance (`Concert`, `Seminar`, `SportMatch`)
- Polymorphism
- Encapsulation
- Exception Handling

---

## Pembagian Tugas

| Anggota | NIM | Tingkat | Tanggung Jawab |
|---|---|---|---|
| Ketut Rama Indrawangsa | 2505551087 | Hard | EventService.java, TicketService.java, EventRepository.java, TicketRepository.java, logika polymorphism, refund, kapasitas |
| I Komang Jaya Mahardika | 2505551014 | Medium | Model: Event.java, Concert.java, Seminar.java, SportMatch.java, Refundable.java, Ticket.java |
| Andika Septianantha | 2505551163 | Normal | User.java, Venue.java, UserRepository.java, VenueRepository.java, UserService.java, VenueService.java |
| Ahmad Ali Gasim | 2505551089 | Easy | Handler: UserHandler.java, VenueHandler.java, EventHandler.java, TicketHandler.java, Exception classes, App.java routing, README, Postman testing |
---

# 📄 Lisensi

Project ini dibuat untuk memenuhi Tugas 2 Mata Kuliah Pemrograman Berorientasi Objek.
