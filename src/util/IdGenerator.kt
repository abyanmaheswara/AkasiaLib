package util

import java.util.concurrent.ConcurrentHashMap

/**
 * Sebuah 'object' singleton yang bertanggung jawab untuk membuat ID unik secara berurutan.
 * Dibuat sebagai 'object' agar hanya ada satu instance di seluruh aplikasi, sehingga berfungsi
 * sebagai pusat penomoran yang terpusat dan konsisten.
 */
object IdGenerator {
    // Menggunakan ConcurrentHashMap untuk menyimpan counter. Ini adalah Map yang thread-safe,
    // aman digunakan jika aplikasi berjalan di lingkungan multi-threaded.
    private val counters = ConcurrentHashMap<String, Int>()

    /**
     * Menghasilkan ID unik berikutnya untuk sebuah prefix.
     * Fungsi ini di-anotasi dengan @Synchronized untuk memastikan thread-safety, artinya jika dua
     * thread memanggil fungsi ini secara bersamaan, mereka akan dieksekusi secara bergantian
     * untuk menghindari kondisi balapan (race condition) dan ID duplikat.
     *
     * @param prefix Prefix untuk ID (misal: "BC", "AG", "PJ").
     * @return String ID yang sudah diformat (misal: "BC-0001").
     */
    @Synchronized
    fun nextId(prefix: String): String {
        // 1. Ambil nilai counter saat ini untuk prefix yang diberikan, atau 0 jika belum ada.
        val nextValue = counters.getOrDefault(prefix, 0) + 1
        // 2. Simpan nilai baru ke dalam map.
        counters[prefix] = nextValue
        // 3. Format nomor menjadi string 4 digit dengan angka nol di depan (e.g., 1 -> "0001")
        //    dan gabungkan dengan prefix.
        return "$prefix-${String.format("%04d", nextValue)}"
    }
}