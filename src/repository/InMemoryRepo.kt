package repository

import model.Identifiable

/**
 * Interface generik untuk sebuah Repository.
 * Mendefinisikan operasi-operasi dasar yang harus dimiliki oleh semua repositori.
 * @param T Tipe data yang dikelola, dibatasi hanya untuk kelas yang mengimplementasikan [Identifiable].
 */
interface Repository<T : Identifiable> {
    fun save(entity: T)
    fun findAll(): List<T>
    fun findById(id: String): T?
    fun delete(id: String): Boolean
}

/**
 * Implementasi konkret dari [Repository] yang menyimpan data di dalam memori (sebuah List).
 * Kelas ini generik, sehingga bisa digunakan untuk mengelola tipe data apa pun selama tipe tersebut
 * mengimplementasikan [Identifiable].
 *
 * @param T Tipe data yang akan disimpan.
 */
class InMemoryRepo<T : Identifiable> : Repository<T> {
    private val items = mutableListOf<T>()

    /**
     * Menyimpan atau memperbarui sebuah entitas.
     * Logika ini menerapkan perilaku "upsert" (update or insert):
     * - Jika entitas dengan ID yang sama sudah ada, entitas lama akan dihapus terlebih dahulu.
     * - Entitas baru kemudian ditambahkan.
     * Ini memastikan tidak ada ID duplikat dan memungkinkan pembaruan data.
     */
    override fun save(entity: T) {
        items.removeIf { it.id == entity.id }
        items.add(entity)
    }

    /**
     * Mengembalikan semua entitas sebagai List yang tidak bisa diubah (read-only).
     */
    override fun findAll(): List<T> {
        return items.toList()
    }

    /**
     * Mencari satu entitas berdasarkan ID-nya.
     */
    override fun findById(id: String): T? {
        return items.find { it.id == id }
    }

    /**
     * Menghapus satu entitas berdasarkan ID-nya.
     * @return `true` jika ada item yang dihapus, `false` jika tidak ada.
     */
    override fun delete(id: String): Boolean {
        return items.removeIf { it.id == id }
    }
}