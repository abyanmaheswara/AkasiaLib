package model

/**
 * Enum untuk merepresentasikan tingkatan atau tier dari keanggotaan.
 * Tier ini akan menentukan aturan peminjaman seperti batas jumlah pinjaman dan lama peminjaman.
 */
enum class Tier {
    REGULAR,
    PREMIUM,
    STAFF
}

/**
 * Data class yang merepresentasikan seorang anggota perpustakaan.
 * Dibuat sebagai 'data class' karena tugas utamanya adalah untuk menyimpan data.
 *
 * @property id ID unik anggota, di-override dari interface [Identifiable].
 * @property nama Nama lengkap anggota.
 * @property tier Tingkatan keanggotaan, menggunakan [Tier] enum.
 * @property statusAktif Menandakan apakah keanggotaan masih aktif atau tidak.
 */
data class Anggota(
    override val id: String,
    val nama: String,
    val tier: Tier,
    var statusAktif: Boolean = true
) : Identifiable