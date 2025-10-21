package service

import model.Peminjaman
import model.Buku
import java.time.LocalDate

interface FineStrategy {
    fun calculateFine(buku: Buku, peminjaman: Peminjaman, tglKembali: LocalDate): Int
}