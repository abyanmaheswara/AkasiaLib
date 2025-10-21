package model

/**
 * Enum untuk merepresentasikan format dari buku digital.
 * Memastikan bahwa format yang dimasukkan selalu valid (antara PDF atau EPUB).
 */
enum class FormatDigital {
    PDF,
    EPUB
}

/**
 * Representasi dari buku dalam bentuk digital.
 * Merupakan kelas turunan dari [Buku].
 *
 * @property ukuranFileMb Ukuran file dari buku digital dalam Megabyte.
 * @property format Format dari buku digital, menggunakan [FormatDigital] enum.
 */
class BukuDigital(
    id: String,
    judul: String,
    penulis: String,
    tahun: Int,
    kategori: String,
    val ukuranFileMb: Double,
    val format: FormatDigital
) : Buku(id, judul, penulis, tahun, kategori) {

    /**
     * Menyediakan representasi String yang detail untuk objek BukuDigital.
     */
    override fun info(): String {
        return """
            Tipe: Buku Digital
            ID: $id
            Judul: $judul
            Penulis: $penulis
            Tahun: $tahun
            Kategori: $kategori
            Ukuran File: ${ukuranFileMb}MB
            Format: $format
        """.trimIndent()
    }
}