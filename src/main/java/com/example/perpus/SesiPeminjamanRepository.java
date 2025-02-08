package com.example.perpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SesiPeminjamanRepository {
    public void save(SesiPeminjaman sesiPeminjaman) throws SQLException {
        String query = "INSERT INTO SesiPeminjaman (bukuId, waktuMulai, waktuSelesai) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, sesiPeminjaman.getBukuId());
            stmt.setTimestamp(2, Timestamp.valueOf(sesiPeminjaman.getWaktuMulai()));
            stmt.setTimestamp(3, Timestamp.valueOf(sesiPeminjaman.getWaktuSelesai()));
            stmt.executeUpdate();
        }
    }
}