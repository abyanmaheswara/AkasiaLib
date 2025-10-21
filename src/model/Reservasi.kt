package model

import java.time.LocalDate

/**
 * Data class yang merepresentasikan sebuah entri reservasi atau antrian untuk sebuah buku.
 * Dibuat ketika seorang anggota ingin meminjam buku yang stoknya habis.
 *
 * @property id ID unik untuk setiap entri reservasi.
 * @property idAnggota ID anggota yang melakukan reservasi.
 * @property idBuku ID buku yang direservasi.
 * @property tanggalReservasi Tanggal saat reservasi dibuat, default-nya adalah waktu saat ini.
 */
data class Reservasi(
    override val id: String,
    val idAnggota: String,
    val idBuku: String,
    val tanggalReservasi: LocalDate = LocalDate.now()
) : Identifiable