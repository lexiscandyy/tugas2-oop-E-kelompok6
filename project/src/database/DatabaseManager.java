package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manager untuk koneksi database SQLite.
 *
 * Database disimpan sebagai file "database.db" di root project.
 * File ini akan dibuat otomatis jika belum ada.
 *
 * Contoh penggunaan di Repository:
 *
 *   try (Connection conn = DatabaseManager.getConnection()) {
 *       PreparedStatement ps = conn.prepareStatement("SELECT * FROM events WHERE id = ?");
 *       ps.setString(1, eventId);
 *       ResultSet rs = ps.executeQuery();
 *       while (rs.next()) {
 *           String nama = rs.getString("nama");
 *           double harga = rs.getDouble("harga_dasar");
 *           // ...
 *       }
 *   }
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:database.db";

    /**
     * Mendapatkan koneksi baru ke database SQLite.
     *
     * PENTING: Selalu gunakan try-with-resources agar koneksi otomatis ditutup!
     *
     *   try (Connection conn = DatabaseManager.getConnection()) {
     *       // gunakan conn disini ...
     *   } // conn otomatis ditutup disini
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        // Aktifkan foreign key support (di SQLite ini harus diaktifkan per koneksi)
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    /**
     * Inisialisasi database: buat semua tabel jika belum ada.
     * Dipanggil sekali saat server pertama kali dijalankan.
     *
     * Tambahkan CREATE TABLE IF NOT EXISTS untuk setiap tabel yang dibutuhkan.
     */
    public static void initialize() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // =============================================
            // BUAT TABEL-TABEL KALIAN DISINI
            // Gunakan CREATE TABLE IF NOT EXISTS agar aman dijalankan berulang.
            // =============================================

            // Contoh (hapus dan ganti dengan tabel kalian):
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (\n" +
                            "    id          TEXT PRIMARY KEY,\n" +
                            "    name        TEXT NOT NULL,\n" +
                            "    email       TEXT NOT NULL UNIQUE,\n" +
                            "    phone       TEXT,\n" +
                            "    role        TEXT DEFAULT 'buyer',       -- 'buyer' atau 'organizer'\n" +
                            "    created_at  TEXT DEFAULT (datetime('now'))\n);"

            );

            stmt.execute("CREATE TABLE IF NOT EXISTS venues (\n" +
                    "    id              TEXT PRIMARY KEY,\n" +
                    "    name            TEXT NOT NULL,\n" +
                    "    address         TEXT NOT NULL,\n" +
                    "    max_capacity    INTEGER NOT NULL,\n" +
                    "    created_at      TEXT DEFAULT (datetime('now'))\n" +
                    ");\n");

            stmt.execute("CREATE TABLE IF NOT EXISTS events (\n" +
                    "    id              TEXT PRIMARY KEY,\n" +
                    "    type            TEXT NOT NULL,           -- 'concert', 'seminar', 'sport_match'\n" +
                    "    name            TEXT NOT NULL,\n" +
                    "    venue_id        TEXT NOT NULL,\n" +
                    "    organizer_id    TEXT NOT NULL,           -- FK ke users (role = 'organizer')\n" +
                    "    date            TEXT NOT NULL,           -- format: YYYY-MM-DD\n" +
                    "    base_price      REAL NOT NULL,\n" +
                    "    created_at      TEXT DEFAULT (datetime('now')),\n" +
                    "    FOREIGN KEY (venue_id) REFERENCES venues(id),\n" +
                    "    FOREIGN KEY (organizer_id) REFERENCES users(id)\n" +
                    ");\n");

            stmt.execute("CREATE TABLE IF NOT EXISTS capacities (\n" +
                    "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    event_id    TEXT NOT NULL,\n" +
                    "    category    TEXT NOT NULL,           -- 'vip', 'regular', 'festival', 'tribune', 'vvip'\n" +
                    "    total       INTEGER NOT NULL,\n" +
                    "    filled      INTEGER DEFAULT 0,\n" +
                    "    FOREIGN KEY (event_id) REFERENCES events(id)\n" +
                    ");\n");

            stmt.execute("CREATE TABLE IF NOT EXISTS tickets (\n" +
                    "    id              TEXT PRIMARY KEY,\n" +
                    "    event_id        TEXT NOT NULL,\n" +
                    "    user_id         TEXT NOT NULL,           -- FK ke users\n" +
                    "    category        TEXT NOT NULL,\n" +
                    "    quantity        INTEGER NOT NULL,\n" +
                    "    unit_price      REAL NOT NULL,\n" +
                    "    total_price     REAL NOT NULL,\n" +
                    "    purchase_date   TEXT DEFAULT (date('now')),\n" +
                    "    status          TEXT DEFAULT 'active',   -- 'active', 'refunded'\n" +
                    "    refund_amount   REAL DEFAULT 0,\n" +
                    "    FOREIGN KEY (event_id) REFERENCES events(id),\n" +
                    "    FOREIGN KEY (user_id) REFERENCES users(id)\n" +
                    ");");

            System.out.println("Database berhasil diinisialisasi.");

        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}