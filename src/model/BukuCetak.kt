package model

/**
 * Representasi dari buku dalam bentuk fisik (tercetak).
 * Merupakan kelas turunan dari [Buku].
 *
 * @property jumlahHalaman Jumlah halaman dari buku cetak.
 * @property stok Jumlah stok fisik yang tersedia di perpustakaan.
 */
class BukuCetak(
    id: String,
    judul: String,
    penulis: String,
    tahun: Int,
    kategori: String,
    val jumlahHalaman: Int,
    var stok: Int
) : Buku(id, judul, penulis, tahun, kategori) {

    /**
     * Menyediakan representasi String yang detail untuk objek BukuCetak.
     */
    override fun info(): String {
        return """
            Tipe: Buku Cetak
            ID: $id
            Judul: $judul
            Penulis: $penulis
            Tahun: $tahun
            Kategori: $kategori
            Halaman: $jumlahHalaman
            Stok: $stok
        """.trimIndent()
    }
}