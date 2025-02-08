package com.example.perpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BukuRepository {
    public Buku findById(Long id) throws SQLException {
        String query = "SELECT * FROM Buku WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Buku buku = new Buku();
                buku.setId(rs.getLong("id"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setTersedia(rs.getBoolean("tersedia"));
                return buku;
            }
        }
        return null;
    }

    public void save(Buku buku) throws SQLException {
        String query = "UPDATE Buku SET tersedia = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, buku.isTersedia());
            stmt.setLong(2, buku.getId());
            stmt.executeUpdate();
        }
    }
}