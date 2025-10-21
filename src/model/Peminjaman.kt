package model

import java.time.LocalDate

/**
 * Enum untuk merepresentasikan status dari sebuah transaksi peminjaman.
 */
enum class StatusPeminjaman {
    DIPINJAM,
    KEMBALI
}

/**
 * Data class yang merepresentasikan sebuah transaksi peminjaman buku oleh anggota.
 *
 * @property id ID unik untuk setiap transaksi peminjaman.
 * @property idAnggota ID anggota yang melakukan peminjaman.
 * @property idBuku ID buku yang dipinjam.
 * @property tanggalPinjam Tanggal saat buku dipinjam.
 * @property tanggalJatuhTempo Tanggal saat buku harus dikembalikan.
 * @property tanggalKembali Tanggal saat buku benar-benar dikembalikan. Bisa null jika belum dikembalikan.
 * @property status Status peminjaman saat ini, menggunakan [StatusPeminjaman] enum.
 */
data class Peminjaman(
    override val id: String,
    val anggotaId: String,
    val bukuId: String,
    val tglPinjam: LocalDate,
    val jatuhTempo: LocalDate,
    var tglKembali: LocalDate? = null,
    var status: StatusPeminjaman = StatusPeminjaman.DIPINJAM,
    val denda: Int = 0
) : Identifiable