package model

/**
 * Interface ini berfungsi sebagai "kontrak" atau jaminan untuk kelas-kelas model.
 * Setiap kelas yang mengimplementasikan interface ini WAJIB memiliki properti `id`.
 * Tujuannya adalah untuk memungkinkan pembuatan repositori generik (`InMemoryRepo<T>`) yang bisa
 * melakukan operasi berdasarkan ID (`findById`, `delete`) secara type-safe.
 */
interface Identifiable {
    val id: String
}