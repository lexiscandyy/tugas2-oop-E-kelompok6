# 🎟️ Event Management & Ticketing REST API

REST API berbasis **Java** untuk sistem **Manajemen Event dan Ticketing** menggunakan **SQLite Database**.

---

# Deskripsi Proyek

Event Management & Ticketing REST API merupakan aplikasi backend berbasis **Java** yang menyediakan layanan **REST API** untuk mengelola data **User**, **Venue**, **Event**, **Ticket**, dan **Order**. API ini memungkinkan pengguna melakukan operasi **Create, Read, Update, Delete (CRUD)** terhadap setiap entitas yang tersimpan pada database **SQLite**.

Project ini dikembangkan sebagai implementasi konsep **Object-Oriented Programming (OOP)** yang telah dipelajari selama perkuliahan, meliputi:

* Encapsulation
* Inheritance
* Polymorphism
* Abstraction

Selain itu, project menerapkan arsitektur **Model → Repository → Service → Handler**, sehingga setiap layer memiliki tanggung jawab yang jelas dalam proses pengolahan data. Seluruh endpoint API diuji menggunakan **Postman**.

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
git clone https://github.com/username/event-management-ticketing-api.git
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


## 🏢 Venue

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
│   ├── OrderHandler.java
│   ├── RouteHandler.java
│   ├── TicketHandler.java
│   ├── UserHandler.java
│   └── VenueHandler.java
│
├── model/
│   ├── Event.java
│   ├── Order.java
│   ├── Ticket.java
│   ├── User.java
│   └── Venue.java
│
├── repository/
│   ├── EventRepository.java
│   ├── OrderRepository.java
│   ├── TicketRepository.java
│   ├── UserRepository.java
│   └── VenueRepository.java
│
├── service/
│   ├── EventService.java
│   ├── OrderService.java
│   ├── TicketService.java
│   ├── UserService.java
│   └── VenueService.java
│
└── Server.java
```

---

# Pembagian Tugas Anggota

| Anggota        | NIM       | Tanggung Jawab                                                                                                                                               |
| -------------- | --------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Nama Anggota 1 | 25xxxxxxx | Model: User.java, Venue.java, Repository: UserRepository.java, VenueRepository.java, Service: UserService.java, VenueService.java                            |
| Nama Anggota 2 | 25xxxxxxx | Model: Event.java, Repository: EventRepository.java, Service: EventService.java                                                                              |
| Nama Anggota 3 | 25xxxxxxx | Model: Ticket.java, Order.java, Repository: TicketRepository.java, OrderRepository.java                                                                      |
| Nama Anggota 4 | 25xxxxxxx | Service: TicketService.java, OrderService.java, Exception Classes                                                                                            |



