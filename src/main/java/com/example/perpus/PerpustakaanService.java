package com.example.perpus;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class PerpustakaanService {
    private BukuRepository bukuRepository;
    private SesiPeminjamanRepository sesiPeminjamanRepository;

    public PerpustakaanService(BukuRepository bukuRepository, SesiPeminjamanRepository sesiPeminjamanRepository) {
        this.bukuRepository = bukuRepository;
        this.sesiPeminjamanRepository = sesiPeminjamanRepository;
    }

    public Buku pinjamBuku(Long bukuId, LocalDateTime waktuMulai, LocalDateTime waktuSelesai) throws SQLException {
        Buku buku = bukuRepository.findById(bukuId);
        if (buku == null || !buku.isTersedia()) {
            throw new RuntimeException("Buku tidak tersedia");
        }
        buku.setTersedia(false);
        bukuRepository.save(buku);

        SesiPeminjaman sesiPeminjaman = new SesiPeminjaman();
        sesiPeminjaman.setBukuId(bukuId);
        sesiPeminjaman.setWaktuMulai(waktuMulai);
        sesiPeminjaman.setWaktuSelesai(waktuSelesai);
        sesiPeminjamanRepository.save(sesiPeminjaman);

        return buku;
    }

    public void kembalikanBuku(Long bukuId) throws SQLException {
        Buku buku = bukuRepository.findById(bukuId);
        if (buku == null) {
            throw new RuntimeException("Buku tidak ditemukan");
        }
        buku.setTersedia(true);
        bukuRepository.save(buku);
    }
}