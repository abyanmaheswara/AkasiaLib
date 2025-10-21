package service

import model.Buku
import model.Peminjaman
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.max

class DefaultFineStrategy : FineStrategy {
    companion object {
        private const val DAILY_FINE_RATE = 1000
    }

    override fun calculateFine(buku: Buku, peminjaman: Peminjaman, tglKembali: LocalDate): Int {
        val overdueDays = max(0, ChronoUnit.DAYS.between(peminjaman.jatuhTempo, tglKembali))
        return (overdueDays * DAILY_FINE_RATE).toInt()
    }
}
