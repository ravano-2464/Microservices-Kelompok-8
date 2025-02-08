package com.example.perpus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@WebServlet("/api/perpustakaan")
public class PerpustakaanServlet extends HttpServlet {
    private PerpustakaanService perpustakaanService;

    @Override
    public void init() throws ServletException {
        try {
            BukuRepository bukuRepository = new BukuRepository();
            SesiPeminjamanRepository sesiPeminjamanRepository = new SesiPeminjamanRepository();
            perpustakaanService = new PerpustakaanService(bukuRepository, sesiPeminjamanRepository);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Long bukuId;
        try {
            bukuId = Long.parseLong(req.getParameter("bukuId"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID buku tidak valid");
            return;
        }

        try {
            if ("pinjam".equals(action)) {
                LocalDateTime waktuMulai = LocalDateTime.parse(req.getParameter("waktuMulai"));
                LocalDateTime waktuSelesai = LocalDateTime.parse(req.getParameter("waktuSelesai"));
                Buku buku = perpustakaanService.pinjamBuku(bukuId, waktuMulai, waktuSelesai);
                resp.getWriter().write("Buku dipinjam: " + buku.getJudul());
            } else if ("kembalikan".equals(action)) {
                perpustakaanService.kembalikanBuku(bukuId);
                resp.getWriter().write("Buku dikembalikan");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Aksi tidak dikenal");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (DateTimeParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format waktu tidak valid");
        }
    }
}