package service

import model.Buku
import model.Peminjaman
import java.time.LocalDate

/**
 * Context class that uses a fine strategy to calculate fines.
 * This class can be configured with different strategies at runtime.
 */
class FineContext(private var strategy: FineStrategy) {

    /**
     * Sets a new fine calculation strategy.
     * @param strategy The new strategy to use.
     */
    fun setStrategy(strategy: FineStrategy) {
        this.strategy = strategy
    }

    /**
     * Executes the current strategy to calculate the fine.
     * @param buku Buku yang dipinjam.
     * @param peminjaman Data peminjaman.
     * @param tglKembali Tanggal pengembalian buku.
     * @return The calculated fine amount.
     */
    fun executeStrategy(buku: Buku, peminjaman: Peminjaman, tglKembali: LocalDate): Int {
        return strategy.calculateFine(buku, peminjaman, tglKembali)
    }
}
