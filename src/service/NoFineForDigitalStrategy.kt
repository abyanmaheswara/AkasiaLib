package service

import model.Buku
import model.Peminjaman
import java.time.LocalDate

class NoFineForDigitalStrategy : FineStrategy {
    override fun calculateFine(buku: Buku, peminjaman: Peminjaman, tglKembali: LocalDate): Int {
        return 0
    }
}
