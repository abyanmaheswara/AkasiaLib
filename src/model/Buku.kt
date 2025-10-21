package model

/**
 * Kelas abstrak yang berfungsi sebagai 'template' atau blueprint untuk semua jenis buku.
 * Dibuat 'abstract' karena tidak ada objek 'Buku' yang generik, melainkan wujud spesifik
 * seperti BukuCetak, BukuDigital, dll.
 *
 * @property id ID unik untuk setiap buku, di-override dari interface Identifiable.
 * @property judul Judul buku.
 * @property penulis Nama pengarang buku.
 * @property tahun Tahun terbit buku.
 * @property kategori Kategori buku (misal: Edukasi, Software, Self-Help).
 */
abstract class Buku(
    override val id: String,
    val judul: String,
    val penulis: String,
    val tahun: Int,
    val kategori: String
) : Identifiable {

    /**
     * Fungsi abstrak untuk menampilkan informasi detail dari sebuah buku.
     * Setiap kelas turunan (BukuCetak, BukuDigital, dll.) WAJIB meng-override fungsi ini
     * untuk menyediakan detail spesifiknya masing-masing. Ini adalah contoh Polimorfisme.
     * @return String yang berisi informasi lengkap buku.
     */
    abstract fun info(): String
}