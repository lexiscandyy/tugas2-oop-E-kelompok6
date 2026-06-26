# 🎟️ Event Management & Ticketing REST API

REST API berbasis **Java** untuk sistem **Manajemen Event dan Ticketing** menggunakan **SQLite Database**.

---

# Deskripsi Proyek

Event Management & Ticketing REST API merupakan aplikasi backend berbasis **Java** yang menyediakan layanan **REST API** untuk mengelola data **User**, **Venue**, **Event**, **Ticket**, dan **Order**. API ini memungkinkan pengguna melakukan operasi **Create, Read, Update, Delete (CRUD)** terhadap setiap entitas yang tersimpan pada database **SQLite**.

---

# Teknologi yang Digunakan

* Java
* SQLite
* JDBC (SQLite Driver)
* Java HTTP Server
* Postman
* Git & GitHub

# Cara Menjalankan Server

1. Clone Repository
   git clone [https://github.com/username/event-management-ticketing-api.git](https://github.com/lexiscandyy/tugas2-oop-E-kelompok6.git)
2. Masuk ke Folder Project
   cd event-management-ticketing-api
3. Buka Project

Buka project menggunakan IntelliJ IDEA atau IDE Java lainnya.

4. Tambahkan SQLite JDBC Driver

Pastikan library SQLite JDBC sudah ditambahkan ke project agar aplikasi dapat terhubung dengan database SQLite.

5. Jalankan Server

Jalankan file berikut:
Server.java

Apabila server berhasil dijalankan, API dapat diakses melalui:

http://localhost:8080

# Daftar Endpoint API

## User

| Method | Endpoint    | Deskripsi                     |
| ------ | ----------- | ----------------------------- |
| GET    | /users      | Mengambil seluruh data user   |
| GET    | /users/{id} | Mengambil user berdasarkan ID |
| POST   | /users      | Menambahkan user baru         |
| PUT    | /users/{id} | Memperbarui data user         |
| DELETE | /users/{id} | Menghapus user                |


## Venue

| Method | Endpoint     | Deskripsi                      |
| ------ | ------------ | ------------------------------ |
| GET    | /venues      | Mengambil seluruh data venue   |
| GET    | /venues/{id} | Mengambil venue berdasarkan ID |
| POST   | /venues      | Menambahkan venue              |
| PUT    | /venues/{id} | Memperbarui venue              |
| DELETE | /venues/{id} | Menghapus venue                |


## Event

| Method | Endpoint     | Deskripsi                      |
| ------ | ------------ | ------------------------------ |
| GET    | /events      | Mengambil seluruh data event   |
| GET    | /events/{id} | Mengambil event berdasarkan ID |
| POST   | /events      | Menambahkan event              |
| PUT    | /events/{id} | Memperbarui event              |
| DELETE | /events/{id} | Menghapus event                |


## Ticket

| Method | Endpoint      | Deskripsi                      |
| ------ | ------------- | ------------------------------ |
| GET    | /tickets      | Mengambil seluruh data tiket   |
| GET    | /tickets/{id} | Mengambil tiket berdasarkan ID |
| POST   | /tickets      | Menambahkan tiket              |
| PUT    | /tickets/{id} | Memperbarui tiket              |
| DELETE | /tickets/{id} | Menghapus tiket                |


# Struktur Proyek

```text
src/
│
├── database/
│   └── DatabaseManager.java
│
├── exception/
│   ├── BadRequestException.java
│   ├── NotFoundException.java
│   └── ValidationException.java
│
├── handler/
│   ├── EventHandler.java
│   ├── TicketHandler.java
│   ├── UserHandler.java
│   └── VenueHandler.java
│
├── model/
│   ├── Concert.java
│   ├── Event.java
│   ├── Refundable.java
│   ├── Seminar.java
│   ├── SportMatch.java
│   ├── Ticket.java
│   ├── User.java
│   └── Venue.java
│
├── repository/
│   ├── EventRepository.java
│   ├── TicketRepository.java
│   ├── UserRepository.java
│   └── VenueRepository.java
│
├── server/
│   ├── Request.java
│   ├── Response.java
│   ├── RouteHandler.java
│   └── Server.java
|
├── service/
│   ├── EventService.java
│   ├── TicketService.java
│   ├── UserService.java
│   └── VenueService.java
│
├── App.java
```

---

## Pembagian Tugas

| Anggota | NIM | Tingkat | Tanggung Jawab |
|---|---|---|---|
| Ketut Rama Indrawangsa | 2505551087 | Paling kuat coding | EventService.java, TicketService.java, EventRepository.java, TicketRepository.java, logika polymorphism, refund, kapasitas |
| I Komang Jaya Mahardika | 2505551014 | Menengah | Model: Event.java, Concert.java, Seminar.java, SportMatch.java, Refundable.java, Ticket.java |
| Andika Septianantha | 2505551163 | Menengah / pemula | User.java, Venue.java, UserRepository.java, VenueRepository.java, UserService.java, VenueService.java |
| Ahmad Ali Gasim | 2505551089 | Paling pemula | Handler: UserHandler.java, VenueHandler.java, EventHandler.java, TicketHandler.java, Exception classes, App.java routing, README, Postman testing |