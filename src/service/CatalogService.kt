package service

import model.*
import repository.InMemoryRepo

class CatalogService(
    private val bookRepo: InMemoryRepo<Buku>
) {

    // 🔹 Tambah buku baru
    fun addBook(buku: Buku): String {
        bookRepo.save(buku)
        return "✅ Buku '${buku.judul}' berhasil ditambahkan ke katalog."
    }

    // 🔹 Hapus buku berdasarkan ID
    fun removeBook(id: String): String {
        val success = bookRepo.delete(id)
        return if (success) "🗑️ Buku dengan ID $id berhasil dihapus."
        else "❌ Buku tidak ditemukan."
    }

    // 🔹 Cari buku berdasarkan judul
    fun searchByTitle(keyword: String): List<Buku> {
        return bookRepo.findAll().filter {
            it.judul.contains(keyword, ignoreCase = true)
        }
    }

    // 🔹 Cari buku berdasarkan penulis
    fun searchByAuthor(author: String): List<Buku> {
        return bookRepo.findAll().filter {
            it.penulis.contains(author, ignoreCase = true)
        }
    }

    // 🔹 Cari berdasarkan kategori
    fun searchByCategory(kategori: String): List<Buku> {
        return bookRepo.findAll().filter {
            it.kategori.equals(kategori, ignoreCase = true)
        }
    }

    // 🔹 Update buku (misal ubah stok atau data lainnya)
    fun updateBook(updated: Buku): String {
        val existing = bookRepo.findById(updated.id)
            ?: return "❌ Buku tidak ditemukan."
        bookRepo.save(updated)
        return "✏️ Buku '${existing.judul}' berhasil diperbarui."
    }

    fun addAudioBook(buku: BukuAudio): String {
        bookRepo.save(buku)
        return "🎧 Buku audio '${buku.judul}' berhasil ditambahkan ke katalog."
    }

    // 🔹 List semua buku
    fun listAllBooks(): List<Buku> = bookRepo.findAll()
}


