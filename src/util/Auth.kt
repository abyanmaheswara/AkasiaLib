package util

/**
 * Enum untuk merepresentasikan peran (role) dari pengguna sistem.
 * Berbeda dari Anggota, User adalah yang mengoperasikan aplikasi (misal: Pustakawan).
 */
enum class Role {
    ADMIN,
    PETUGAS // Petugas (Librarian)
}

/**
 * Data class untuk merepresentasikan pengguna sistem (operator aplikasi).
 *
 * @property username Username untuk login.
 * @property password Password untuk login.
 * @property role Peran dari pengguna, menggunakan [Role] enum.
 */
data class User(
    val username: String,
    val password: String, // Catatan: Di aplikasi nyata, ini harus disimpan dalam bentuk hash, bukan plain text.
    val role: Role
)

/**
 * Service yang bertanggung jawab untuk otentikasi pengguna sistem.
 */
object Auth { // Changed to object to hold state
    // Di aplikasi nyata, daftar pengguna ini seharusnya dimuat dari database
    // atau sumber yang aman, dan password harus di-hash.
    // Untuk tujuan proyek ini, daftar pengguna di-hardcode.
    private val users = mutableListOf( // Changed to mutableListOf
        User("admin", "admin123", Role.ADMIN),
        User("pustakawan", "lib123", Role.PETUGAS) // Changed Role.PUSTAKAWAN to Role.PETUGAS
    )

    /**
     * Memvalidasi kredensial pengguna.
     * @param username Username yang dimasukkan pengguna.
     * @param password Password yang dimasukkan pengguna.
     * @return Objek [User] jika login berhasil, atau null jika gagal.
     */
    fun login(username: String, password: String): User? {
        return users.find { it.username.equals(username, ignoreCase = true) && it.password == password }
    }

    /**
     * Membuat akun petugas baru.
     * @param username Username untuk petugas baru.
     * @param password Password untuk petugas baru.
     * @return Pesan status.
     */
    fun createPetugas(username: String, password: String): String {
        if (users.any { it.username == username }) {
            return "❌ Username sudah ada."
        }
        users.add(User(username, password, Role.PETUGAS))
        return "✅ Akun petugas '$username' berhasil ditambahkan."
    }

    /**
     * Mengembalikan daftar semua akun petugas.
     * @return List objek [User] dengan peran PETUGAS.
     */
    fun listPetugas(): List<User> {
        return users.filter { it.role == Role.PETUGAS }
    }
}