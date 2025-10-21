package service

import model.*
import repository.InMemoryRepo
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.max
import service.FineContext
import service.FineStrategy
import service.DefaultFineStrategy
import service.NoFineForDigitalStrategy

class CirculationService(
    private val bookRepo: InMemoryRepo<Buku>,
    private val memberRepo: InMemoryRepo<Anggota>,
    private val loanRepo: InMemoryRepo<Peminjaman>,
    private val reservationService: ReservationService
) {
    // Batas pinjam & lama pinjam per tier
    private val limitMap = mapOf(
        Tier.REGULAR to Pair(2, 7),
        Tier.PREMIUM to Pair(5, 14),
        Tier.STAFF to Pair(10, 30)
    )

    // =============== PEMINJAMAN ===============
    fun borrowBook(anggotaId: String, bukuId: String): String {
        val anggota = memberRepo.findById(anggotaId) ?: return "❌ Anggota tidak ditemukan."
        val buku = bookRepo.findById(bukuId) ?: return "❌ Buku tidak ditemukan."

        // Batas pinjaman
        val (maxBorrow, lamaPinjam) = limitMap[anggota.tier]!!
        val activeLoans = loanRepo.findAll().filter { it.anggotaId == anggotaId && it.tglKembali == null }.size
        if (activeLoans >= maxBorrow) return "❌ Batas pinjaman ${anggota.tier} sudah tercapai."

        if (buku is BukuCetak) {
            if (buku.stok <= 0) {
                reservationService.reserveBook(anggotaId, bukuId)
                return "⚠️ Buku habis, ${anggota.nama} dimasukkan ke daftar antrian."
            }
            buku.stok--
        }

        val pinjam = Peminjaman(
            id = "PM-${loanRepo.findAll().size + 1}",
            bukuId = bukuId,
            anggotaId = anggotaId,
            tglPinjam = LocalDate.now(),
            jatuhTempo = LocalDate.now().plusDays(lamaPinjam.toLong())
        )

        loanRepo.save(pinjam)
        return "✅ ${anggota.nama} berhasil meminjam buku '${buku.judul}'."
    }

    // =============== PENGEMBALIAN ===============
    fun returnBook(peminjamanId: String, tglKembali: LocalDate): String {
        val pinjam = loanRepo.findById(peminjamanId) ?: return "❌ Data peminjaman tidak ditemukan."
        val buku = bookRepo.findById(pinjam.bukuId) ?: return "❌ Buku tidak ditemukan."

        if (pinjam.tglKembali != null) return "⚠️ Buku sudah dikembalikan sebelumnya."

        val overdueDays = max(0, ChronoUnit.DAYS.between(pinjam.jatuhTempo, tglKembali))
        val fineStrategy: FineStrategy = if (buku is BukuDigital) {
            NoFineForDigitalStrategy()
        } else {
            DefaultFineStrategy()
        }
        val fineContext = FineContext(fineStrategy)
        val denda = fineContext.executeStrategy(buku, pinjam, tglKembali)

        // Update stok kalau cetak
        if (buku is BukuCetak) {
            buku.stok++
        }

        // Update data pinjaman
        val updated = pinjam.copy(tglKembali = tglKembali, denda = denda, status = StatusPeminjaman.KEMBALI)
        loanRepo.save(updated)

        // Cek antrian
        val nextInQueue = reservationService.popNextReservation(buku.id)
        val notif = if (nextInQueue != null)
            "\n📢 Buku '${buku.judul}' sekarang tersedia untuk ${nextInQueue.nama}."
        else ""

        return "✅ Buku '${buku.judul}' berhasil dikembalikan. Denda: Rp$denda.$notif"
    }

    // =============== LAPORAN ===============
    fun listActiveLoans(): List<Peminjaman> =
        loanRepo.findAll().filter { it.tglKembali == null }

    fun totalDenda(): Int =
        loanRepo.findAll().sumOf { it.denda }

    fun topBorrowedBooks(): List<String> {
        val counts = loanRepo.findAll().groupingBy { it.bukuId }.eachCount()
        val sorted = counts.toList().sortedByDescending { it.second }.take(3)
        return sorted.map { (bukuId, count) ->
            val buku = bookRepo.findById(bukuId)
            "${buku?.judul} ($count kali)"
        }
    }
}